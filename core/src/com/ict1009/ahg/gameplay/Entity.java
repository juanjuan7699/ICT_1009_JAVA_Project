package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.enums.EntityType;

public abstract class Entity {
    private Vector3 currentLocation; //2f
    private Vector3 targetLocation; //2f
    private Vector3 spawnLocation; //2f
    private EntityType entityType;

    private float currentHealth;
    private float maxHealth;
    private float damageScale;
    private float attackSpeed; //higher the faster ROF, -1 = cannot attack, 0 = tickrate attackspeed (infinite)
    private float movementSpeed; // movspeed
    private float healthRegen;

    private int team;
    private int attacks;

    private boolean movable; //check if object is supposed to move, "(wall) coded as minions"
    private boolean visible; //to draw on canvas (some entities can be invisible walls)
    private boolean combat; //is in combat?
    private boolean pendingRemoval;

    private String UID; //for networking
    private String name;

    private Rectangle boundingBox; //hitbox
    private TextureRegion sprite;

    public abstract void tryMove(int direction);
    public abstract void tryTeleport(Vector3 targetLocation);
    public abstract void addToRenderQueue();
    public abstract void onDestroy(Entity instigator); //explosions, death animations here
    public abstract void update(float deltaTime);

    public Entity() {
        this.visible = true;
    }

    public void draw(Batch batch) {
        if (!this.isVisible()) { //dont draw if invisible
            return;
        }
        batch.draw(this.getSprite(), this.getBoundingBox().x, this.getBoundingBox().y, this.getBoundingBox().width, this.getBoundingBox().height);
    }

    public void translate(float xChange, float yChange) {
        getBoundingBox().setPosition(getBoundingBox().x + xChange, getBoundingBox().y + yChange);
    }

    public Vector3 getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Vector3 currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Vector3 getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Vector3 targetLocation) {
        this.targetLocation = targetLocation;
    }

    public Vector3 getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Vector3 spawnLocation) {
        this.spawnLocation = spawnLocation;
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

    public void modifyHealth(float modifier) { //clamp
        if (this.currentHealth + modifier >= this.maxHealth) {//upper clamp
            this.currentHealth = this.maxHealth;
        }
        else if (this.currentHealth + modifier <= 0) { //lower clamp
            this.currentHealth = 0;
        }
        else {
            this.currentHealth += modifier;
        }
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
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

    public boolean isCombat() {
        return combat;
    }

    public void setCombat(boolean combat) {
        this.combat = combat;
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

    public float getDamageScale() {
        return damageScale;
    }

    public void setDamageScale(float damageScale) {
        this.damageScale = damageScale;
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public boolean isPendingRemoval() {
        return pendingRemoval;
    }

    public void setPendingRemoval(boolean pendingRemoval) {
        this.pendingRemoval = pendingRemoval;
    }

    public float getHealthRegen() {
        return healthRegen;
    }

    public void setHealthRegen(float healthRegen) {
        this.healthRegen = healthRegen;
    }

    public int getAttacks() {
        return attacks;
    }

    public void setAttacks(int attacks) {
        this.attacks = Math.min(5, attacks);
    }
}
