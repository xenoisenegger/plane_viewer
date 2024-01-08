package guilib;


public abstract class Labeled extends Control {
    private TextAlignment textAlignment;
    private String text;

    public Labeled(float x, float y, String text, TextAlignment textAlignment) {
        super(x, y);
        this.textAlignment = textAlignment;
        this.text = text;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        setDirty();
        this.textAlignment = textAlignment;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String newText) {
        setDirty();
        this.text = newText;
    }
}
