package guilib;

import io.github.humbleui.jwm.Event;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Color4f;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.Point;

public class Label extends Labeled{
    private static final Color4f DEFAULTTEXTCOLOR = new Color4f(0, 0, 0, 1);
    private Color4f textColor;
    private float x;
    private float y;
    private float width;

    private float fontSize = DEFAULTFONTSIZE;
    private float textPositionX;

    public Label(float x, float y, int width, String text) {
        super(x, y, text, TextAlignment.LEFT);
        this.x = x;
        this.y = y;
        this.width = width;
        this.textColor = DEFAULTTEXTCOLOR;
        this.fontSize = DEFAULTFONTSIZE;
    }

    public Label(Point point, String text) {
        super(point.getX(), point.getY(), text, TextAlignment.LEFT);

        this.x = point.getX();
        this.y = point.getY();
        this.width = new Font(DEFAULT_TYPEFACE, DEFAULTFONTSIZE).measureTextWidth(text);
        this.textColor = DEFAULTTEXTCOLOR;
        this.fontSize = DEFAULTFONTSIZE;
    }
    public float getWidth() {
        return width;
    }

    @Override
    public boolean isInside(int x, int y) {
        return x >= this.getX() && x <= this.getX() + getWidth()
                && y >= this.getY() && y <= this.getY() + getHeight();
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        setDirty();
    }
    public Color4f getTextColor() {
        return textColor;
    }

    public void setTextColor(Color4f textColor) {
        setDirty();
        this.textColor = textColor;
    }
    public Font font(){
        setDirty();
        return new Font(DEFAULT_TYPEFACE, fontSize);
    }
    @Override
    public float getHeight() {
        return font().getMetrics().getHeight();
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void handleEvent(Event scaledEvent) {
    }
    public float getAscent(){
        return font().getMetrics().getAscent();
    }
    @Override
    public void paint(Canvas canvas) {
        if (getTextAlignment() == TextAlignment.CENTER){
            textPositionX = getX() + ((getWidth() / 2) - font().measureTextWidth(getText()) / 2);
        } else if (getTextAlignment() == TextAlignment.LEFT) {
            textPositionX = getX();
        } else if (getTextAlignment() == TextAlignment.RIGHT) {
            textPositionX = getX() + getWidth() - font().measureTextWidth(getText());
        }
        canvas.drawString(getText(), textPositionX , getY() + getHeight(), font(), new Paint().setColor4f(getTextColor()));
    }
}
