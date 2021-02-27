package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

import static com.ict1009.ahg.screens.GameScreen.newPlayerTextures;
import static com.ict1009.ahg.screens.GameScreen.onHitSwarmTexture;

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
