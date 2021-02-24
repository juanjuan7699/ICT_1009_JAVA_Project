package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.screens.GameScreen;

public class Explosion extends Entity {

    private Animation<TextureRegion> explosionAnimation;
    private float explosionTimer;

    private Rectangle boundingBox;

    public Explosion(Texture texture, Rectangle boundingBox, float totalAnimationTime){
        this.boundingBox = boundingBox;

        //split texture
        TextureRegion[][] textureRegion2D = TextureRegion.split(texture, 480 , 480);

        //convert to 1D array
        TextureRegion[] textureRegion1D = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
                textureRegion1D[index] = textureRegion2D[i][j];
                index++;
            }
        }

        explosionAnimation = new Animation<>(totalAnimationTime / 16, textureRegion1D);
        explosionTimer = 0;
    }

    @Override
    public void update(float deltaTime){
        explosionTimer += deltaTime;
    }

    public boolean isFinished(){
        return explosionAnimation.isAnimationFinished(explosionTimer);
    }


    @Override
    public void tryMove(int direction) {
        //null
    }

    @Override
    public void tryTeleport(Vector3 targetLocation) {
        //null
    }

    @Override
    public void addToRenderQueue() {
        GameScreen.explosionList.add(this);
    }

    @Override
    public void onDestroy(Entity instigator) {

    }

    @Override
    public void draw(Batch batch) {
        batch.draw(explosionAnimation.getKeyFrame(explosionTimer),
                boundingBox.x,
                boundingBox.y,
                boundingBox.width,
                boundingBox.height);
    }
}
