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

    public Animal() {
        this.setMovementSpeed(48);
        this.setMaxHealth(20);
        this.modifyHealth(20);
        this.setDamageScale(5);
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
    public void usePickup(Entity pickup) {

    }

    @Override
    public void addToRenderQueue() {
        GameScreen.mobs.add(this);
    }

    @Override
    public void onDestroy() { //pending removal
        explosionList.add(new Explosion(explosionTexture, new Rectangle (this.getBoundingBox()), 0.7f));
        //Killed and obtain score
        score += 250;
        levelScore += 250;
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
    public void takeDamage(float damage, int damageType) {
        this.modifyHealth(-damage);
        onTakeDamage();
    }

    @Override
    public void onTakeDamage() {
        if (this.getCurrentHealth() <= 0) {
            onDestroy();
        }
        //blink animation?
    }
}
