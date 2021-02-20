package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.GameScreen;
import com.ict1009.ahg.interfaces.ICollidable;
import com.ict1009.ahg.interfaces.IDamageHandler;

import static com.ict1009.ahg.GameScreen.*;

public class Animal extends Entity implements ICollidable, IDamageHandler {

    private Vector2 directionVector;

    public Animal() { //scaling per level
        this.setMovementSpeed(Math.min(100, 24 + 3 * level));
        this.setMaxHealth(5 + 3 * level);
        this.modifyHealth(5 + 3 * level);
        this.setDamageScale(1 + 0.5f * level);
        this.setBoundingBox(new Rectangle(generator.nextFloat() * (WORLD_WIDTH - 10) + 5 - 10, WORLD_HEIGHT + 30 - 10, 20, 20));
        this.setSprite(animalForestTextures[0]);

        this.directionVector = new Vector2(0 ,-1);
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomizeDirectionVector() {
        double bearing = generator.nextDouble() * 6.283185; // 0 to 2*Pi
        directionVector.x = (float)Math.sin(bearing);
        directionVector.y = (float)Math.cos(bearing);
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
        explosionList.add(new Explosion(explosionTexture, new Rectangle (this.getBoundingBox()), 0.7f));
        //Killed and obtain score
        score += 105 + 25 * level;
        levelScore += 105 + 25 * level;

        //instigator //add points to instigator or smth

        this.setPendingRemoval(true);
    }

    @Override
    public void update(float deltaTime) {
        randomizeDirectionVector();
    }

    @Override
    public boolean collisionTest(Entity target) {
        return this.getBoundingBox().overlaps(target.getBoundingBox());
    }

    @Override
    public void takeDamage(float damage, int damageType, Entity instigator) {
        this.modifyHealth(-damage);
        onTakeDamage(instigator);
    }

    @Override
    public void onTakeDamage(Entity instigator) {
        if (this.getCurrentHealth() <= 0) {
            onDestroy(instigator);
        }
        //blink animation?
    }
}
