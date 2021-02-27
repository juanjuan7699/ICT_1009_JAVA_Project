package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;

import static com.ict1009.ahg.screens.GameScreen.*;

public class NovaLaser extends Laser {
    public NovaLaser(Entity owner, int team) {
        super(owner, team);
        this.setMovementSpeed(45);
        this.setDamageScale(0.6f); //it hits about alot of times
        this.setSprite(newPlayerTextures[3]);
    }

    @Override
    public void applyOnHit(Entity target) { //temp texture
        //new OnHitAndExplosion(onHitSwarmTexture, new Rectangle(target.getBoundingBox()), 0.3f,96,96).addToRenderQueue();
        new OnHitAndExplosion(onHitTexture, new Rectangle (this.getBoundingBox()), 0.7f,100,100).addToRenderQueue();

    }
}
