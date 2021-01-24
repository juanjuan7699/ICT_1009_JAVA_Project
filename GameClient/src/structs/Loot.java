package structs;

import java.util.Random;

public class Loot extends Entity {

    private int rarity;
    private int lootType; //probably gonna be another enum
    private int amount;

    public Loot() { //loot auto generates on spawn
        super(EntityType.INTERACT_ENTITY);
        regenRandomLoot();
    }

    public void regenRandomLoot() {
        this.rarity = new Random().nextInt(5);
        this.lootType = new Random().nextInt(5);
        this.amount = new Random().nextInt(10);
    }

    public void tryPickup(Entity instigator) {
        if (instigator.getEntityType() != EntityType.PLAYER_ENTITY) {
            //cannot loot
            return;
        }
        //do loot and despawn
        super.destroyEntity(true);
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getLootType() {
        return lootType;
    }

    public void setLootType(int lootType) {
        this.lootType = lootType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
