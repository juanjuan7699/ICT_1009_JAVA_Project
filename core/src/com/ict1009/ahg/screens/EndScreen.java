package com.ict1009.ahg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.ict1009.ahg.AnimalHunter;

import java.util.Locale;

public class EndScreen implements Screen {

    private final AnimalHunter game;
    private final SpriteBatch batch = new SpriteBatch();
    private BitmapFont font = new BitmapFont();
    private BitmapFont font2 = new BitmapFont();

    public EndScreen(AnimalHunter game) {
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

        FreeTypeFontGenerator fontGenerator2 = new FreeTypeFontGenerator(Gdx.files.internal("test.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter2.size = 30;
        fontParameter2.borderWidth = 3.6f;
        fontParameter2.color = new Color(1,1,1,0.3f);
        fontParameter2.borderColor = new Color(0,0,0,0.3f);

        font2 = fontGenerator2.generateFont(fontParameter2);
    }

    @Override
    public void render(float deltaTime){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.draw(batch, "Game Over", GameScreen.WORLD_WIDTH * 2, GameScreen.WORLD_HEIGHT * 5);
        font.draw(batch, String.format(Locale.getDefault(), "Score: %06d", GameScreen.score), 265, GameScreen.WORLD_HEIGHT * 4, GameScreen.WORLD_WIDTH, Align.center, false);
        font2.draw(batch, "Press R to restart\n\nPress B to go to menu", 160, GameScreen.WORLD_HEIGHT * 2);

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            GameScreen.gameReset();
            game.setScreen(new GameScreen(game));
        } else if (Gdx.input.isKeyPressed(Input.Keys.B)) {
            GameScreen.gameReset();
            game.setScreen(new MenuScreen(game));
        }

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
