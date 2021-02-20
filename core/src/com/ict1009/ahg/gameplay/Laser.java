package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.GameScreen;

public class Laser extends Entity {

    private Entity owner;
    private int team; //0 = players, 1 = animals

    public Laser(Entity owner, int team) {
        this.owner = owner;
        this.team = team;
        this.setMovementSpeed(1);
        this.setSprite(GameScreen.textureAtlas.findRegion("laserRed12")); //defaults
        this.setBoundingBox();
    }

    @Override
    public void tryMove(int direction) { //up or down?

    }

    @Override
    public void tryTeleport(Vector3 targetLocation) {

    }

    @Override
    public void usePickup(Entity pickup) {

    }

    @Override
    public void addToRenderQueue() {
        GameScreen.renderQueue.add(this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void update(float deltaTime) {

    }
}
