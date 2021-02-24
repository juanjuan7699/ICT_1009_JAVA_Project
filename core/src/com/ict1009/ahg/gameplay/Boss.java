package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ict1009.ahg.screens.GameScreen;

import static com.ict1009.ahg.screens.GameScreen.*;
import static com.ict1009.ahg.screens.GameScreen.WORLD_HEIGHT;

public class Boss extends Animal {
    public Boss() {
        this.setMovementSpeed(Math.min(100, 24 + 3 * level));
        this.setMaxHealth(5 + 3 * level * level * level);
        this.modifyHealth(5 + 3 * level * level * level);
        this.setDamageScale(5 + 0.5f * level);
        this.setBoundingBox(new Rectangle(generator.nextFloat() * (WORLD_WIDTH - 10) + 5 - 10, WORLD_HEIGHT + 30 - 10, 30, 30));
//        this.setSprite(animalForestTextures[0]);
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

        //also drops one weapon per boss death
        new Pickup(this.getBoundingBox().getX(), this.getBoundingBox().getY(), 2, true).addToRenderQueue();
    }
}
