package structs;

public class GPosition {
    private float posX;
    private float posY;

    public GPosition(float posX, float posY) {

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
