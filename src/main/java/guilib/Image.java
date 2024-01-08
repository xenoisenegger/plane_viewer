package guilib;

import io.github.humbleui.jwm.Event;
import io.github.humbleui.jwm.EventMouseMove;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.Rect;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Image extends Control {
    private static final double GROW_FACTOR = 6;
    private float x;
    private float y;
    private float width;
    private float height;
    private String path;
    private io.github.humbleui.skija.Image image;
    private float oldWidth;
    private float oldHeight;
    private float oldX;
    private float oldY;

    private boolean animated;
    private boolean isHover;


    public Image(float x, float y, float width, float height, String path) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.path = path;
        this.image = createImage(path);
        this.animated = false;
        this.isHover = false;
    }

    public float getWidth() {
        return width;
    }

    @Override
    public boolean isInside(int x, int y) {
        return x >= this.getX() && x <= this.getX() + getWidth()
                && y >= this.getY() && y <= this.getY() + getHeight();
    }


    @Override
    public void handleEvent(Event e) {
        if (e instanceof EventMouseMove mb
                && isInside(mb.getX(), mb.getY())
                && animated
                && !isHover) {
            oldWidth = width;
            oldHeight = height;
            oldX = getX();
            oldY = getY();
            growImg();
            isHover = true;
        } else if (e instanceof EventMouseMove mb
                && !isInside(mb.getX(), mb.getY()) && isHover) {
            setHeight(oldHeight);
            setWidth(oldWidth);
            super.setX(oldX);
            super.setY(oldY);
            isHover = false;
        }
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawImageRect(getImage(), Rect.makeXYWH(getX(), getY(), getWidth(), getHeight()));
    }

    private io.github.humbleui.skija.Image createImage(String path) {
        io.github.humbleui.skija.Image image = null;
        try {
            byte[] fileData = Files.readAllBytes(Paths.get(path));
            image = io.github.humbleui.skija.Image.makeFromEncoded(fileData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void growImg() {
        int grownWidth = (int) (getWidth() + GROW_FACTOR);
        int grownHeight = (int) (getHeight() + GROW_FACTOR);
        float grownX = getX() - (float) (GROW_FACTOR / 2);
        float grownY = getY() - (float) (GROW_FACTOR / 2);
        setWidth(grownWidth);
        setHeight(grownHeight);
        super.setX(grownX);
        super.setY(grownY);
    }

    public void setX(float x) {
        this.x = x;
        setDirty();
    }

    public void setY(float y) {
        this.y = y;
        setDirty();
    }

    public void setWidth(float width) {
        this.width = width;
        setDirty();
    }

    @Override
    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        setDirty();
    }

    public void setPath(String path) {
        this.path = path;
        setDirty();
    }

    public io.github.humbleui.skija.Image getImage() {
        return image;
    }

    public void setImage(io.github.humbleui.skija.Image image) {
        this.image = image;
        setDirty();
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

}

