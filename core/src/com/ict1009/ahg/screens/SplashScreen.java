package com.ict1009.ahg.screens;//package menu;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.ict1009.ahg.AnimalHunter;
//
//public class SplashScreen implements Screen {
//    private SpriteBatch batch;
//    private Texture ttrSplash;
//
//    public SplashScreen(AnimalHunter animalHunter) {
//        super();
//        batch = new SpriteBatch();
//        ttrSplash = new Texture("splashscreen.png");
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        batch.begin();
//        batch.draw(ttrSplash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        batch.end();
//    }
//
//    @Override
//    public void hide() { }
//
//    @Override
//    public void pause() { }
//
//    @Override
//    public void resume() { }
//
//    @Override
//    public void show() { }
//
//    @Override
//    public void resize(int width, int height) { }
//
//    @Override
//    public void dispose() {
//        ttrSplash.dispose();
//        batch.dispose();
//    }
//}
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ict1009.ahg.AnimalHunter;

public class SplashScreen implements Screen {
    private Texture splashtexture;
    private Image splashimage;
    private Stage splashstage;
    private float WIDTH,HEIGHT;
    private float timeToShowSplashScreen = 3f; // 3 seconds

    private AnimalHunter game;

    public SplashScreen(AnimalHunter aGame) {
        game = aGame;
    }

    @Override
    public void show() {
        WIDTH = 600;
        HEIGHT = 900;

        splashtexture = new Texture(Gdx.files.internal("splashscreen.png"));
        splashtexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        splashimage = new Image(splashtexture);
        splashimage.setSize(WIDTH, HEIGHT);

        splashstage = new Stage(new FitViewport(WIDTH, HEIGHT) {
        });
        splashstage.addActor(splashimage);

        splashimage.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.fadeIn(1.25F),Actions.delay(1F), Actions.fadeOut(0.75F)));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        splashstage.act();
        splashstage.draw();
        // remove delta from time
        timeToShowSplashScreen -= delta;
        // 3 seconds are up
        if (timeToShowSplashScreen <= 0) {
            // tell game to change screen
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        splashstage.getViewport().update(width,height,true);
    }
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        splashtexture.dispose();
        splashstage.dispose();
    }
}