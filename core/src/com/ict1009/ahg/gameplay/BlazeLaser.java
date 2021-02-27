package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

import static com.ict1009.ahg.enums.StatusType.DOWNED;
import static com.ict1009.ahg.screens.GameScreen.newPlayerTextures;
import static com.ict1009.ahg.screens.GameScreen.onHitSwarmTexture;

public class BlazeLaser extends Laser {
    public BlazeLaser(Entity owner, int team) {
        super(owner, team);
        this.setMovementSpeed(300);
        this.setDamageScale(4);
        this.setSprite(newPlayerTextures[6]);
    }

    @Override
    public void applyOnHit(Entity target) { //freeze forever
        // super.applyOnHit(target);

        if (target instanceof Animal) {
            final Animal targett = (Animal)target;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (targett.hasStatus(DOWNED) || targett.isPendingRemoval()) {
                        this.cancel();
                        return;
                    }

                    targett.takeDamage(2, 0, getOwner()); //3dot
                    new OnHitAndExplosion(onHitSwarmTexture, new Rectangle(targett.getBoundingBox()), 0.15f,64,64).addToRenderQueue();
                }
            }, 300, 1000); //dot until enemy dies

        }
    }
}
