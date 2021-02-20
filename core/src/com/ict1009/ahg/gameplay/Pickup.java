package com.ict1009.ahg.gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.ict1009.ahg.GameScreen;
import com.ict1009.ahg.enums.ItemType;
import com.ict1009.ahg.enums.Rarity;

import static com.ict1009.ahg.GameScreen.animalDesertTextures;
import static com.ict1009.ahg.GameScreen.generator;

public class Pickup extends Entity {

    private ItemType pickupType;
    private Rarity rarity;

    private boolean inventoryItem; //false for instant buffs

    Pickup() { //random generation
        this.setDamageScale(1 + generator.nextFloat()); //scaling of the pickup itself regardless if its damage, health, attack speed
        this.inventoryItem = generator.nextBoolean();
        //get spawn area then set bounding box
        this.setBoundingBox(new Rectangle()); //need more images
        this.setSprite(animalDesertTextures[0]); //temporary

        if (this.inventoryItem) {
            //drop non buffs
            this.onDestroy(this); //temporarily no inventory items
        }
        else { //drop buffs
            int rng = generator.nextInt(4);
            switch (rng) {
                case 0:
                    this.pickupType = ItemType.ATTACKSPEED_BUFF;
                    this.setSprite(animalDesertTextures[0]); //temporary
                    break;
                case 1:
                    this.pickupType = ItemType.REGEN_BUFF;
                    this.setSprite(animalDesertTextures[0]); //temporary
                    break;
                case 2:
                    this.pickupType = ItemType.DAMAGE_BUFF;
                    this.setSprite(animalDesertTextures[0]); //temporary
                    break;
                case 3:
                    this.pickupType = ItemType.EXTRA_LASER_BUFF;
                    this.setSprite(animalDesertTextures[0]); //temporary
                    break;
            }
        }

        this.addToRenderQueue();
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
                instigator.setDamageScale(instigator.getDamageScale() * 1.2f);
                break;
            case ATTACKSPEED_BUFF:
                instigator.setAttackSpeed(instigator.getAttackSpeed() * .9f);
                break;
            case REGEN_BUFF:
                instigator.setHealthRegen(instigator.getHealthRegen() + .1f);
                break;
            case EXTRA_LASER_BUFF:
                instigator.setAttacks(instigator.getAttacks() + 1);
                break;
        }

        this.setPendingRemoval(true);
    }

    @Override
    public void update(float deltaTime) {

    }
}
