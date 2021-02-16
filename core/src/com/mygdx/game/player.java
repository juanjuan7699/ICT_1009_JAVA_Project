package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class player {

    // Player characteristics
    float movementSpeed; // worldunits/second

    // Position & dimensions
    float xPosition, yPosition; // lower left corner
    float width, height;

    // Graphics
    TextureRegion playerTexture;

    public player(float movementSpeed, float width, float height, float xCentre,
                  float yCentre, TextureRegion playerTexture) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xCentre - width/2;
        this.yPosition = yCentre - height/2;
        this.width = width;
        this.height = height;
        this.playerTexture = playerTexture;
    }

    public void draw(Batch batch) {
        batch.draw(playerTexture, xPosition, yPosition, width, height);

    }
}
