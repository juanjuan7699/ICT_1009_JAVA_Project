package structs;

enum PickupType {
    DAMAGE_BUFF,
    MOVEMENT_BUFF,
    ROF_BUFF,
    HEALTHPACK,

    //can have one for each weapon pickup
    BULLET_PROJECTILE,
    BULLET_HITSCAN,
    BULLET_LASER,

    //ammo if required
    AMMO
}

public class Pickup extends Entity { //TODO todo

    private PickupType pickupType;
    private float scale; //scale on this.damage for the final buff power

    public Pickup() {
        super(EntityType.INTERACT_ENTITY);
    }
}
