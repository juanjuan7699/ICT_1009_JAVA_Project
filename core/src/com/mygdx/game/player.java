package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class player {

    // Player characteristics
    float movementSpeed; // worldunits/second

    // Position & dimensions
    float xPosition, yPosition; // lower left corner
    float width, height;

    // Laser information
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

    // Graphics
    TextureRegion playerTextureRegion, laserTextureRegion;

    public player(float xCentre,
                  float yCentre, float width, float height, float movementSpeed,
                  float laserWidth, float laserHeight, float laserMovementSpeed,
                  float timeBetweenShots,
                  TextureRegion playerTexture, TextureRegion laserTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.laserHeight = laserHeight;
        this.laserWidth = laserWidth;
        this.timeBetweenShots = timeBetweenShots;
        this.laserMovementSpeed = laserMovementSpeed;
        this.playerTextureRegion = playerTexture;
        this.laserTextureRegion = laserTextureRegion;
    }

    public void update(float deltaTime) {

        timeSinceLastShot += deltaTime;

    }

    public boolean canFireLaser() {
        return (timeSinceLastShot - timeBetweenShots >= 0);
    }

    public Laser[] fireLasers() {
        Laser[] laser = new Laser[1];
        laser[0] = new Laser(xPosition + width*.72f, yPosition + height*.98f,
                laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);

        timeSinceLastShot = 0;

        return laser;
    }


    public void draw(Batch batch) {
        batch.draw(playerTextureRegion, xPosition, yPosition, width, height);

    }
}
