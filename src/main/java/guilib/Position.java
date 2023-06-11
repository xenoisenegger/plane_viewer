package guilib;

import io.github.humbleui.types.Point;

public final class Position {

    private Position() {} // no need to instantiate

    public static Point at(Control control) {
        return new Point(control.getX(), control.getY());
    }

    public static Point below(Control control) {
        return at(control).offset(0, control.getHeight());
    }

    public static Point rightOf(Control control) {
        return at(control).offset(control.getWidth(), 0);
    }
}
