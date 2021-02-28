package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

import static com.ict1009.ahg.screens.GameScreen.*;

public class SwarmLaser extends Laser {
    public SwarmLaser(Entity owner, int team) {
        super(owner, team);
        this.setMovementSpeed(300);
        this.setDamageScale(25);
        this.setSprite(newPlayerTextures[2]);
    }

    @Override
    public void applyOnHit(Entity target) { //freeze forever
        // super.applyOnHit(target);

        //animate bombs 5 times
        if (target instanceof Animal) {
            //apply x additional base damage (without modifiers or it becomes op) where x is owner's total bullet count
            for (int i = 0; i < getOwner().getAttacks(); i++) {
                ((Animal)target).takeDamage(this.getDamageScale(), 0, getOwner());
                final Entity targetT = target;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        new OnHitAndExplosion(onHitSwarmTexture, new Rectangle(targetT.getBoundingBox()), 0.15f,64,64).addToRenderQueue();
                    }
                }, 100 * i);

                System.out.println("exploding");
            }
        }
    }
}
