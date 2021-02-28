package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.enums.StatusType;
import com.ict1009.ahg.interfaces.IStatus;
import com.ict1009.ahg.screens.GameScreen;
import com.ict1009.ahg.interfaces.ICollidable;
import com.ict1009.ahg.interfaces.IDamageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.ict1009.ahg.screens.GameScreen.*;

public class Animal extends Entity implements ICollidable, IDamageHandler, IStatus {

    public Vector2 directionVector;
    private List<StatusType> statuses;

    public Animal() { //scaling per level
        this.setMovementSpeed(Math.min(100, 24 + 3 * level));
        this.setMaxHealth(5 + 3 * level);
        this.modifyHealth(5 + 3 * level);
        this.setDamageScale(1 + 0.5f * level);
        this.setBoundingBox(new Rectangle(generator.nextFloat() * (WORLD_WIDTH - 10) + 5 - 10, WORLD_HEIGHT + 30 - 10, 20, 20));
//        this.setSprite(animalForestTextures[0]);
        this.setSprite(GameScreen.getAnimalTexture());

        this.directionVector = new Vector2(0 ,-1);
        randomizeDirectionVector();

        this.statuses = new ArrayList<>();
        this.addStatus(StatusType.ALIVE);

        //additional power every 5 levels
        if (level/5 >= 1) { //max 300 health extra
            this.setMaxHealth((float) Math.min((getMaxHealth() + Math.pow(3, level/5)), 300)); //3,9,27,81,243

        }
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    public void randomizeDirectionVector() {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double bearing = generator.nextDouble() * 6.283185; // 0 to 2*Pi
                directionVector.x = (float)Math.sin(bearing);
                directionVector.y = (float)Math.cos(bearing);
            }
        }, 0, 4000);

        //find target enemy
    }

    @Override
    public void tryMove(int direction) {

    }

    @Override
    public void tryTeleport(Vector3 targetLocation) {

    }

    @Override
    public void addToRenderQueue() {
        GameScreen.mobs.add(this);
    }

    @Override
    public void onDestroy(Entity instigator) { //pending removal

        if (this.statuses.contains(StatusType.DOWNED)) {
            onHitAndExplosionList.add(new OnHitAndExplosion(explosionTexture, new Rectangle (this.getBoundingBox()), 0.7f,480,480));
            //Killed and obtain score
            score += 105 + 25 * level;
            levelScore += 105 + 25 * level;

            //instigator //add points to instigator or smth

            this.setPendingRemoval(true);
            this.statuses.add(StatusType.DEAD);

            //30% chance of dropping buff when dead
            int rng = generator.nextInt(10);
            if (rng >= 7) {
                new Pickup(false).addToRenderQueue();
            }
        }


    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public boolean collisionTest(Entity target) {
        return this.getBoundingBox().overlaps(target.getBoundingBox());
    }

    @Override
    public void takeDamage(float damage, int damageType, Entity instigator) {
        if (!this.hasStatus(StatusType.DOWNED) && !this.hasStatus(StatusType.DEAD)) {
            this.modifyHealth(-damage);
            onTakeDamage(instigator);
        }
    }

    @Override
    public void onTakeDamage(Entity instigator) {
        if (this.getCurrentHealth() <= 0) {
            this.addStatus(StatusType.DOWNED);
            this.removeStatus(StatusType.ALIVE);
            onDestroy(instigator);
        }
    }

    @Override
    public void addStatus(StatusType status) {
        this.statuses.add(status);
    }

    @Override
    public void removeStatus(StatusType status) {
        this.statuses.remove(status);
    }

    @Override
    public void removeStatus(int index) {
        this.statuses.remove(index);
    }

    @Override
    public void removeAllStatus() {
        this.statuses = new ArrayList<>();
    }

    @Override
    public boolean hasStatus(StatusType status) {
        return this.statuses.contains(status);
    }

    @Override
    public List<StatusType> getStatuses() {
        return this.statuses;
    }
}
