package com.ict1009.ahg.interfaces;

import com.ict1009.ahg.gameplay.Entity;

public interface IDamageHandler { //if you implement this class, you will take and deal damage

    void takeDamage(float damage, int damageType, Entity instigator);

    void onTakeDamage(Entity instigator);

}
