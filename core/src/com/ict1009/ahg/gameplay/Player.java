package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.GameScreen;
import com.ict1009.ahg.interfaces.ICollidable;
import com.ict1009.ahg.interfaces.IDamageHandler;

import static com.ict1009.ahg.GameScreen.WORLD_HEIGHT;
import static com.ict1009.ahg.GameScreen.WORLD_WIDTH;

public class Player extends Entity implements ICollidable, IDamageHandler {

    private Laser weapon;
    private float timeSinceLastShot;
    private boolean invulnerable;

    public Player() {
        this.setSprite(GameScreen.textureAtlas.findRegion("soldier1_gun"));
        this.setMaxHealth(100);
        this.modifyHealth(100);
        this.setMovementSpeed(48);
        this.setBoundingBox(new Rectangle(WORLD_WIDTH/2 - 5, WORLD_HEIGHT/2 - 5, 10, 10));
        this.setDamageScale(1);
        this.weapon = new Laser(this, 0);
        this.timeSinceLastShot = 0;
        this.setAttackSpeed(1.45f);
        this.setHealthRegen(.01f);
        this.setAttacks(1);
    }

    public void resetBuffs() {
        this.setMaxHealth(100);
        this.modifyHealth(100);
        this.setMovementSpeed(48);
        this.setDamageScale(1);
        this.setHealthRegen(.01f);
        this.setAttacks(1);
    }

    @Override
    public boolean collisionTest(Entity target) {
        return !invulnerable && this.getBoundingBox().overlaps(target.getBoundingBox()); //not invulnerable and overlap = get damage
    }

    @Override
    public void takeDamage(float damage, int damageType, Entity instigator) {
        this.modifyHealth(-damage);
        //do something else when it gets damaged
        onTakeDamage(instigator);
    }

    @Override
    public void onTakeDamage(Entity instigator) {
        //show hurt etc
        if (getCurrentHealth() <= 0) {
            //downed state or die
            //this.onDestroy(instigator);
        }
    }

    @Override
    public void tryMove(int direction) {

    }

    @Override
    public void tryTeleport(Vector3 targetLocation) {

    }

    @Override
    public void addToRenderQueue() {
        GameScreen.players.add(this);
    }

    @Override
    public void onDestroy(Entity instigator) { //what happens when the player dies

    }

    @Override
    public void update(float deltaTime) {

        timeSinceLastShot += deltaTime;
        this.modifyHealth(this.getHealthRegen());
    }

    public Laser[] attack() {
        Laser[] laser = new Laser[this.getAttacks()];

        for (int i = 0; i < this.getAttacks(); i++) {
            laser[i] = new Laser(this, 0);

            if (i >= 1 && i % 2 == 0) { //even
                laser[i].setBoundingBox(new Rectangle(this.getBoundingBox().x + this.getBoundingBox().width *.72f + 4f * i/2, this.getBoundingBox().y + this.getBoundingBox().height *.98f,1,4));
            }
            else if (i >= 1) { //odd
                laser[i].setBoundingBox(new Rectangle(this.getBoundingBox().x + this.getBoundingBox().width *.72f - 2.5f - 3f * i/2, this.getBoundingBox().y + this.getBoundingBox().height *.98f,1,4));
            }
        }

        timeSinceLastShot = 0;
        return laser;
    }

    public boolean canFireLaser() {
        return (timeSinceLastShot - this.getAttackSpeed() >= 0);
    }

}
