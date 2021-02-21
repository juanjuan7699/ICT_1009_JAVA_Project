package com.ict1009.ahg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;

import java.util.Locale;

public class EndScreen implements Screen {

    private AnimalHunter game;
    private final SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();

    EndScreen(AnimalHunter game) {
        this.game = game;
        prepareHud();
    }

    private void prepareHud() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("test.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1,1,1,0.3f);
        fontParameter.borderColor = new Color(0,0,0,0.3f);

        font = fontGenerator.generateFont(fontParameter);
    }

    @Override
    public void render(float deltaTime){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.draw(batch, "Game Over", GameScreen.WORLD_WIDTH * 2, GameScreen.WORLD_HEIGHT * 5);
        font.draw(batch, String.format(Locale.getDefault(), "Score: %06d", GameScreen.score), 265, GameScreen.WORLD_HEIGHT * 3, GameScreen.WORLD_WIDTH, Align.center, false);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
