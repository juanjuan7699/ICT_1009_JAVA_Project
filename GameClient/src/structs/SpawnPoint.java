package structs;

public class SpawnPoint {
    private GPosition position;
    private boolean occupied; //if there is an item already on it

    private String sprite; //change based on ally / enemy / pickup spawn

    public GPosition getPosition() {
        return position;
    }

    public void setPosition(GPosition position) {
        this.position = position;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
