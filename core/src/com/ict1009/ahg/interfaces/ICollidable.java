package com.ict1009.ahg.interfaces;

import com.ict1009.ahg.gameplay.Entity;

public interface ICollidable { //if you implement this, you will be blocked / collide with things

    boolean collisionTest(Entity target);

}
