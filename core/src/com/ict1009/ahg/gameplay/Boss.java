package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ict1009.ahg.enums.StatusType;
import com.ict1009.ahg.screens.GameScreen;

import java.util.Timer;
import java.util.TimerTask;

import static com.ict1009.ahg.screens.GameScreen.*;
public class Boss extends Animal {
    public Boss() {
        this.setMovementSpeed(Math.min(100, 24 + 3 * level));
        this.setMaxHealth(5 + 3 * level * level * level);
        this.modifyHealth(5 + 3 * level * level * level);
        this.setDamageScale(5 + 0.5f * level);
        this.setBoundingBox(new Rectangle(generator.nextFloat() * (WORLD_WIDTH - 10) + 5 - 10, WORLD_HEIGHT + 30 - 10, 30, 30));
        this.setSprite(GameScreen.getBossTexture());

        super.directionVector = new Vector2(0 ,-1);
        randomizeDirectionVector();
    }

    @Override
    public void onDestroy(Entity instigator) { //boss spawns 5 pickups per kill
        super.onDestroy(instigator);

        for (int i = 0; i < 5; i++) {
            new Pickup(this.getBoundingBox().getX(), this.getBoundingBox().getY(), 2, false).addToRenderQueue();
        }

        //also drops two weapon per boss death
        new Pickup(this.getBoundingBox().getX(), this.getBoundingBox().getY(), 2, true).addToRenderQueue();
        new Pickup(this.getBoundingBox().getX(), this.getBoundingBox().getY(), 2, true).addToRenderQueue();

        //also increases all player health by 50 per boss kill
        for (final Player p : players) {
            p.setMaxHealth(p.getMaxHealth() + 50);
            p.modifyHealth(25); //heal half of the max health increment
            p.addStatus(StatusType.INVULNERABLE); //invul for 3 seconds after killing boss

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                   p.removeStatus(StatusType.INVULNERABLE);
                }
            }, 3000);
        }
    }
}
