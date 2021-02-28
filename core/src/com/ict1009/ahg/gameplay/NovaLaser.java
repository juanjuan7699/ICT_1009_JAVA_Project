package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;

import static com.ict1009.ahg.screens.GameScreen.*;

public class NovaLaser extends Laser {

    public NovaLaser(Entity owner, int team) { //just make it shred everything, for fun factor
        super(owner, team);
        this.setMovementSpeed(45);
        this.setDamageScale(1.8f); //x30 times
        this.setSprite(newPlayerTextures[3]);
    }

    @Override
    public void applyOnHit(Entity target) { //temp texture        
        new OnHitAndExplosion(onHitTexture, new Rectangle (this.getBoundingBox()), 0.7f,100,100).addToRenderQueue();
    }
}
