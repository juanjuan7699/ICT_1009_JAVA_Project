package states;

import enums.GState;
import maths.GVector;
import org.lwjgl.glfw.GLFW;
import rendering.Renderer;
import rendering.Texture;
import structs.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class GameState implements State {
    private HashMap<String, Entity> players; //place all players in this area, can also include max players per session
    private List<Entity> renderQueue; //the rest of the stuff that are not players here

    private float gameTime; //timer
    private int totalScore;
    private GState currentState;

    private SpawnPoint[] spawnPoints;

    private Texture texture;
    private Renderer renderer;

    public GameState(Renderer renderer) {
        this.renderer = renderer;
        currentState = GState.STARTUP;

        players = new HashMap<>();
        renderQueue = new ArrayList<>();
    }

    public void testAddPlayer() {
        //TODO make not debug
        Player player = new Player();
        player.setUID("1");
        player.setSprite(Texture.loadTexture("resources/color2.png"));
        player.setColor(Color.WHITE);
        player.setCurrentLocation(new GVector(50,80));
        player.setName("My aasdasd name");
        addPlayerToRenderQueue(player);

        Player player2 = new Player();
        player2.setSprite(Texture.loadTexture("resources/color3.png"));
        player2.setColor(Color.WHITE);
        player2.setCurrentLocation(new GVector(300,200));
        addPlayerToRenderQueue(player2);
    }

    public void addPlayerToRenderQueue(Player player) {
        players.put(player.getUID(), player);
    }

    public void addToRenderQueue(Entity any) {
        renderQueue.add(any);
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
        //TODO debug testing only
        long window = GLFW.glfwGetCurrentContext();
        if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
            players.get("1").tryMoveInput(1);
        }
        if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
            players.get("1").tryMoveInput(2);
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
            players.get("1").tryMoveInput(3);
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            players.get("1").tryMoveInput(4);
        }

        if (glfwGetKey(window, GLFW_KEY_R) == GLFW_PRESS && ((Player)players.get("1")).isCanAttack()) {
            Bullet bullet = new Bullet();
            bullet.setCurrentLocation(players.get("1").getCurrentLocation());
            bullet.setVelocity(new GVector(8, 0));
            addToRenderQueue(bullet); // must remove later on, it will cause mem leaks if not
            ((Player)players.get("1")).setCanAttack(false);
        }

        if (glfwGetKey(window, GLFW_KEY_R) == GLFW_RELEASE) {
            ((Player)players.get("1")).setCanAttack(true);
        }
    }

    @Override
    public void update(float delta) {
        //update scores here
    }

    @Override
    public void render(float alpha) {
        renderer.clear(); //clear before render loop


        //render here TODO: add all the animations here
        for (Map.Entry<String, Entity> entities : players.entrySet()) { //RENDERING PLAYERS ONLY
            renderer.begin();

            Entity entity = entities.getValue();
            texture = entity.getSprite();
            texture.bind();
            entity.render(renderer);

            renderer.end();

            entity.renderTextData(renderer); //always render text after ending previous renderer
        }

        for (Entity entity : renderQueue) { //RENDERING EVERYTHING ELSE : yes they look the same but they are separate for gamestate purposes
            renderer.begin();

            texture = entity.getSprite();
            texture.bind();
            entity.render(renderer);

            renderer.end();

            entity.renderTextData(renderer);
        }
        //end render here

        //render text here !!must render after renderer.end() because it starts its own render loop
        renderer.drawText("total score: " + totalScore, 5, 520);
    }

    @Override
    public void enter() {
        //texture = Texture.loadTexture("resources/cat.png");
        glClearColor(0f, 0.73f, 0.83f, 1f);
    }

    @Override
    public void exit() {
        if (texture != null)
            texture.delete();
    }
}
