package states;

import structs.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

enum GState {
    PAUSED,
    LOBBY,
    RUNNING,
    COMPLETED //show scoreboard
}

public class GameState {
    private HashMap<String, Entity> players; //place all players in this area, can also include max players per session
    private Entity[] enemies;

    private float gameTime; //timer
    private int totalScore;
    private GState currentState;

    private SpawnPoint[] spawnPoints;

    public void addPlayer(Player player) {
        players.put(player.getUID(), player);
    }

    public Player getPlayerByID(String UID) {
        return (Player) players.get(UID);
    }

    //get all players

    //get player nearest to coords
    public Player getNearestPlayer(GPosition position) { //usually for AI
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

}
