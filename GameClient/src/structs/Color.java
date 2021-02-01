package structs;

public class Color extends GPosition {

    private float alpha;

    public static final Color WHITE = new Color(1f, 1f, 1f);
    public static final Color BLACK = new Color(0f, 0f, 0f);
    public static final Color RED = new Color(1f, 0f, 0f);
    public static final Color GREEN = new Color(0f, 1f, 0f);
    public static final Color BLUE = new Color(0f, 0f, 1f);

    public Color(float x, float y, float z, float a) {
        super(x,y,z);
        this.alpha = a;
    }

    public Color(float x, float y, float z) {
        this(x,y,z, 0f);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
