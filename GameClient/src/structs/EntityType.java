package structs;

public enum EntityType { //to provide entity details
    PLAYER_ENTITY(0),
    ANIMAL_ENTITY(1),
    BULLET_ENTITY(2),
    INTERACT_ENTITY(3), //for maybe health pickups or dropped loot etc
    OBJECT_ENTITY(4), //respawnable walls, destructibles or stuff that can block bullets but not move
    UNKNOWN(5);

    public final int entityNo;

    EntityType(int entityNo) {
        this.entityNo = entityNo;
    }
}
