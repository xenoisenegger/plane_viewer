package guilib;

import io.github.humbleui.skija.Color4f;
import io.github.humbleui.skija.Typeface;

public abstract class Control extends Node {
    public final static Color4f DEFAULTBACKGROUNDCOLOR = new Color4f(0, 0.66f, 0.37f);
    public final static Color4f DEFAULTBHOVERBACKGROUNDCOLOR = new Color4f(0, 0.77f, 0.47f);
    public final static Color4f DEFAULTPRESSEDBACKGROUNDCOLOR = new Color4f(0, 0.50f, 0.20f);
    public final static Color4f DEFAULT_BORDER_COLOR = new Color4f(0, 0.66f, 0.37f);
    public final static Color4f DEFAULT_TEXT_COLOR = new Color4f(0, 0, 0);
    public final static int DEFAULTFONTSIZE = 16;
    private float x;
    private float y;

    public Control(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static final Typeface DEFAULT_TYPEFACE = Typeface.makeDefault();

    public abstract float getHeight();

    public float getX() {
        return x;
    }

    ;

    public float getY() {
        return y;
    }

    ;

    public abstract float getWidth();

    public abstract boolean isInside(int x, int y);

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
