package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.GameScreen;
import com.ict1009.ahg.enums.StatusType;
import com.ict1009.ahg.interfaces.ICollidable;
import com.ict1009.ahg.interfaces.IDamageHandler;
import com.ict1009.ahg.interfaces.IStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.ict1009.ahg.GameScreen.WORLD_HEIGHT;
import static com.ict1009.ahg.GameScreen.WORLD_WIDTH;

public class Player extends Entity implements ICollidable, IDamageHandler, IStatus {

    private Laser weapon;
    private float timeSinceLastShot;
    private boolean invulnerable;
    private List<StatusType> statuses;

    private Timer attackTimer = new Timer();


    //runnable here to destroy onDestroy

    public Player() {
        this.setSprite(GameScreen.textureAtlas.findRegion("soldier1_gun"));
        this.setMaxHealth(100);
        this.modifyHealth(100);
        this.setMovementSpeed(48);
        this.setBoundingBox(new Rectangle(WORLD_WIDTH/2 - 5, WORLD_HEIGHT/2 - 5, 10, 10));
        this.setDamageScale(1);
        this.weapon = new Laser(this, 0);
        this.setAttackSpeed(1.45f);
        this.setHealthRegen(.01f);
        this.setAttacks(1);

        this.statuses = new ArrayList<>();
        this.statuses.add(StatusType.ALIVE);
    }

    public void resetBuffs() {
        this.setMaxHealth(100);
        this.modifyHealth(100);
        this.setMovementSpeed(48);
        this.setDamageScale(1);
        this.setHealthRegen(.01f);
        this.setAttacks(1);
    }

    @Override
    public boolean collisionTest(Entity target) {
        return !invulnerable && this.getBoundingBox().overlaps(target.getBoundingBox()); //not invulnerable and overlap = get damage
    }

    @Override
    public void takeDamage(float damage, int damageType, Entity instigator) {
        this.modifyHealth(-damage);
        //do something else when it gets damaged
        onTakeDamage(instigator);
    }

    @Override
    public void onTakeDamage(Entity instigator) {
        //show hurt etc
        if (getCurrentHealth() <= 0) {
            //downed state or die
            //this.onDestroy(instigator);
        }
    }

    @Override
    public void tryMove(int direction) {

    }

    @Override
    public void tryTeleport(Vector3 targetLocation) {

    }

    @Override
    public void addToRenderQueue() {
        GameScreen.players.add(this);
    }

    @Override
    public void onDestroy(Entity instigator) { //what happens when the player dies
        attackTimer.cancel();
        this.setPendingRemoval(true);
    }

    @Override
    public void update(float deltaTime) {
        this.modifyHealth(this.getHealthRegen()); //move to timer to slow down
    }

    public void startAttacking() { //now in a fixed timer instead of running the method every tick

        attackTimer.cancel();
        attackTimer = new Timer();

        attackTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < getAttacks(); i++) {
                    Laser laser = new Laser(Player.this, 0);

                    if (i >= 1 && i % 2 == 0) { //even
                        laser.setBoundingBox(new Rectangle(getBoundingBox().x + getBoundingBox().width * .72f + 4f * i / 2, getBoundingBox().y + getBoundingBox().height * .98f, 1, 4));
                    } else if (i >= 1) { //odd
                        laser.setBoundingBox(new Rectangle(getBoundingBox().x + getBoundingBox().width * .72f - 2.5f - 3f * i / 2, getBoundingBox().y + getBoundingBox().height * .98f, 1, 4));
                    }

                    laser.addToRenderQueue();
                }
            }
        }, 0, (long) (this.getAttackSpeed() * 1000));
    }



    @Override
    public void addStatus(StatusType statusType) {
        if (!this.statuses.contains(statusType))
            this.statuses.add(statusType);
    }

    @Override
    public void removeStatus(StatusType statusType) {
        this.statuses.remove(statusType);
    }

    @Override
    public void removeStatus(int index) {
        this.statuses.remove(index);
    }

    @Override
    public void removeAllStatus() {
        this.statuses = new ArrayList<>();
        this.statuses.add(StatusType.ALIVE);
    }

    @Override
    public boolean hasStatus(StatusType status) {
        return this.statuses.contains(status);
    }

    @Override
    public List<StatusType> getStatuses() {
        return this.statuses;
    }
}
