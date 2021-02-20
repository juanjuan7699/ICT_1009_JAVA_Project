package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.GameScreen;

public class Animal extends Entity {
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
    public void onDestroy() {

    }

    @Override
    public void update(float deltaTime) {

    }
}
