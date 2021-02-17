package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity{

    public Player(float xCentre, float yCentre, 
                  float width, float height, 
                  float movementSpeed, int health, int lives,
                  float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots,
                  TextureRegion entityTextureRegion, TextureRegion laserTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.health = health;
        this.lives = lives;
        this.boundingBox = new Rectangle(xCentre - width / 2, yCentre - height / 2, width, height);
        this.laserHeight = laserHeight;
        this.laserWidth = laserWidth;
        this.timeBetweenShots = timeBetweenShots;
        this.laserMovementSpeed = laserMovementSpeed;
        this.entityTextureRegion = entityTextureRegion;
        this.laserTextureRegion = laserTextureRegion;        
    }

    public Laser[] fireLasers() {
        Laser[] laser = new Laser[1];
        laser[0] = new Laser(boundingBox.x + boundingBox.width *.72f, boundingBox.y + boundingBox.height *.98f,
                laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);

        timeSinceLastShot = 0;

        return laser;
    }

    public void update(float deltaTime) {
        timeSinceLastShot += deltaTime;
    }
}
