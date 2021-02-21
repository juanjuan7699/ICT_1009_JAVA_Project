package com.ict1009.ahg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import menu.MenuScreen;
import menu.SplashScreen;

import java.util.Random;

public class AnimalHunter extends Game {
	private GameScreen gameScreen;
	private MenuScreen menuScreen;
	private SplashScreen splashScreen;
	static public Skin gameSkin;

	@Override
	public void create () {
//		gameScreen = new GameScreen(this);
//		menuScreen = new MenuScreen(this);
		gameSkin = new Skin(Gdx.files.internal("glassy-ui.json"));
//		this.setScreen(new MenuScreen(this));
		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose () {
//		super.dispose();
//		gameScreen.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
//		gameScreen.resize(width, height);
	}
}
