package com.mygdx.game;

import com.badlogic.gdx.Game;

import java.util.Random;

public class MyGdxGame extends Game {

	GameScreen gameScreen;

	public static Random random = new Random();

	@Override
	public void create() {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		gameScreen.dispose();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		gameScreen.resize(width, height);
	}

}
