package com.ict1009.ahg.gameplay;

import static com.ict1009.ahg.screens.GameScreen.playerTextures;

public class StasisLaser extends Laser {

    public StasisLaser(Entity owner, int team) {
        super(owner, team);
        this.setMovementSpeed(150);
        this.setDamageScale(3);
        this.setSprite(playerTextures[2]);
    }

    @Override
    public void applyOnHit(Entity target) { //freeze forever
        super.applyOnHit(target);
        target.setMovementSpeed(0f);
    }
}
