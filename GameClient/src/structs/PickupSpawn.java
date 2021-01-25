package structs;

import java.util.Random;

public class PickupSpawn extends SpawnPoint {
    private PickupType pickupType; //should only spawn the buffs
    private float respawnDuration;
    private boolean recurring; //recurring or single spawn pickup
    private double scale; //scale of the pickup

    PickupSpawn() {
        super();
        this.setScale(1.0 + new Random().nextInt(5)/10.0);
    }

    public PickupType getPickupType() {
        return pickupType;
    }

    public void setPickupType(PickupType pickupType) {
        this.pickupType = pickupType;
    }

    public float getRespawnDuration() {
        return respawnDuration;
    }

    public void setRespawnDuration(float respawnDuration) {
        this.respawnDuration = respawnDuration;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
