package guilib;

import io.github.humbleui.jwm.Event;
import io.github.humbleui.jwm.EventMouseButton;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.Point;
import io.github.humbleui.types.RRect;

public class Checkbox extends Control{

    private boolean state;
    private String text;
    private Font font;
    private int cellSize;
    public Checkbox(Point point, String text) {
        super(point.getX(), point.getY());
        this.state = false;
        this.text = text;
        this.font = new Font(DEFAULT_TYPEFACE, DEFAULTFONTSIZE);
        this.cellSize = 20;
    }

    @Override
    public float getHeight() {
        return font.getMetrics().getAscent();
    }

    @Override
    public float getWidth() {
        return font.measureTextWidth(text);
    }

    @Override
    public boolean isInside(int x, int y) {
        return x >= getX() && y >= getY() && x <= getX() + cellSize && y <= getY() + cellSize;

    }

    @Override
    protected void handleEvent(Event e) {
        if (e instanceof EventMouseButton mb
                && mb.isPressed()
                && isChecked()
                && isInside(mb.getX(), mb.getY())){
            setChecked(false);
        } else if (e instanceof EventMouseButton mb
                && mb.isPressed()
                && isInside(mb.getX(), mb.getY())
                && !isChecked()) {
            setChecked(true);
        }
    }

    @Override
    protected void paint(Canvas canvas) {
        RRect rrect = RRect.makeXYWH(getX(), getY(), cellSize, cellSize, 5);
        canvas.drawRRect(rrect, new Paint().setColor4f(DEFAULT_BORDER_COLOR).setMode(PaintMode.STROKE).setStrokeWidth(2));
        canvas.drawString(getText(), getX() + cellSize + 10, getY() + (cellSize / 2) - (getHeight() / 2) ,font, new Paint());
        if (isChecked()){
            canvas.drawLine(getX() + 4, getY() + 4, getX() + 16, getY() + 16,new Paint().setStrokeWidth(2));
            canvas.drawLine(getX() + 4, getY() + 16, getX() + 16, getY() + 4,new Paint().setStrokeWidth(2));
        }
    }

    public void setChecked(boolean b) {
        state = b;
        setDirty();
    }

    public boolean isChecked() {
        return state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        setDirty();
    }
}
