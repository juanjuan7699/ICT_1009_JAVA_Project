package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ict1009.ahg.AnimalHunter;

public class InstructionScreen implements Screen {
    private AnimalHunter game;
    private Stage stage;

    public InstructionScreen(AnimalHunter aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        Label title = new Label("How to play", AnimalHunter.gameSkin,"big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*6/8);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        Label instruction = new Label("Player 1\n Up:'W'\n Left: 'A' \nDown: 'S'\n Right:'D'\n \nPlayer 2\n Up:'Up Arrow'\n " +
                "Left: 'Left Arrow'\n Down: 'Down Arrow'\n Right:'Right Arrow' ", AnimalHunter.gameSkin,"black");
        instruction.setFontScale(1.2f, 1.2f);
        instruction.setAlignment(Align.center);
        instruction.setY(Gdx.graphics.getHeight()*2/5);
        instruction.setWidth(Gdx.graphics.getWidth());
        stage.addActor(instruction);

        // to go back to menu page
        TextButton backButton = new TextButton("Back",AnimalHunter.gameSkin);
        backButton.setWidth(Gdx.graphics.getWidth()/2);
        backButton.setPosition(Gdx.graphics.getWidth()/2-backButton.getWidth()/2,Gdx.graphics.getHeight()/4-backButton.getHeight()/2);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                MenuScreen.getMusic().dispose();
                game.setScreen(new MenuScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backButton);

    }
    @Override
    public void show () {
       Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render ( float delta){
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();

        TextureRegion backgroundTexture = new TextureRegion(new Texture("jungle02.png"), 0, 0, Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        SpriteBatch batch = new SpriteBatch();

        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();
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

