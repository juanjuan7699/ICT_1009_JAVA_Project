package com.ict1009.ahg.gameplay;

import static com.ict1009.ahg.screens.GameScreen.newPlayerTextures;

public class NanoLaser extends Laser {
    public NanoLaser(Entity owner, int team) {
        super(owner, team);
        this.setMovementSpeed(300);
        this.setDamageScale(8);
        this.setSprite(newPlayerTextures[7]);
    }

    @Override
    public void applyOnHit(Entity target) { //healing
        getOwner().modifyHealth(Math.min((getDamageScale() * getOwner().getDamageScale())/3, 25)); //min 3 max 25
    }
}
