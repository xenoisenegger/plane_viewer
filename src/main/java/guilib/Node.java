package guilib;

import io.github.humbleui.jwm.Event;
import io.github.humbleui.skija.Canvas;

public abstract class Node {
    private boolean dirty;
    public Node() {
        this.dirty = false;
    }

    protected abstract void handleEvent(Event scaledEvent);
    protected abstract void paint(Canvas canvas);
    protected void setDirty(){
        this.dirty = true;
    };
    protected boolean isDirty(){
        return this.dirty;
    }
    protected boolean clearDirty(){
        if (isDirty()){
            cleanDirty();
            return true;
        }else {
            return false;
        }
    };
    protected void cleanDirty(){
        this.dirty = false;
    }
    public void updateDirty(boolean correct, boolean b){

    }
}
