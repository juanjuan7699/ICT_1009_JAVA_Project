package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.screens.GameScreen;
import com.ict1009.ahg.enums.ItemType;

import static com.ict1009.ahg.screens.GameScreen.*;

public class Pickup extends Entity {

    private ItemType pickupType;

    private boolean inventoryItem; //false for instant buffs

    public Pickup(float x, float y, float scale, boolean inventoryItem) { //to spawn at boss location
        this(inventoryItem);
        this.setBoundingBox(new Rectangle(x + (float)generator.nextInt(8),y + (float)generator.nextInt(8), 15, 15));
        this.setDamageScale(scale);
        this.setMovementSpeed(0.25f);
    }

    public Pickup(boolean inventoryItem) { //random generation
        this.setMovementSpeed(0.5f);
        this.setDamageScale(1 + generator.nextFloat()); //scaling of the pickup itself regardless if its damage, health, attack speed
        this.inventoryItem = inventoryItem;
        //get spawn area then set bounding box
        this.setBoundingBox(new Rectangle((float)generator.nextInt((int)WORLD_WIDTH - 5), WORLD_HEIGHT-20, 20, 20)); //need more images
        this.setSprite(gunTextures[5]); //temporary
        this.pickupType = ItemType.NONE;

        if (this.inventoryItem) {
            //drop non buffs
            int rng = generator.nextInt(6);
            System.out.println("A non buff was generated");
            switch (rng) {
                case 0:
                    this.pickupType = ItemType.GENERIC_LASER;
                    this.setSprite(gunTextures[5]); //temporary
                    break;
                case 1:
                    this.pickupType = ItemType.STASIS_LASER;
                    this.setSprite(gunTextures[2]);//temp
                    break;
                case 2:
                    this.pickupType = ItemType.SWARM_LASER;
                    this.setSprite(gunTextures[1]);//temp
                    break;
                case 3:
                    this.pickupType = ItemType.NANO_LASER;
                    this.setSprite(gunTextures[4]);//temp
                    break;
                case 4:
                    this.pickupType = ItemType.BLAZE_LASER;
                    this.setSprite(gunTextures[0]);//yellow
                    break;
                case 5:
                    this.pickupType = ItemType.NOVA_LASER;
                    this.setSprite(gunTextures[3]);//temp
                    break;
            }
        }
        else { //drop buffs
            int rng = generator.nextInt(10);
            switch (rng) {
                case 0:
                case 1:
                    this.pickupType = ItemType.ATTACKSPEED_BUFF;
                    this.setSprite(potionTextures[0]); //temporary
                    break;
                case 2:
                case 3:
                    this.pickupType = ItemType.REGEN_BUFF;
                    this.setSprite(potionTextures[2]); //temporary
                    break;
                case 4:
                case 5:
                    this.pickupType = ItemType.HEALTH_BUFF;
                    this.setSprite(potionTextures[2]);
                    break;
                case 6:
                case 7:
                case 8:
                    this.pickupType = ItemType.DAMAGE_BUFF;
                    this.setSprite(potionTextures[1]); //temporary
                    break;
                case 9:
                    this.pickupType = ItemType.EXTRA_LASER_BUFF;
                    this.setSprite(potionTextures[3]); //temporary
                    break;
            }
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
        GameScreen.renderQueue.add(this);
    }

    @Override
    public void onDestroy(Entity instigator) { //ALL BUFFS WILL STACK UNTIL THE PLAYER IS DOWNED
        //animation

        switch (pickupType) {
            case DAMAGE_BUFF: //exponential buff
                instigator.setDamageScale(instigator.getDamageScale() * (this.getDamageScale() * 1.25f));
                System.out.println("buffed dmg now " + instigator.getDamageScale());
                break;
            case ATTACKSPEED_BUFF:
                instigator.setAttackSpeed((float) Math.max(0.2f, instigator.getAttackSpeed() - (0.1 * this.getDamageScale())));
                if (instigator instanceof Player)
                    ((Player)instigator).startAttacking();

                System.out.println("buffed spd now " + instigator.getAttackSpeed());
                break;
            case REGEN_BUFF:
                instigator.setHealthRegen((float) (instigator.getHealthRegen() + (Math.pow(.03f, this.getDamageScale()))));
                System.out.println("buffed healthreg now " + instigator.getHealthRegen());
                break;
            case EXTRA_LASER_BUFF: //if they already have 5, buff their attack instead
                if (instigator.getAttacks() + 1 > 5) {
                    instigator.setDamageScale(instigator.getDamageScale() + 1f);
                    System.out.println("buffed dmg now " + instigator.getDamageScale());
                    break;
                }

                instigator.setAttacks(instigator.getAttacks() + 1);
                System.out.println("buffed attks now " + instigator.getAttacks());
                break;
            case HEALTH_BUFF:
                instigator.setMaxHealth(instigator.getMaxHealth() + 5);
                System.out.println("buffed hp now " + instigator.getMaxHealth());
                break;
            case GENERIC_LASER:
            case STASIS_LASER:
            case SWARM_LASER:
            case NANO_LASER:
            case BLAZE_LASER:
            case NOVA_LASER:
                if (instigator instanceof Player) {
                    ((Player)instigator).addWeapon(pickupType);
                }
                break;
        }

        //picking up a buff means you will increase the level difficulty by a fixed rate of 0.20
        //score += 200; //but will not add to total score of hunted animals
        levelScore += 200;

        this.setPendingRemoval(true);
    }

    @Override
    public void update(float deltaTime) {
        this.translate(0, -this.getMovementSpeed());
    }
}
