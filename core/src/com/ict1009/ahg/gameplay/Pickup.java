package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.GameScreen;
import com.ict1009.ahg.enums.ItemType;
import com.ict1009.ahg.enums.Rarity;

import static com.ict1009.ahg.GameScreen.*;

public class Pickup extends Entity {

    private ItemType pickupType;
    private Rarity rarity;

    private boolean inventoryItem; //false for instant buffs

    public Pickup(boolean inventoryItem) { //random generation
        this.setDamageScale(1 + generator.nextFloat()); //scaling of the pickup itself regardless if its damage, health, attack speed
        this.inventoryItem = inventoryItem;
        //get spawn area then set bounding box
        this.setBoundingBox(new Rectangle((float)generator.nextInt((int)WORLD_WIDTH - 5), WORLD_HEIGHT-20, 20, 20)); //need more images
        this.setSprite(animalDesertTextures[1]); //temporary
        this.pickupType = ItemType.NONE;

        if (this.inventoryItem) {
            //drop non buffs
            this.onDestroy(this); //temporarily no inventory items
            System.out.println("A non buff was generated");
        }
        else { //drop buffs
            int rng = generator.nextInt(4);
            switch (rng) {
                case 0:
                    this.pickupType = ItemType.ATTACKSPEED_BUFF;
                    this.setSprite(animalDesertTextures[1]); //temporary
                    break;
                case 1:
                    this.pickupType = ItemType.REGEN_BUFF;
                    this.setSprite(animalDesertTextures[1]); //temporary
                    break;
                case 2:
                    this.pickupType = ItemType.DAMAGE_BUFF;
                    this.setSprite(animalDesertTextures[1]); //temporary
                    break;
                case 3:
                    this.pickupType = ItemType.EXTRA_LASER_BUFF;
                    this.setSprite(animalDesertTextures[1]); //temporary
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
                instigator.setDamageScale(instigator.getDamageScale() * 1.15f);
                System.out.println("buffed dmg now " + instigator.getDamageScale());
                break;
            case ATTACKSPEED_BUFF:
                instigator.setAttackSpeed(Math.max(0.2f, instigator.getAttackSpeed() * .9f));
                if (instigator instanceof Player)
                    ((Player)instigator).startAttacking();

                System.out.println("buffed spd now " + instigator.getAttackSpeed());
                break;
            case REGEN_BUFF:
                instigator.setHealthRegen(instigator.getHealthRegen() + .01f);
                System.out.println("buffed healthreg now " + instigator.getHealthRegen());
                break;
            case EXTRA_LASER_BUFF:
                instigator.setAttacks(instigator.getAttacks() + 1);
                System.out.println("buffed attks now " + instigator.getAttacks());
                break;
        }

        //picking up a buff means you will increase the level difficulty by a fixed rate of 0.20
        //score += 200; //but will not add to total score of hunted animals
        levelScore += 200;

        this.setPendingRemoval(true);
    }

    @Override
    public void update(float deltaTime) {
        this.translate(0, -1f);
    }
}
