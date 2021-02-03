package maths;

public class GVector {

    //acts as 2f,3f,4f, color, hitbox, etc
    private float x;
    private float y;
    private float z;
    private float w;

    public GVector(float x, float y) { //2 value position
        this(x, y, 0f);
    }

    public GVector(float x, float y, float z) { //3 value position
        this(x,y,z,0f);
    }

    public GVector(float x, float y, float z, float w) { //4 value poosition
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void drawDebug() {
        //do draw here posX posY
    }

    public double getRangeTo(GVector target) { //pythagoras theorem, slight rounding
        //if x:100 y:200 and target x:200 y: 250
        //return range of hypotenuse
        int xDiff = (int)Math.abs(getX() - target.getX());
        int yDiff = (int)Math.abs(getY() - target.getY());
        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
    }

    public GVector getExactRangeTo(GVector target) { //absolute vals
        //if x:100 y:200 and target x:200 y: 250
        //return {x:100 y:50}
        return new GVector(Math.abs(getX() - target.getX()), Math.abs(getY() - target.getY()));
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float lengthSquared3f() {
        return x * x + y * y + z * z;
    }

    public float length3f() {
        return (float) Math.sqrt(lengthSquared3f());
    }

    public GVector lerp3f(GVector other, float alpha) {
        return this.scale(1f - alpha).add(other.scale(alpha));
    }

    public GVector add(GVector other) {
        float x = this.x + other.x;
        float y = this.y + other.y;
        float z = this.z + other.z;
        float w = this.w + other.w;
        return new GVector(x, y, z, w);
    }

    public GVector subtract(GVector other) {
        return this.add(other.negate());
    }

    public GVector negate() {
        return scale(-1f);
    }

    public GVector scale(float scalar) {
        float x = this.x * scalar;
        float y = this.y * scalar;
        float z = this.z * scalar;
        float w = this.w * scalar;
        return new GVector(x, y, z, w);
    }

    public GVector normalize() {
        float length = length3f();
        return divide(length);
    }

    public GVector divide(float scalar) {
        return scale(1f / scalar);
    }

    public float dot3f(GVector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public GVector cross3f(GVector other) {
        float x = this.y * other.z - this.z * other.y;
        float y = this.z * other.x - this.x * other.z;
        float z = this.x * other.y - this.y * other.x;
        return new GVector(x, y, z);
    }
}

