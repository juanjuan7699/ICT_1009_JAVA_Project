package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;

class GameScreen implements Screen {

    //Screen
    private Camera camera;
    private Viewport viewport;

    //Graphics
    private SpriteBatch batch;
    //	private TextureAtlas textureAtlas;
//	private Texture background;
    private TextureAtlas textureAtlas;
    private float backgroundHeight; //  of background in world units
    private TextureRegion[] backgrounds;
    private TextureRegion playerTextureRegion, bearTextureRegion, crocTextureRegion, duckTextureRegion, goatTextureRegion,
            laserTextureRegion, pigTextureRegion, rabbitTextureRegion, snakeTextureRegion;


    //Timing
//	private int backgroundOffset;
    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;

    //World parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    // Game Objects
    private Animals enemyAnimal;
    private Player player;
    private LinkedList<Laser> laserLinkedList;

    GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

//		background = new Texture("darkPurpleStarscape.png");
//		backgroundOffset = 0;

        // Setup texture atlas
        textureAtlas = new TextureAtlas("images.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        // init texture regions
        playerTextureRegion = textureAtlas.findRegion("soldier1_gun");
        bearTextureRegion = textureAtlas.findRegion("bear");
//		crocTextureRegion = textureAtlas.findRegion("crocodile");
//		duckTextureRegion = textureAtlas.findRegion("duck");
//		goatTextureRegion = textureAtlas.findRegion("goat");
//		pigTextureRegion = textureAtlas.findRegion("pig");
//		rabbitTextureRegion = textureAtlas.findRegion("rabbit");
//		snakeTextureRegion = textureAtlas.findRegion("snake");
        laserTextureRegion = textureAtlas.findRegion("laserOrange02");

        // Setup game objects
        player = new Player(WORLD_WIDTH / 2, WORLD_HEIGHT / 4, 10,
                10, 2,
                0.4f, 4, 90, .5f,
                playerTextureRegion, laserTextureRegion);

        enemyAnimal = new Animals(2, 10, 10, WORLD_WIDTH / 2, WORLD_HEIGHT * 3 / 4, bearTextureRegion);

        laserLinkedList = new LinkedList<>();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();

        player.update(deltaTime);
        enemyAnimal.update(deltaTime);

        //Scrolling background
		renderBackground(deltaTime);

        // Animals
        enemyAnimal.draw(batch);

        // Player
        player.draw(batch);
        player.draw(batch);

        // Lasers
        renderLasers(deltaTime);

        //Detect collisions
        detectCollisions();

        // Explosions
//        renderExplosions(deltaTime);

        batch.end();
    }

    private void detectCollisions(){
        //Check if laser intersects animal
        ListIterator<Laser> iterator = laserLinkedList.listIterator();
        while(iterator.hasNext()) {
            Laser laser = iterator.next();
            if(enemyAnimal.intersects(laser.boundingBox)){
                // Touches animal
                enemyAnimal.hit(laser);
                iterator.remove();
            }
        }
    }

//    private void renderExplosions(float deltaTime){
//
//    }


    private void renderLasers(float deltaTime){
        // Create new lasers
        if (player.canFireLaser()) {
            Laser[] lasers = player.fireLasers();
            for (Laser laser : lasers) {
                laserLinkedList.add(laser);
            }

        }
        // Draw lasers
        // Remove old lasers
        ListIterator<Laser> iterator = laserLinkedList.listIterator();
        while(iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y += laser.movementSpeed*deltaTime;
            if (laser.boundingBox.y + laser.boundingBox.height < 0) {
                iterator.remove();
            }
        }
    }

    private void renderBackground(float deltaTime) {

        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }

            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer], WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer] + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {

    }

}
