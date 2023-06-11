package guilib;

import io.github.humbleui.jwm.*;
import io.github.humbleui.skija.*;
import io.github.humbleui.types.Point;
import io.github.humbleui.types.RRect;

public class TextField extends Control{
    public static final Padding PADDING = new Padding(5,5,5,5);
    private Color4f textColor;
    private Color4f borderColor;
    private Color4f backgorundColor;
    private int fontSize;
    private String text;
    private boolean selected;
    private Label display;
    private Font font = new Font(DEFAULT_TYPEFACE, DEFAULTFONTSIZE);
    private int cursorIndex;
    private float height;
    private float width;
    Runnable onAction;
    public TextField(float x, float y, int cellSize) {
        super(x, y);
        this.height = cellSize;
        this.width = cellSize;
        this.text = null;
        this.textColor = DEFAULT_TEXT_COLOR;
        this.borderColor = DEFAULTBACKGROUNDCOLOR;
        this.backgorundColor = new Color4f(0.9f, 0.9f, 0.9f);
    }

    public TextField(Point point, int cellSize) {
        super(point.getX(), point.getY());
        this.width = cellSize;
        this.height = font.getMetrics().getHeight() + 20;
        this.text = null;
        this.textColor = DEFAULT_TEXT_COLOR;
        this.borderColor = DEFAULTBACKGROUNDCOLOR;
        this.backgorundColor = new Color4f(0.9f, 0.9f, 0.9f);
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public boolean isInside(int x, int y) {
        return x >= this.getX() && x <= this.getX() + getWidth()
                && y >= this.getY() && y <= this.getY() + getHeight();
    }

    @Override
    public void handleEvent(Event scaledEvent) {
        if (scaledEvent instanceof EventMouseButton mb
        && isInside(mb.getX(), mb.getY())
        && !mb.isPressed()){
            setBorderColor(new Color4f(0, 0, 0));
            selected = true;
            if (getText() == null){
                cursorIndex = 0;
            }else {
                cursorIndex = getText().length();
            }
        }else if (scaledEvent instanceof EventMouseButton mb
                && !isInside(mb.getX(), mb.getY())
                && !mb.isPressed()){
            setBorderColor(DEFAULTBACKGROUNDCOLOR);
            selected = false;
        } else if (scaledEvent instanceof EventTextInput ti
                && selected) {
            if (getText() == null){
                setText(ti.getText());
                cursorIndex += 1;
            } else {
                setText(getText().substring(0, cursorIndex) + ti.getText() + getText().substring(cursorIndex, getText().length()));
                cursorIndex += 1;
            }
            onAction.run();
        } else if (scaledEvent instanceof EventKey ek
                && selected
                && getText() != null
                && ek.isPressed()) {
            if (ek.getKey().equals(Key.BACKSPACE)
                    && getText().length() > 0){
                setText(getText().substring(0, cursorIndex - 1)  + getText().substring(cursorIndex, getText().length()));
                cursorIndex -= 1;
            } else if (ek.getKey().equals(Key.LEFT)) {
                if (cursorIndex >= 1){
                    cursorIndex -= 1;
                }
            } else if (ek.getKey().equals(Key.RIGHT)) {
                if (cursorIndex <= getText().length() - 1){
                    cursorIndex += 1;
                }
            }
            onAction.run();
            setDirty();
        }
    }

    private boolean isInside(float xM, float yM) {
        return (xM >= getX() && xM <= getX() + getWidth()) && (yM >= getY() && yM <= getY() + getHeight());
    }

    @Override
    public void paint(Canvas canvas) {
        RRect rRect = RRect.makeXYWH(getX(), getY(), getWidth(), getHeight(), 5);
        canvas.drawRRect(rRect, new Paint().setColor4f(backgorundColor));
        canvas.drawRRect(rRect, new Paint().setColor4f(borderColor).setMode(PaintMode.STROKE).setStrokeWidth(2));
        if (getText() != null){
            display = new Label(getX() + PADDING.getLeft(), getY() + ((getHeight() - PADDING.getTop() - PADDING.getBottom())/ 2) + (font.getMetrics().getAscent() / 2), (int) (getWidth() - PADDING.getTop() - PADDING.getBottom()), getText());
            display.setTextAlignment(TextAlignment.LEFT);
            display.setFontSize(DEFAULTFONTSIZE);
            display.paint(canvas);
        }
        if (selected){
            float pointTopX = getX() + PADDING.getLeft();
            float pointTopY = getY() + PADDING.getTop();
            float pointBottomX = getX() + PADDING.getLeft();
            float pointBottomY = getY() + getHeight() - PADDING.getBottom();
            if (getText() != null){
                pointTopX += font.measureTextWidth(getText().substring(0, cursorIndex));
                pointBottomX += font.measureTextWidth(getText().substring(0, cursorIndex));
            }
            canvas.drawLine(pointBottomX, pointBottomY, pointTopX, pointTopY, new Paint());
        }
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        setDirty();
    }

    public String getText() {
        return text;
    }

    public Color4f getTextColor() {
        return textColor;
    }

    public void setTextColor(Color4f textColor) {
        this.textColor = textColor;
        setDirty();
    }

    public Color4f getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color4f borderColor) {
        this.borderColor = borderColor;
        setDirty();
    }

    public void setText(String s) {
        this.text = s;
        setDirty();
    }

    public void setOnTextChanged(Runnable onAction) {
        this.onAction = onAction;
        setDirty();
    }
}
