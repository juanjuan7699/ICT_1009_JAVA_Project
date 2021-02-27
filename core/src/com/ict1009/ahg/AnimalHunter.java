package com.ict1009.ahg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ict1009.ahg.screens.SplashScreen;

public class AnimalHunter extends Game {
	static public Skin gameSkin;

	@Override
	public void create () {
		gameSkin = new Skin(Gdx.files.internal("glassy-ui.json"));
		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}
}