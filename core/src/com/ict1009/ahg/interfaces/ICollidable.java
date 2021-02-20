package com.ict1009.ahg.interfaces;

import com.badlogic.gdx.math.Rectangle;

public interface ICollidable { //if you implement this, you will be blocked / collide with things

    void collisionTest(Rectangle target);

}
