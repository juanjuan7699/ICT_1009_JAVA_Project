package structs;

import enums.PickupType;

public class Pickup extends Entity { //TODO todo

    private PickupType pickupType;
    private float scale; //scale on this.damage for the final buff power

    public Pickup() {
        super(EntityType.INTERACT_ENTITY);
    }
}
