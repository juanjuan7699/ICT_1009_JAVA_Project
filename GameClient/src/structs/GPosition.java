package structs;

public class GPosition {
    private float posX;
    private float posY;

    public GPosition(float posX, float posY) { //is actually a Vector2f custom made

        this.setPosX(posX);
        this.setPosY(posY);
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void drawDebug() {
        //do draw here posX posY
    }

    public double getRangeTo(GPosition target) { //pythagoras theorem, slight rounding
        //if x:100 y:200 and target x:200 y: 250
        //return range of hypotenuse
        int xDiff = (int)Math.abs(getPosX() - target.getPosX());
        int yDiff = (int)Math.abs(getPosY() - target.getPosY());
        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
    }

    public GPosition getExactRangeTo(GPosition target) { //absolute vals
        //if x:100 y:200 and target x:200 y: 250
        //return {x:100 y:50}
        return new GPosition(Math.abs(getPosX() - target.getPosX()), Math.abs(getPosY() - target.getPosY()));
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
