package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Laser {



    // Positions and dimensions
    float xPosition, yPosition; // Bottom centre of the laser
    float width, height;

    // Laser characteristics
    float movementSpeed; // worldunits/second

    // Graphics
    TextureRegion textureRegion;

    public Laser(float xPosition, float yPosition,
                 float width, float height, float movementSpeed,
                 TextureRegion textureRegion) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
        this.textureRegion = textureRegion;
    }

    public void draw(Batch batch) {
        batch.draw(textureRegion, xPosition - width/2, yPosition, width, height);
    }
}
