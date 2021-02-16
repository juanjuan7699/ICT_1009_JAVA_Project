package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
	private float[] backgroundOffsets = {0,0,0,0};
	private float backgroundMaxScrollingSpeed;

	//World parameters
	private final int WORLD_WIDTH = 72;
	private final int WORLD_HEIGHT = 128;

	// Game Objects
	private animals enemyAnimal;
	private player player;
	
	GameScreen(){
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

		backgroundMaxScrollingSpeed = (float)(WORLD_HEIGHT) / 4;

		// init texture regions
		playerTextureRegion = textureAtlas.findRegion("soldier1_gun");
		//playerTextureRegion.;
		bearTextureRegion = textureAtlas.findRegion("bear");
//		crocTextureRegion = textureAtlas.findRegion("crocodile");
//		duckTextureRegion = textureAtlas.findRegion("duck");
//		goatTextureRegion = textureAtlas.findRegion("goat");
//		pigTextureRegion = textureAtlas.findRegion("pig");
//		rabbitTextureRegion = textureAtlas.findRegion("rabbit");
//		snakeTextureRegion = textureAtlas.findRegion("snake");
		laserTextureRegion = textureAtlas.findRegion("laserOrange02");

		// Setup game objects
		player = new player(2, 10, 10,
				WORLD_WIDTH/2, WORLD_HEIGHT/4, playerTextureRegion);

		enemyAnimal = new animals(2, 10, 10, WORLD_WIDTH/2, WORLD_HEIGHT*3/4, bearTextureRegion);
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float deltaTime) {
		batch.begin();
		
		//Scrolling background
//		backgroundOffset ++;
//		if(backgroundOffset % WORLD_HEIGHT == 0) {
//			backgroundOffset = 0;
//		}
//
//		batch.draw(background, 0, -backgroundOffset, WORLD_WIDTH, WORLD_HEIGHT);
//		batch.draw(background, 0, -backgroundOffset + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);

		renderBackground(deltaTime);

		// Animals
		enemyAnimal.draw(batch);

		// Player
		player.draw(batch);

		batch.end();
	}

	private void renderBackground(float deltaTime){

		backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
		backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
		backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
		backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

		for(int layer = 0; layer < backgroundOffsets.length; layer++){
			if(backgroundOffsets[layer] > WORLD_HEIGHT){
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
