package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.ict1009.ahg.screens.GameScreen;

public class OnHitAndExplosion extends Entity {

    private final Animation<TextureRegion> onHitAndExplosionAnimation;
    private float onHitAndExplosionTimer;


    public OnHitAndExplosion(Texture texture, Rectangle boundingBox, float totalAnimationTime, int width, int height){
        this.setBoundingBox(boundingBox);

        onHitAndExplosionAnimation = new Animation<>(totalAnimationTime / 16, splitTexture(texture, width, height));
        onHitAndExplosionTimer = 0;
    }

    public TextureRegion[] splitTexture(Texture texture, int width, int height) {
        //split texture
        TextureRegion[][] textureRegion2D = TextureRegion.split(texture, width, height);

        //convert to 1D array
        TextureRegion[] textureRegion1D = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
                textureRegion1D[index] = textureRegion2D[i][j];
                index++;
            }
        }        
        return textureRegion1D;
    }

    @Override
    public void update(float deltaTime){
        onHitAndExplosionTimer += deltaTime;
    }

    public boolean isFinished(){
        return onHitAndExplosionAnimation.isAnimationFinished(onHitAndExplosionTimer);
    }

    @Override
    public void addToRenderQueue() {
        GameScreen.onHitAndExplosionList.add(this);
    }

    @Override
    public void onDestroy(Entity instigator) {

    }

    @Override
    public void draw(Batch batch) {
        batch.draw(onHitAndExplosionAnimation.getKeyFrame(onHitAndExplosionTimer),
                this.getBoundingBox().x,
                this.getBoundingBox().y,
                this.getBoundingBox().width,
                this.getBoundingBox().height);
    }
}
