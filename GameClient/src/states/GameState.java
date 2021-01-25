package states;

import structs.Entity;

enum GState {
    PAUSED,
    LOBBY,
    RUNNING,
    COMPLETED //show scoreboard
}

public class GameState {
    private Entity[] players; //place all players in this area, can also include max players per session
    private Entity[] enemies;

    private float gameTime; //timer
    private int totalScore;
    private GState currentState;




}
