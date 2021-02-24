package com.ict1009.ahg.gameplay;

import com.ict1009.ahg.interfaces.ISpawnPoint;

import java.util.Timer;
import java.util.TimerTask;

public class PickupSpawner implements ISpawnPoint {

    private long timer = 8000;
    Timer spawning;

    @Override
    public void startSpawning() {
        spawning = new Timer();
        spawning.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new Pickup(true).addToRenderQueue();
            }
        }, 0, timer);
    }

    @Override
    public void destroySpawner() {
        spawning.cancel();
    }

    @Override
    public void setTimer(long timer) {
        this.timer = timer;
    }
}
