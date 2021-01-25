package structs;

public class AnimalSpawn extends SpawnPoint {
    private int wave; //the wave number scales the animal level and stats
    private boolean bossSpawn; //is this a boss spawn point?

    AnimalSpawn() {
        super();
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public boolean isBossSpawn() {
        return bossSpawn;
    }

    public void setBossSpawn(boolean bossSpawn) {
        this.bossSpawn = bossSpawn;
    }
}
