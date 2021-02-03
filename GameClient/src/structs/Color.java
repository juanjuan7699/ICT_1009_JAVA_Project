package structs;

import maths.GVector;

public class Color extends GVector {
    public static final Color WHITE = new Color(1f, 1f, 1f);
    public static final Color BLACK = new Color(0f, 0f, 0f);
    public static final Color RED = new Color(1f, 0f, 0f);
    public static final Color GREEN = new Color(0f, 1f, 0f);
    public static final Color BLUE = new Color(0f, 0f, 1f);

    //x is red
    //y is green
    //z is blue
    //w is alpha

    /** The default color is black. */
    public Color() {
        this(0f, 0f, 0f);
    }

    public Color(float red, float green, float blue) {
        this(red, green, blue, 1f);
    }

    public Color(float red, float green, float blue, float alpha) {
        super(red, green, blue, alpha);
    }


    //just a few wrappers
    public float getRed() {
        return this.getX();
    }

    public void setRed(float red) {
        if (red < 0f) {
            red = 0f;
        }
        if (red > 1f) {
            red = 1f;
        }
        this.setX(red);
    }

    public float getGreen() {
        return this.getY();
    }

    public void setGreen(float green) {
        if (green < 0f) {
            green = 0f;
        }
        if (green > 1f) {
            green = 1f;
        }
        this.setY(green);
    }

    public float getBlue() {
        return this.getZ();
    }

    public void setBlue(float blue) {
        if (blue < 0f) {
            blue = 0f;
        }
        if (blue > 1f) {
            blue = 1f;
        }
        this.setZ(blue);
    }

    public float getAlpha() {
        return this.getW();
    }

    public void setAlpha(float alpha) {
        if (alpha < 0f) {
            alpha = 0f;
        }
        if (alpha > 1f) {
            alpha = 1f;
        }
        this.setW(alpha);
    }

}
