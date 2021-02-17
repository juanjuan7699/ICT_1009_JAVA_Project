package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private TextureRegion playerTextureRegion, player2TextureRegion, bearTextureRegion, crocTextureRegion, duckTextureRegion, goatTextureRegion,
            laserTextureRegion, laser2TextureRegion, pigTextureRegion, rabbitTextureRegion, snakeTextureRegion;


    //Timing
//	private int backgroundOffset;
    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;
    private float timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer = 0;

    //World parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    // Game Objects
    private LinkedList<Animals> enemyAnimalList;
    private Player player;
    private Player player2;
    private LinkedList<Laser> laserLinkedList;
    private LinkedList<Laser> laser2LinkedList;

    GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

//		background = new Texture("darkPurpleStarscape.png");
//		backgroundOffset = 0;

        // Setup texture atlas
        textureAtlas = new TextureAtlas("images.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("grassBackground");
        backgrounds[1] = textureAtlas.findRegion("grassBackground");
        backgrounds[2] = textureAtlas.findRegion("grassBackground");
        backgrounds[3] = textureAtlas.findRegion("grassBackground");
//        backgrounds[0] = textureAtlas.findRegion("Starscape00");
//        backgrounds[1] = textureAtlas.findRegion("Starscape01");
//        backgrounds[2] = textureAtlas.findRegion("Starscape02");
//        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        // init texture regions
        playerTextureRegion = textureAtlas.findRegion("soldier1_gun");
        player2TextureRegion = textureAtlas.findRegion("manBlue_gun");
        bearTextureRegion = textureAtlas.findRegion("bear");
		crocTextureRegion = textureAtlas.findRegion("crocodile");
//		duckTextureRegion = textureAtlas.findRegion("duck");
//		goatTextureRegion = textureAtlas.findRegion("goat");
//		pigTextureRegion = textureAtlas.findRegion("pig");
//		rabbitTextureRegion = textureAtlas.findRegion("rabbit");
//		snakeTextureRegion = textureAtlas.findRegion("snake");
        laserTextureRegion = textureAtlas.findRegion("laserRed12");
        laser2TextureRegion = textureAtlas.findRegion("laserBlue12"); // Change this value if setting player 2 laser to another colour

        // Setup game objects
        player = new Player(WORLD_WIDTH / 2, WORLD_HEIGHT / 4, 10,
                10, 48,
                1f, 4, 90, .5f,
                playerTextureRegion, laserTextureRegion);

        // player 2
        player2 = new Player(WORLD_WIDTH / 2, WORLD_HEIGHT / 4, 10,
                10, 48,
                1f, 4, 90, .5f,
                player2TextureRegion, laser2TextureRegion);

        enemyAnimalList = new LinkedList<>();

        laserLinkedList = new LinkedList<>();
        laser2LinkedList = new LinkedList<>();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();

        //Scrolling background
        renderBackground(deltaTime);

        detectInput(deltaTime);
        player.update(deltaTime);
        player2.update(deltaTime);

        spawnEnemyAnimals(deltaTime);

        ListIterator<Animals> enemyAnimalListIterator = enemyAnimalList.listIterator();
        while (enemyAnimalListIterator.hasNext()) {
            Animals enemyAnimal = enemyAnimalListIterator.next();
            moveEnemy(enemyAnimal, deltaTime);
            enemyAnimal.update(deltaTime);
            // Animals
            enemyAnimal.draw(batch);
        }

        // Player
        player.draw(batch);
        player2.draw(batch);

        // Lasers
        renderLasers(deltaTime);
        renderLasers2(deltaTime);

        //Detect collisions
        detectCollisions();

        // Explosions
//        renderExplosions(deltaTime);

        batch.end();
    }

    private void spawnEnemyAnimals(float deltaTime) {
        enemySpawnTimer += deltaTime;

        if (enemySpawnTimer > timeBetweenEnemySpawns) {
            enemyAnimalList.add(new Animals(48, 10,
                    10, MyGdxGame.random.nextFloat() * (WORLD_WIDTH - 10) + 5,
                    WORLD_HEIGHT - 5, bearTextureRegion));
            enemySpawnTimer -= timeBetweenEnemySpawns;
        }
    }

    private void detectInput(float deltaTime) {
        // Keyboard Input

        // Strategy: determine the max distance the ship can move
        // Check each key that matters and move accordingly

        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -player.boundingBox.x;
        downLimit = -player.boundingBox.y;
        rightLimit = WORLD_WIDTH - player.boundingBox.x - player.boundingBox.width;
        upLimit = (float)WORLD_HEIGHT/2 - player.boundingBox.y - player.boundingBox.height;

        float leftLimit2, rightLimit2, upLimit2, downLimit2;
        leftLimit2 = -player2.boundingBox.x;
        downLimit2 = -player2.boundingBox.y;
        rightLimit2 = WORLD_WIDTH - player2.boundingBox.x - player2.boundingBox.width;
        upLimit2 = (float)WORLD_HEIGHT/2 - player2.boundingBox.y - player2.boundingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0) {
//            float xChange = player.movementSpeed * deltaTime;
//            xChange = Math.min(xChange, rightLimit);
//            player.translate(xChange, 0f);

            player.translate(Math.min(player.movementSpeed * deltaTime, rightLimit), 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0) {
            player.translate(0f, Math.min(player.movementSpeed * deltaTime, upLimit));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0) {
            player.translate(Math.max(-player.movementSpeed * deltaTime, leftLimit), 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0) {
            player.translate(0f, Math.max(-player.movementSpeed * deltaTime, downLimit));
        }

        //Player 2
        if (Gdx.input.isKeyPressed(Input.Keys.D) && rightLimit2 > 0) {
//            float xChange = player.movementSpeed * deltaTime;
//            xChange = Math.min(xChange, rightLimit);
//            player.translate(xChange, 0f);

            player2.translate(Math.min(player2.movementSpeed * deltaTime, rightLimit2), 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && upLimit2 > 0) {
            player2.translate(0f, Math.min(player2.movementSpeed * deltaTime, upLimit2));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && leftLimit2 < 0) {
            player2.translate(Math.max(-player2.movementSpeed * deltaTime, leftLimit2), 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) && downLimit2 < 0) {
            player2.translate(0f, Math.max(-player2.movementSpeed * deltaTime, downLimit2));
        }

        // Touch Input (Mouse)
    }


    private void moveEnemy(Animals enemyAnimal, float deltaTime) {
        // Strategy: determine the max distance the ship can move

        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyAnimal.boundingBox.x;
        downLimit = (float)WORLD_HEIGHT/2 - enemyAnimal.boundingBox.y;
        rightLimit = WORLD_WIDTH - enemyAnimal.boundingBox.x - enemyAnimal.boundingBox.width;
        upLimit = WORLD_HEIGHT - enemyAnimal.boundingBox.y - enemyAnimal.boundingBox.height;

        float xMove = enemyAnimal.getDirectionVector().x * enemyAnimal.movementSpeed * deltaTime;
        float yMove = enemyAnimal.getDirectionVector().y * enemyAnimal.movementSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove,leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove,downLimit);

        enemyAnimal.translate(xMove,yMove);
    }

    private void detectCollisions(){
        //Check if laser intersects animal
        ListIterator<Laser> laserListIterator = laserLinkedList.listIterator();
        while(laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<Animals> enemyAnimalListIterator = enemyAnimalList.listIterator();
            while (enemyAnimalListIterator.hasNext()) {
                Animals enemyAnimal = enemyAnimalListIterator.next();

                if (enemyAnimal.intersects(laser.boundingBox)){
                    // Touches animal
                    enemyAnimal.hit(laser);
                    laserListIterator.remove();
                    break;
                }
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

    private void renderLasers2(float deltaTime){
        // Create new lasers
        if (player2.canFireLaser()) {
            Laser[] lasers2 = player2.fireLasers();
            for (Laser laser2 : lasers2) {
                laser2LinkedList.add(laser2);
            }
        }
        // Draw lasers
        // Remove old lasers
        ListIterator<Laser> iterator = laser2LinkedList.listIterator();
        while(iterator.hasNext()) {
            Laser laser2 = iterator.next();
            laser2.draw(batch);
            laser2.boundingBox.y += laser2.movementSpeed*deltaTime;
            if (laser2.boundingBox.y + laser2.boundingBox.height < 0) {
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
