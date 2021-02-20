package com.ict1009.ahg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class AnimalHunter extends Game {
	GameScreen gameScreen;

	@Override
	public void create () {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		gameScreen.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		gameScreen.resize(width, height);
	}
}
