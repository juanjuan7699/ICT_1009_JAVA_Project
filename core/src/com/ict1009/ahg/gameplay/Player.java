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

    public Player() {
        this.weapon = new Laser(this, 0);
        this.setSprite(GameScreen.textureAtlas.findRegion("soldier1_gun"));
        this.setMaxHealth(100);
        this.modifyHealth(100);
        this.setMovementSpeed(48);
        this.setBoundingBox(new Rectangle(WORLD_WIDTH/2 - 5, WORLD_HEIGHT/2 - 5, 10, 10));
    }

    @Override
    public void collisionTest(Rectangle target) {

    }

    @Override
    public void takeDamage(float damage, int damageType) {
        this.modifyHealth(-damage);
        //do something else when it gets damaged
    }

    @Override
    public void onTakeDamage() {
        //show hurt etc
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
        GameScreen.players.add(this);
    }

    @Override
    public void onDestroy() { //what happens when the player dies

    }

    @Override
    public void update(float deltaTime) {

    }

}
