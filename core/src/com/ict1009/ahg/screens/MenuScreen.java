package com.ict1009.ahg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ict1009.ahg.AnimalHunter;

public class MenuScreen implements Screen {
    private static Music music;
    private final Stage stage;
    private final AnimalHunter game;
    private final TextureRegion backgroundTexture = new TextureRegion(new Texture("jungle02.png"), 0, 0, Gdx.graphics.getWidth() , Gdx.graphics.getHeight());

    public MenuScreen(AnimalHunter aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        try{
            music = Gdx.audio.newMusic(Gdx.files.internal("menu_music.ogg"));
            music.setVolume(1.2f);
            music.setLooping(true);
            music.play();
        }catch (RuntimeException e){
            System.out.println("Music file not found: " + e);
        }

        Label title = new Label("Animal Hunter", AnimalHunter.gameSkin, "big-black");
        title.setAlignment(Align.center);
        title.setY((float)Gdx.graphics.getHeight() * 6 / 9);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        // play button to start the game
        TextButton playBtn = new TextButton("Play!", AnimalHunter.gameSkin);
        playBtn.getLabel().setFontScale(0.8f, 0.8f);
        playBtn.setWidth((float)Gdx.graphics.getWidth() / 2);
        playBtn.setPosition((float)Gdx.graphics.getWidth() / 2 - playBtn.getWidth() / 2, (float)Gdx.graphics.getHeight() / 2 - playBtn.getHeight() / 2);
        playBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                music.dispose();
                game.setScreen(new GameScreen(game));
                Gdx.input.setInputProcessor(null);
                return true;
            }
        });
        stage.addActor(playBtn);

        // instructions for each player keys
        TextButton instructionButton = new TextButton("Instruction", AnimalHunter.gameSkin);
        instructionButton.getLabel().setFontScale(0.8f, 0.8f);
        instructionButton.setWidth((float)Gdx.graphics.getWidth() / 2);
        instructionButton.setPosition((float)Gdx.graphics.getWidth() / 2 - instructionButton.getWidth() / 2, (float)Gdx.graphics.getHeight() / 3 - instructionButton.getHeight() / 2);
        instructionButton.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new InstructionScreen(game));
                return true;
            }

        });
        stage.addActor(instructionButton);

        // to exit the game
        TextButton quitButton = new TextButton("Quit", AnimalHunter.gameSkin);
        quitButton.getLabel().setFontScale(0.8f, 0.8f);
        quitButton.setWidth((float)Gdx.graphics.getWidth() / 2);
        quitButton.setPosition((float)Gdx.graphics.getWidth() / 2 - quitButton.getWidth() / 2, (float)Gdx.graphics.getHeight() / 6 - quitButton.getHeight() / 2);
        quitButton.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        stage.addActor(quitButton);
    }

    @Override
    public void show () {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();


        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();
    }

    public static Music getMusic() {
        return music;
    }

    @Override
    public void resize ( int width, int height){
    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void hide () {

    }

    public void dispose () {
        stage.dispose();

    }
}
