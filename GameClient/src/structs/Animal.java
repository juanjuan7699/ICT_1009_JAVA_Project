package structs;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Animal extends Entity {

    private float score;
    Timer AILoop;
    private long aiDelay = 3000; //3000 is 3 seconds, ai will only do behavior every 3 seconds
    private int wanderDistance = 50;
    private int nextIntent = 0;
    private Entity aggroEntity;
    private Entity lastHit; //last hit data to give score
    private boolean instantKill = false; //on demand AI killswitch

    public Animal() {
        super(EntityType.ANIMAL_ENTITY);
        runBehaviorTree(); //run once only
        this.score = 999;
    }

    public void runBehaviorTree() {
        Random rng = new Random(); //each tree will have its own random
        AILoop = new Timer();
        AILoop.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isAlive()) {
                    destroyAI(true);
                    //entity give score or separate gamestate data
                    if (lastHit.getEntityType() == EntityType.PLAYER_ENTITY) {
                        //give points here or sendto gameState
                        return;
                    }
                    return;
                }

                //generate rng data every run
                setTargetLocation(new GPosition(rng.nextInt(wanderDistance), rng.nextInt(wanderDistance)));
                nextIntent = rng.nextInt(5);

                if (nextIntent == 0 && isCombat()) {
                    nextIntent = aggroEntity == null ? 1 : 2;
                }

                if (instantKill) { //should kill somewhere earlier
                    return;
                }

                //execute based on next intent
                switch (nextIntent) { //behavior tree
                    case 0: //MOVEMENT_WANDER
                        tryMoveToTargetLocation();
                        break;
                    case 1: //MOVEMENT_EVADE
                        avoidFire();
                        break;
                    case 2: //AGGRO_ATTACK
                        aggroAttack();
                        break;
                    case 3: //ANY_ATTACK
                        anyAttack();
                        break;
                    case 4: //CHANGE_AGGRO
                        findAggroTarget();
                        break;
                }
            }
        }, new Date(), aiDelay);
    }

    public void destroyAI(boolean onDemand) {
        if (AILoop != null) {
            if (onDemand) {
                instantKill = true;
            }

            AILoop.cancel(); //cancel gracefully (with last task)
            AILoop = null;
        }
    }

    public void avoidFire() {
        if (instantKill) { //stops AI
            return;
        }
        //check for nearby projectiles and avoid
    }

    public void aggroAttack() { //attack the current aggro-d tagret
        if (instantKill) { //stops AI
            return;
        }
    }

    public void anyAttack() { //attack straight
        if (instantKill) { //stops AI
            return;
        }
    }

    public void findAggroTarget() {
        if (instantKill) { //stops AI
            return;
        }
    }
}
