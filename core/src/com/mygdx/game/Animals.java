package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Animals extends Entity{

    Vector2 directionVector;
    float timeSinceLastDirectionChange = 0;
    float directionChangeFrequency = 0.75f;

    public Animals(float movementSpeed, int health, 
                   float width, float height, 
                   float xCentre, float yBottom, 
                   float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots, 
                   TextureRegion entityTextureRegion, TextureRegion laserTextureRegion ) {
        this.movementSpeed = movementSpeed;
        this.health = health;
        this.boundingBox = new Rectangle(xCentre - width / 2, yBottom - height, width, height);
        this.laserHeight = laserHeight;
        this.laserWidth = laserWidth;
        this.timeBetweenShots = timeBetweenShots;
        this.laserMovementSpeed = laserMovementSpeed;        
        this.entityTextureRegion = entityTextureRegion;
        this.laserTextureRegion = laserTextureRegion;
        
        directionVector = new Vector2(0, -1);
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomizeDirectionVector() {
        double bearing = MyGdxGame.random.nextDouble() * 6.283185; // 0 to 2*Pi
        directionVector.x = (float)Math.sin(bearing);
        directionVector.y = (float)Math.cos(bearing);
    }

    public Laser[] fireLasers() {
        Laser[] laser = new Laser[1];
        laser[0] = new Laser(boundingBox.x + boundingBox.width *.72f, boundingBox.y - boundingBox.height ,
                laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);

        timeSinceLastShot = 0;

        return laser;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastShot += deltaTime;
        timeSinceLastDirectionChange += deltaTime;
        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }        
    }

    public void draw(Batch batch) {
        batch.draw(entityTextureRegion, boundingBox.x, boundingBox.y - boundingBox.height, boundingBox.width, boundingBox.height);
    }
}
