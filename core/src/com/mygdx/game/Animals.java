package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Animals extends Entity{

    public Animals(float movementSpeed, float width, float height, float xCentre,
                   float yBottom, TextureRegion entityTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.boundingBox = new Rectangle(xCentre - width / 2, yBottom - height, width, height);
        this.entityTextureRegion = entityTextureRegion;
    }

    public void draw(Batch batch) {
        batch.draw(entityTextureRegion, boundingBox.x, boundingBox.y - boundingBox.height, boundingBox.width, boundingBox.height);
    }
}
