package structs;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

enum NextIntent {
    NONE,
    MOVEMENT_WANDER,
    MOVEMENT_EVADE,
    ATTACK_AGGRO,
    ATTACK_RANDOM,
    SEARCH_TARGET,
    SEARCH_HEALING //when health is low try to search for health packs
}

public class Animal extends Entity { //create random animal types

    private float score;
    Timer AILoop;
    private long aiDelay = 3000; //3000 is 3 seconds, ai will only do behavior every 3 seconds
    private int wanderDistance = 50;
    private NextIntent nextIntent = NextIntent.NONE; //state machine
    private Entity aggroEntity;
    private Entity lastHit; //last hit data to give score
    private boolean instantKill = false; //on demand AI killswitch

    public Animal() {
        super(EntityType.ANIMAL_ENTITY);
        runBehaviorTree(); //run once only
        this.score = 999;
    }

    public void runBehaviorTree() { //removed rng brain
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
                //execute FIRST based on next intent
                switch (nextIntent) { //behavior tree
                    case MOVEMENT_WANDER: //MOVEMENT_WANDER
                        tryMoveToTargetLocation();
                        break;
                    case MOVEMENT_EVADE: //MOVEMENT_EVADE
                        avoidFire();
                        break;
                    case ATTACK_AGGRO: //AGGRO_ATTACK
                        aggroAttack();
                        break;
                    case ATTACK_RANDOM: //ANY_ATTACK
                        anyAttack();
                        break;
                    case SEARCH_TARGET: //CHANGE_AGGRO
                        findAggroTarget();
                        break;
                    case SEARCH_HEALING:
                        tryFindHealthPack();
                        break;
                    case NONE:
                    default:
                        //do nothing i guess
                        break;
                }

                //state machine with intents: change intent AFTER running initial intent
                if (getHealthPercent() < 0.35) { //first priority to save self around 33%
                    nextIntent = NextIntent.SEARCH_HEALING;
                    setCombat(false);
                }

                else if ((aggroEntity == null || !aggroEntity.isAlive()) && getHealthPercent() > 0.7) { //at 70% or more start finding targets
                    nextIntent = NextIntent.SEARCH_TARGET;
                }

                else if (aggroEntity != null && aggroEntity.isAlive()) { //state attacking
                    nextIntent = NextIntent.ATTACK_AGGRO;
                    setCombat(true);
                }

                else if (nextIntent == NextIntent.NONE && isCombat()) { //state search and attack
                    nextIntent = aggroEntity == null ? NextIntent.SEARCH_TARGET : NextIntent.ATTACK_AGGRO;
                }

                else if (tryFindNearbyEnemies()) { //at 35% to 70% attack randomly (makes no sense but for now yea)
                    nextIntent = NextIntent.ATTACK_RANDOM;
                }

                else {
                    nextIntent = NextIntent.MOVEMENT_WANDER;
                }

                //generate rng data every run //TODO: change to + or - if not the wandering will only go positive X and Y
                setTargetLocation(new GPosition(getTargetLocation().getPosX() + rng.nextInt(wanderDistance), getTargetLocation().getPosY() + rng.nextInt(wanderDistance)));

                if (instantKill) { //should kill somewhere earlier
                    return;
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

    public void tryFindHealthPack() {
        if (instantKill) { //stops AI
            return;
        }
        //get gamestate.pickups and find nearest PickupType.HEALTHPACK
        if (true) { //TODO if there are no health packs force evade
            nextIntent = NextIntent.MOVEMENT_EVADE;
            avoidFire();
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

        //after damaging
        if (!aggroEntity.isAlive()) {
            nextIntent = NextIntent.SEARCH_TARGET;
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

    public boolean tryFindNearbyEnemies() {
        if (instantKill) { //stops AI
            return false;
        }

        //if there is nearby enemies
        return true;
    }
}
