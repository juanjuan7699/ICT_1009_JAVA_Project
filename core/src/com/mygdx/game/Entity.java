package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Entity {

    // Characteristics
    float movementSpeed; // worldunits/second

    // Position & dimensions
    Rectangle boundingBox;

    // Laser information
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

    // Graphics
    TextureRegion entityTextureRegion, laserTextureRegion;

    public void update(float deltaTime) {

    }

    public boolean canFireLaser() {
        return (timeSinceLastShot - timeBetweenShots >= 0);
    }

    public boolean intersects(Rectangle otherRectangle) {
        return boundingBox.overlaps(otherRectangle);
    }

    public void hit(Laser laser) {
    }

    public void draw(Batch batch) {
        batch.draw(entityTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
