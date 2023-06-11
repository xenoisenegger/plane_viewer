package guilib;

import io.github.humbleui.jwm.Event;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Arrays;

public class Group extends Node{
    private ArrayList<Node> nodes = new ArrayList<Node>();
    public Group(Node... children){
        nodes.addAll(Arrays.asList(children));
    }

    @Override
    public void handleEvent(Event scaledEvent) {
        for (Node node: nodes) {
            node.handleEvent(scaledEvent);
        }
    }

    public void paint(Canvas canvas){
        for (Node node: nodes) {
            node.paint(canvas);
        }
    }
    @Override
    protected boolean clearDirty() {
        for (Node node : nodes) {
            if (node.clearDirty()) {
                for (Node dirtyNodes : nodes) {
                    dirtyNodes.cleanDirty();
                }
                return true;
            }
        }
        return false;
    }

    public void addChild(Control field) {
        nodes.add(field);
    }
}
