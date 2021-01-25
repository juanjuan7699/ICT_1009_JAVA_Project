package structs;

public class GPosition {
    private double posX;
    private double posY;

    public GPosition(double posX, double posY) { //is actually a Vector2f custom made

        this.setPosX(posX);
        this.setPosY(posY);
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
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
