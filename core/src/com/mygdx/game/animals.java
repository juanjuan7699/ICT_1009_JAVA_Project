package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class animals {

    // animal characteristics
    float movementSpeed; // worldunits/second

    // Position & dimensions
    float xPosition, yPosition; // lower left corner
    float width, height;

    // Graphics
    TextureRegion animalTexture;

    public animals(float movementSpeed, float width, float height, float xCentre,
                   float yCentre, TextureRegion animalTexture) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xCentre - width/2;
        this.yPosition = yCentre - height/2;
        this.width = width;
        this.height = height;
        this.animalTexture = animalTexture;
    }

    public void draw(Batch batch) {
        batch.draw(animalTexture, xPosition, yPosition, width, height);

    }
}
