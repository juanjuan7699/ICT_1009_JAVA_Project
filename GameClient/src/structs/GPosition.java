package structs;

public class GPosition {

    //now also acts as a vector3f for colors and others
    private float x;
    private float y;
    private float z;

    public GPosition(float x, float y) { //is actually a Vector2f custom made

        this(x, y, 0f);

    }

    public GPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public void drawDebug() {
        //do draw here posX posY
    }

    public double getRangeTo(GPosition target) { //pythagoras theorem, slight rounding
        //if x:100 y:200 and target x:200 y: 250
        //return range of hypotenuse
        int xDiff = (int)Math.abs(getX() - target.getX());
        int yDiff = (int)Math.abs(getY() - target.getY());
        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
    }

    public GPosition getExactRangeTo(GPosition target) { //absolute vals
        //if x:100 y:200 and target x:200 y: 250
        //return {x:100 y:50}
        return new GPosition(Math.abs(getX() - target.getX()), Math.abs(getY() - target.getY()));
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}

class HPosition extends GPosition { //hitbox
    //really weird but probably change later
    private float posXend;
    private float posYend;

    public HPosition(int posX, int posY, int posXend, int posYend) {
        super(posX, posY);
        this.posXend = posXend;
        this.posYend = posYend;
    }

    @Override
    public void drawDebug() {
        //draw square from posX to posYend
    }

    public float getPosYend() {
        return posYend;
    }

    public void setPosYend(float posYend) {
        this.posYend = posYend;
    }

    public float getPosXend() {
        return posXend;
    }

    public void setPosXend(float posXend) {
        this.posXend = posXend;
    }
}
