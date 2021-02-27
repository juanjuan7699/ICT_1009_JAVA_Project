package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;

import static com.ict1009.ahg.screens.GameScreen.*;

public class StasisLaser extends Laser {

    public StasisLaser(Entity owner, int team) {
        super(owner, team);
        this.setMovementSpeed(150);
        this.setDamageScale(3);
        this.setSprite(newPlayerTextures[4]);
    }

    @Override
    public void applyOnHit(Entity target) { //freeze forever
        new OnHitAndExplosion(onHitStasisTexture, new Rectangle (target.getBoundingBox()), 0.3f,96,96).addToRenderQueue();
        target.setMovementSpeed(0f);
    }
}
