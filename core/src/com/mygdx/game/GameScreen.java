package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Random;

class GameScreen implements Screen {

    //Screen
    private Camera camera;
    private Viewport viewport;

    //Graphics
    private SpriteBatch batch;
    //	private TextureAtlas textureAtlas;
//	private Texture background;
    private TextureAtlas textureAtlas;
    private Texture explosionTexture; 
    private float backgroundHeight; //  of background in world units
    private TextureRegion[] backgrounds;
    private TextureRegion playerTextureRegion, player2TextureRegion, bearTextureRegion, crocTextureRegion, duckTextureRegion, goatTextureRegion,
            laserTextureRegion, laser2TextureRegion,enemyLaserTextureRegion, pigTextureRegion, rabbitTextureRegion, snakeTextureRegion,
            elephantTextureRegion, lionTextureRegion, gorillaTextureRegion, camelTextureRegion;


    //Timing
//	private int backgroundOffset;
    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;
    private float timeBetweenEnemySpawns = 3f;
    private float timeBetweenDamage = 2;
    private float timeBetweenNewMap = 30f;
    private float enemySpawnTimer = 0;
    private float damageTimer = 0;
    private float timeElapsed = 0;

    //World parameters
    private final float WORLD_WIDTH = 72;
    private final float WORLD_HEIGHT = 128;

    // Game Objects
    private LinkedList<Animals> enemyAnimalList;
    private Player player;
    private Player player2;
    private LinkedList<Laser> laserLinkedList;
    private LinkedList<Laser> laser2LinkedList;
    private LinkedList<Laser> enemyLaserList;
    private LinkedList<Explosion> explosionList;

    private int level = 0;
    private int score = 0;
    private int levelScore = 0;

    // Sound Effects
    private Sound sound;
    private Music music;

    /*HUD only shows player1, maybe if more players means combine scoregains, lives etc?*/ 
    //Heads-Up Display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;

     /*if want to remove animal laser, gameobjects enemylaserlist, enemylaser linkedlist, 
    animals variables(class and gamescreen), detectCollisions enemylist, renderlasers 2 lists)  */

    Random generator = new Random();
    TextureRegion[] animalForestTextures;
    TextureRegion[] animalDesertTextures;

    GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

//		background = new Texture("darkPurpleStarscape.png");
//		backgroundOffset = 0;

        // Setup texture atlas
        textureAtlas = new TextureAtlas("images.atlas");

        backgrounds = new TextureRegion[4];

        backgrounds[0] = textureAtlas.findRegion("grassBackground2");
        backgrounds[1] = textureAtlas.findRegion("grassBackground2");
        backgrounds[2] = textureAtlas.findRegion("grassBackground2");
        backgrounds[3] = textureAtlas.findRegion("grassBackground2");


        // init texture regions
        playerTextureRegion = textureAtlas.findRegion("soldier1_gun");
        player2TextureRegion = textureAtlas.findRegion("manBlue_gun");

        // Animal textures
        bearTextureRegion = textureAtlas.findRegion("bear2");
        crocTextureRegion = textureAtlas.findRegion("crocodile");
        elephantTextureRegion = textureAtlas.findRegion("elephant");
        lionTextureRegion = textureAtlas.findRegion("lion");
        gorillaTextureRegion = textureAtlas.findRegion("gorilla");
        camelTextureRegion = textureAtlas.findRegion("camel");

        laserTextureRegion = textureAtlas.findRegion("laserRed12");
        laser2TextureRegion = textureAtlas.findRegion("laserBlue12"); // Change this value if setting player 2 laser to another colour
        enemyLaserTextureRegion = textureAtlas.findRegion("laserOrange12");
        explosionTexture = new Texture("explosion.png");

        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

//        animalTextures = new TextureRegion[]{bearTextureRegion, elephantTextureRegion, lionTextureRegion, gorillaTextureRegion, camelTextureRegion};
        animalForestTextures = new TextureRegion[]{bearTextureRegion, elephantTextureRegion};
        animalDesertTextures = new TextureRegion[]{lionTextureRegion, camelTextureRegion};

//		duckTextureRegion = textureAtlas.findRegion("duck");
//		goatTextureRegion = textureAtlas.findRegion("goat");
//		pigTextureRegion = textureAtlas.findRegion("pig");
//		rabbitTextureRegion = textureAtlas.findRegion("rabbit");
//		snakeTextureRegion = textureAtlas.findRegion("snake");

        // Setup game objects
        player = new Player(WORLD_WIDTH / 2, WORLD_HEIGHT / 4, 
                10, 10, 
                48, 2, 3, 
                1f, 4, 120, .35f,
                playerTextureRegion, laserTextureRegion);

        // player 2
        player2 = new Player(WORLD_WIDTH / 2, WORLD_HEIGHT / 4, 
                10, 10, 
                48, 2, 3,
                1f, 4, 120, .35f,
                player2TextureRegion, laser2TextureRegion);

        enemyAnimalList = new LinkedList<>();

        laserLinkedList = new LinkedList<>();
        laser2LinkedList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();
        
        explosionList = new LinkedList<>();

        batch = new SpriteBatch();

        prepareHud();

        try{
            music = Gdx.audio.newMusic(Gdx.files.internal("across_the_valley.ogg"));

            music.setVolume(0.2f);
            music.setLooping(true);
            music.play();
        }catch (RuntimeException e){
            System.out.println("Music file not found: " + e);
        }

    }

    private void prepareHud(){
        //Create a BitmapFont from our font file
        // FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("TheFoxTailRegular.otf")); //doesnt work
        try{
            FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("test.otf"));
            // FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("killerblack.otf")); //lives doesnt show negative
            FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

            fontParameter.size = 72;
            fontParameter.borderWidth = 3.6f;
            fontParameter.color = new Color (1,1,1,0.3f);
            fontParameter.borderColor = new Color(0,0,0,0.3f);

            font = fontGenerator.generateFont(fontParameter);

            //Scale the font to fit world
            font.getData().setScale(0.08f);
        }catch (RuntimeException e){
            System.out.println("Font file not found: " + e);
        }



        //Calculate hud margins, etc
        hudVerticalMargin = font.getCapHeight() / 2;
        hudLeftX = hudVerticalMargin; 
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3; 
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin; 
        hudRow2Y = WORLD_HEIGHT  - hudVerticalMargin * 1.5f- font.getCapHeight(); 
        hudSectionWidth = WORLD_WIDTH / 3;
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
//        player2.draw(batch);

        // Lasers
        renderLasers(deltaTime);
//        renderLasers2(deltaTime);
//        renderEnemyLasers(deltaTime);

        //Detect collisions
        detectCollisions(deltaTime);

        updateLevel();

        // Explosions
        updateAndRenderExplosions(deltaTime);
        //hud rendering
        updateAndRenderHUD();

        batch.end();
    }

    private void updateLevel(){
        if(levelScore /  1000  == 1){
            levelScore = 0;
            level += 1;
        }

        if (level % 2 == 0) {
            backgrounds[0] = textureAtlas.findRegion("grassBackground2");
            backgrounds[1] = textureAtlas.findRegion("grassBackground2");
            backgrounds[2] = textureAtlas.findRegion("grassBackground2");
            backgrounds[3] = textureAtlas.findRegion("grassBackground2");
        }else {
            backgrounds[0] = textureAtlas.findRegion("desertBackground");
            backgrounds[1] = textureAtlas.findRegion("desertBackground");
            backgrounds[2] = textureAtlas.findRegion("desertBackground");
            backgrounds[3] = textureAtlas.findRegion("desertBackground");
        }
    }

    private int getLevel(){
        return level;
    }

    private void updateAndRenderHUD(){
        //render top row labels
        font.draw(batch, "Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Level", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, "Lives", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //render second row values
        font.draw(batch, String.format(Locale.getDefault(), "%06d", score), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", level), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", player.lives), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
    }    

    private void spawnEnemyAnimals(float deltaTime) {
        enemySpawnTimer += deltaTime;
        timeElapsed += deltaTime;

        int randomIndex = generator.nextInt(animalForestTextures.length);

        if (enemySpawnTimer > timeBetweenEnemySpawns && enemyAnimalList.size() < 10) {
            TextureRegion animalTexture;
            if (level % 2 == 0) {
                animalTexture = animalForestTextures[randomIndex];
            } else {
                animalTexture = animalDesertTextures[randomIndex];
            }
            enemyAnimalList.add(new Animals(48, 5,
                                            20, 20,
                                            MyGdxGame.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_HEIGHT + 30,
                                            0.8f, 4, 70, .8f,
                                            animalTexture, enemyLaserTextureRegion));
            enemySpawnTimer -= timeBetweenEnemySpawns;
        }
    }

    private void detectInput(float deltaTime) {
        // Keyboard Input

        // Strategy: determine the max distance the player can move
        // Check each key that matters and move accordingly

        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -player.boundingBox.x;
        downLimit = -player.boundingBox.y;
        rightLimit = WORLD_WIDTH - player.boundingBox.x - player.boundingBox.width;
        upLimit = WORLD_HEIGHT - player.boundingBox.y - player.boundingBox.height;

        float leftLimit2, rightLimit2, upLimit2, downLimit2;
        leftLimit2 = -player2.boundingBox.x;
        downLimit2 = -player2.boundingBox.y;
        rightLimit2 = WORLD_WIDTH - player2.boundingBox.x - player2.boundingBox.width;
        upLimit2 = WORLD_HEIGHT - player2.boundingBox.y - player2.boundingBox.height;

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
    }


    private void moveEnemy(Animals enemyAnimal, float deltaTime) {
        // Strategy: determine the max distance the enemy can move

        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyAnimal.boundingBox.x;
        downLimit = -enemyAnimal.boundingBox.y;
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

    private void detectCollisions(float deltaTime){
        //Check if player1 laser intersects animal
        damageTimer += deltaTime;

        ListIterator<Laser> laserListIterator = laserLinkedList.listIterator();
        while(laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<Animals> enemyAnimalListIterator = enemyAnimalList.listIterator();
            while (enemyAnimalListIterator.hasNext()) {
                Animals enemyAnimal = enemyAnimalListIterator.next();

                if (enemyAnimal.intersects(laser.boundingBox)){
                    // Touches animal
                    if(enemyAnimal.hitAndCheckKilled(laser))
                    {
                        enemyAnimalListIterator.remove();
                            explosionList.add(
                                new Explosion(explosionTexture, 
                                       new Rectangle (enemyAnimal.boundingBox), 
                                                0.7f));
                        //Killed and obtain score
                        score += 250;
                        levelScore += 250;
                    }
                    laserListIterator.remove();
                    break;
                }

                // Player 1 takes damage from enemy hitbox
                if (enemyAnimal.intersects(player.boundingBox)){
                    if (damageTimer > timeBetweenDamage && player.lives > 0) {
                        player.lives --;
                        damageTimer = 0;
                    }else if(damageTimer > timeBetweenDamage){
                        damageTimer = 0;
                        player.lives --;
                    }
                }

                // Player 2 takes damage from enemy hitbox
                if (enemyAnimal.intersects(player2.boundingBox)){
                    if (damageTimer > timeBetweenDamage) {
                        player2.lives --;
                        damageTimer -= timeBetweenDamage;
                    }
                }
            }
        }
            //Check if player2 laser intersects animal
            ListIterator<Laser> laser2ListIterator = laser2LinkedList.listIterator();
            while(laser2ListIterator.hasNext()) {
                Laser laser = laser2ListIterator.next();
                ListIterator<Animals> enemyAnimalListIterator = enemyAnimalList.listIterator();
                while (enemyAnimalListIterator.hasNext()) {
                    Animals enemyAnimal = enemyAnimalListIterator.next();
    
                    if (enemyAnimal.intersects(laser.boundingBox)){
                        // Touches animal
                        if(enemyAnimal.hitAndCheckKilled(laser))
                        {
                            enemyAnimalListIterator.remove();
                                explosionList.add(
                                    new Explosion(explosionTexture, 
                                            new Rectangle (enemyAnimal.boundingBox), 
                                                    0.7f));
                            //Killed and obtain score
                            score += 250;
                            levelScore += 250;
                        }
                        laser2ListIterator.remove();
                        break;
                    }

                    if (enemyAnimal.intersects(player2.boundingBox)){
                        player2.lives --;
                    }
                }
            }
        //Check if laser intersects player1
        laserListIterator = enemyLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (player.intersects(laser.boundingBox)) {
                //contact with player
                if (player.hitAndCheckKilled(laser)) {
                    explosionList.add(
                            new Explosion(explosionTexture,
                                    new Rectangle(player.boundingBox),
                                    1.6f));
                    player.lives --; //0 lives then gameover                      
                }
                laserListIterator.remove();
            }
        }
        //Check if laser intersects player2
        laserListIterator = enemyLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (player2.intersects(laser.boundingBox)) {
                //contact with player
                if (player2.hitAndCheckKilled(laser)) {
                    explosionList.add(
                            new Explosion(explosionTexture,
                                    new Rectangle(player2.boundingBox),
                                    1.6f));
                    player2.lives --; //0 lives then gameover                      
                }
                laserListIterator.remove();
            }
        }
    }

    private void updateAndRenderExplosions(float deltaTime){
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while (explosionListIterator.hasNext()){
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if(explosion.isFinished()){
                explosionListIterator.remove();
            }
            else {
                explosion.draw(batch);
            }
        }
   }

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

    private void renderEnemyLasers(float deltaTime){
        //Create new lasers
        ListIterator<Animals> enemyAnimalListIterator = enemyAnimalList.listIterator();
        while(enemyAnimalListIterator.hasNext()){
            Animals enemyAnimal = enemyAnimalListIterator.next();
            if(enemyAnimal.canFireLaser()){
                Laser[] lasers = enemyAnimal.fireLasers();
                for (Laser laser: lasers){                    
                enemyLaserList.add(laser);
                }
            }
        }
        
        ListIterator<Laser> iterator = enemyLaserList.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.movementSpeed*deltaTime;
            if(laser.boundingBox.y + laser.boundingBox.height < 0){
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
