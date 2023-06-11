package guilib;

import io.github.humbleui.jwm.*;
import io.github.humbleui.jwm.skija.EventFrameSkija;
import io.github.humbleui.jwm.skija.LayerGLSkija;
import io.github.humbleui.skija.Canvas;

public abstract class Application {

    private Window window;
    private float scale;
    private Group controls;

    protected void start(String title, int width, int height) {
        App.start(() -> {
            window = App.makeWindow();
            // for high-res displays, determine scaling factor
            scale = window.getScreen().getScale();

            controls = createContent();

            window.setLayer(new LayerGLSkija());
            window.setTitle(title);
            // make window larger on high-res displays
            window.setContentSize((int) (width * scale), (int) (height * scale));
            window.setEventListener(this::handleEvent);
            window.setVisible(true);
        });
    }

    public Window getWindow() {
        return window;
    }

    protected abstract Group createContent();

    protected void handleEvent(Event e) {
        if (e instanceof EventWindowCloseRequest) {
            App.terminate();
        } else if (e instanceof EventFrameSkija frameEvent) {
            var canvas = frameEvent.getSurface().getCanvas();
            canvas.save();
            // scale canvas up, so that coordinates used in paint()
            // methods are automatically converted to larger size
            canvas.scale(scale, scale);
            paint(canvas);
            canvas.restore();
        } else {
            var scaledEvent = scaleMouseCoordinates(e);
            controls.handleEvent(scaledEvent);
            if (controls.clearDirty()){
                window.requestFrame();
            }
        }
    }

    protected void paint(Canvas canvas) {
        canvas.clear(0xFFFFFFFF);
        controls.paint(canvas);
    }

    /**
     * Scales the coordinates in the given mouse event down, so that
     * larger size window coordinates are converted back to smaller
     * size. Non-mouse events are unaffected.
     */
    private Event scaleMouseCoordinates(Event e) {
        if (e instanceof EventMouseButton mb) {
            return new EventMouseButton(mb.getButton()._mask, mb.isPressed(),
                    (int) (mb.getX() / scale), (int) (mb.getY() / scale), mb.getModifiers());
        } else if (e instanceof EventMouseMove mm) {
            return new EventMouseMove((int) (mm.getX() / scale), (int) (mm.getY() / scale),
                    (int) (mm.getMovementX() / scale), (int) (mm.getMovementY() / scale),
                    mm._buttons, mm._modifiers);
        } else if (e instanceof EventMouseScroll ms) {
            return new EventMouseScroll(ms.getDeltaX() / scale, ms.getDeltaY() / scale,
                    ms.getDeltaChars(), ms.getDeltaLines(), ms.getDeltaPages(),
                    (int) (ms.getX() / scale), (int) (ms.getY() / scale), ms._modifiers);
        } else {
            return e;
        }
    }
}
