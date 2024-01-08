package guilib;

import io.github.humbleui.jwm.Event;
import io.github.humbleui.jwm.EventMouseButton;
import io.github.humbleui.jwm.EventMouseMove;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Color4f;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.Point;
import io.github.humbleui.types.RRect;

public class Button extends Labeled {

    private final static Color4f DEFAULTTEXTCOLOR = new Color4f(1, 1, 1);

    private String text;
    private Runnable onAction;

    public int getFontSize() {
        return fontSize;
    }

    private int fontSize;

    private float width;

    private float height;
    private float x;
    private float y;
    private Color4f backgroundColor;
    private Color4f hoverBackgroundColor;
    private Color4f pressedBackgroundColor;
    private Color4f textColor;
    private boolean isHover;
    private Color4f originalColor;

    public Button(float x, float y, String text) {
        super(x, y, text, TextAlignment.CENTER);
        this.text = text;
        this.width = new Font(DEFAULT_TYPEFACE, DEFAULTFONTSIZE).measureTextWidth(text);
        this.x = x;
        this.y = y;
        this.backgroundColor = DEFAULTBACKGROUNDCOLOR;
        this.hoverBackgroundColor = DEFAULTBHOVERBACKGROUNDCOLOR;
        this.pressedBackgroundColor = DEFAULTPRESSEDBACKGROUNDCOLOR;
        this.textColor = DEFAULTTEXTCOLOR;
        this.fontSize = DEFAULTFONTSIZE;
    }

    public Button(Point point, float width, String text) {
        super(point.getX(), point.getY(), text, TextAlignment.CENTER);
        this.width = width;
        this.text = text;
        this.x = point.getX();
        this.y = point.getY();
        this.backgroundColor = DEFAULTBACKGROUNDCOLOR;
        this.hoverBackgroundColor = DEFAULTBHOVERBACKGROUNDCOLOR;
        this.pressedBackgroundColor = DEFAULTPRESSEDBACKGROUNDCOLOR;
        this.textColor = DEFAULTTEXTCOLOR;
        this.fontSize = DEFAULTFONTSIZE;
        this.height = getHeight();
    }

    private Font font() {
        return new Font(DEFAULT_TYPEFACE, fontSize);
    }

    public float getWidth() {
        return width;
    }

    @Override
    public boolean isInside(int x, int y) {
        return x >= this.getX() && x <= this.getX() + getWidth()
                && y >= this.getY() && y <= this.getY() + height;
    }

    public float getHeight() {
        return font().getMetrics().getHeight() + 20;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    public void setText(String text) {
        setDirty();
        this.text = text;
    }

    public Color4f getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color4f backgroundColor) {
        setDirty();
        this.backgroundColor = backgroundColor;
    }

    public Color4f getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color4f hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color4f getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color4f pressedBackgroundColor) {
        setDirty();
        this.pressedBackgroundColor = pressedBackgroundColor;
    }

    public Color4f getTextColor() {
        return textColor;
    }

    public void setTextColor(Color4f textColor) {
        setDirty();
        this.textColor = textColor;
    }

    public void setFontSize(int fontSize) {
        setDirty();
        this.fontSize = fontSize;
    }

    public void setOnAction(Runnable onAction) {
        setDirty();
        this.onAction = onAction;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }


    @Override
    public void handleEvent(Event e) {
        if (onAction != null
                && e instanceof EventMouseButton mb
                && !mb.isPressed() // released
                && isInside(mb.getX(), mb.getY())) {
            onAction.run();
            setBackgroundColor(originalColor);
        } else if (onAction != null
                && e instanceof EventMouseButton mb
                && mb.isPressed()
                && isInside(mb.getX(), mb.getY())) {
            setBackgroundColor(DEFAULTPRESSEDBACKGROUNDCOLOR);
        } else if (e instanceof EventMouseMove mb
                && isInside(mb.getX(), mb.getY()) && !isHover) {
            originalColor = getBackgroundColor();
            setBackgroundColor(DEFAULTBHOVERBACKGROUNDCOLOR);
            isHover = true;
        } else if (e instanceof EventMouseMove mb
                && !isInside(mb.getX(), mb.getY()) && isHover) {
            setBackgroundColor(originalColor);
            isHover = false;
        }
    }


    @Override
    public void paint(Canvas canvas) {
        RRect rrect = RRect.makeXYWH(getX(), getY(), getWidth(), height, 5);
        canvas.drawRRect(rrect, new Paint().setColor4f(backgroundColor));
        float horizontalTextCenterAlligmentTextCenterAlligment = getX() + ((getWidth() / 2) - font().measureTextWidth(text) / 2);
        float verticalTextCenterAlligment = getY() + (height / 2) - (font().getMetrics().getAscent() / 2);
        canvas.drawString(text, horizontalTextCenterAlligmentTextCenterAlligment, verticalTextCenterAlligment, font(), new Paint().setColor4f(textColor));
    }
}
