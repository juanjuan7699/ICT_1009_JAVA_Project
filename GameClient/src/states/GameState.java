package states;

import enums.GState;
import maths.GVector;
import rendering.Renderer;
import rendering.Texture;
import structs.*;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.glClearColor;

public class GameState implements State {
    private HashMap<String, Entity> players; //place all players in this area, can also include max players per session
    private Entity[] enemies;

    private float gameTime; //timer
    private int totalScore;
    private GState currentState;

    private SpawnPoint[] spawnPoints;

    private Texture texture;
    private Renderer renderer;

    public GameState(Renderer renderer) {
        this.renderer = renderer;
        currentState = GState.STARTUP;
    }

    public void addPlayer(Player player) {
        players.put(player.getUID(), player);
    }

    public Player getPlayerByID(String UID) {
        return (Player) players.get(UID);
    }

    //get all players

    //get player nearest to coords
    public Player getNearestPlayer(GVector position) { //usually for AI
        Entity nearest = null;
        float nearestRange = Float.MAX_VALUE;
        for (Map.Entry<String, Entity> check : players.entrySet()) {
            Entity en = check.getValue();
            if (en.getEntityType() != EntityType.PLAYER_ENTITY) {
                continue;
            }
            if (nearest == null || position.getRangeTo(nearest.getCurrentLocation()) < nearestRange) {
                nearest = en;
            }
        }
        return (Player) nearest;
    }

    @Override
    public void input() {
        //inout here
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float alpha) {
        renderer.clear(); //clear before render loop

        texture.bind();
        renderer.begin();

        //render here TODO: add all the animations here
        renderer.drawTextureRegion(texture, 50, 50, 0, 0, 512, 512, Color.GREEN);
        //end render here TODO

        renderer.end();

        //render text here !!must render after renderer.end() because it starts its own render loop
        renderer.drawText("things are rendering", 5, 500);
        renderer.drawText("total score: " + totalScore, 5, 520);
    }

    @Override
    public void enter() {
        texture = Texture.loadTexture("resources/cat.png");
        glClearColor(0.5f, 0.5f, 0.5f, 1f);
    }

    @Override
    public void exit() {
        if (texture != null)
            texture.delete();
    }
}
