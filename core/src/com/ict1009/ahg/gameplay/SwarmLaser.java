package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

import static com.ict1009.ahg.screens.GameScreen.explosionTexture;
import static com.ict1009.ahg.screens.GameScreen.playerTextures;

public class SwarmLaser extends Laser {
    public SwarmLaser(Entity owner, int team) {
        super(owner, team);
        this.setMovementSpeed(300);
        this.setDamageScale(25);
        this.setSprite(playerTextures[4]);
    }

    @Override
    public void applyOnHit(Entity target) { //freeze forever
        super.applyOnHit(target);

        //animate bombs 5 times
        if (target instanceof Animal) {
            //apply x additional base damage (without modifiers or it becomes op) where x is owner's total bullet count
            for (int i = 0; i < getOwner().getAttacks(); i++) {
                ((Animal)target).takeDamage(this.getDamageScale(), 0, getOwner());
                final Entity targett = target;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        new Explosion(explosionTexture, new Rectangle(targett.getBoundingBox()), 0.7f).addToRenderQueue();
                    }
                }, 100 * i);

                System.out.println("exploding");
            }
        }
    }
}
