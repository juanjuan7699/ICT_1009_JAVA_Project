package structs;

import java.util.Random;
import java.util.UUID;

enum EntityType { //to provide entity details
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

public class Entity {
    //parent class for Player and other units like Animals and probably bullets
    private GPosition currentLocation;
    private GPosition targetLocation; //you can lerp (linear interpolation) from currentLocation to targetLocation for e.g. homing missiles
    private GPosition spawnLocation;
    private HPosition hitBox;
    private EntityType entityType;

    private float currentHealth;
    private float maxHealth;
    private float damage;
    private float attackSpeed; //higher the faster ROF, -1 = cannot attack, 0 = tickrate attackspeed (infinite)

    private boolean movable; //check if object is supposed to move, "(wall) coded as minions"
    private boolean visible; //to draw on canvas (some entities can be invisible walls)
    private boolean collision; //generate hitbox
    private boolean combat; //is in combat?

    private String UID; //for networking
    private String name;
    private String sprite; //currently string, have to change to canvas or something, draw on LWJGL

    public Entity(EntityType entityType) { //generic
        this.entityType = entityType;
        this.spawnLocation = new GPosition(0,0);
        this.currentLocation = new GPosition(0,0);
        this.targetLocation = new GPosition(0,0);
        this.hitBox = new HPosition(0,0,0,0);

        this.currentHealth = 1;
        this.maxHealth = 1;
        this.damage = 0;
        this.attackSpeed = -1;

        this.movable = false;
        this.visible = false;
        this.collision = false;
        this.combat = false;

        this.UID = UUID.randomUUID().toString();
        this.name = UID;
        this.sprite = "not red ball";
    }

    public boolean hitTest(Entity target) { //check hitbox and the other object to see if it collides
        if (target.getHitBox() == this.getHitBox()) { //TODO: change to if target.hitBox is within our hitbox, then is true
            //do something
            return true;
        }
        return false;
    }

    public boolean tryTeleportToLocation(GPosition targetLocation) { //initial spawning or stuff
        //draw at target location
        //if teleport failed find suitable location etc
        return true;
    }

    public boolean tryMoveToTargetLocation() {
        this.currentLocation = this.targetLocation; //do lerping slowly
        return true;
    }

    public void destroyEntity(boolean animate) { //kills self
        this.movable = false;
        this.collision = false;

        //animation of dying?
        if (animate) {
            //doo animations before invisible
            this.visible = false; //run in after a delay
            return;
        }

        //undraw only, java will auto garbage collect
        this.visible = false; //instant vanish

    }

    public boolean isAlive() {
        return getCurrentHealth() > 0;
    }

    public GPosition getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(GPosition currentLocation) {
        this.currentLocation = currentLocation;
    }

    public GPosition getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(GPosition targetLocation) {
        this.targetLocation = targetLocation;
    }

    public GPosition getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(GPosition spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public HPosition getHitBox() {
        return hitBox;
    }

    public void setHitBox(HPosition hitBox) {
        this.hitBox = hitBox;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public void updateHealth(float incHealth) { //with clamp
        if (this.currentHealth + incHealth <= 0) {
            this.currentHealth = 0;
            return;
        }
        if (this.currentHealth + incHealth >= this.maxHealth) {
            this.currentHealth = this.maxHealth;
            return;
        }
        this.currentHealth += incHealth;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getHealthPercent() {
        return currentHealth/maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public boolean isCombat() {
        return combat;
    }

    public void setCombat(boolean combat) {
        this.combat = combat;
    }
}
