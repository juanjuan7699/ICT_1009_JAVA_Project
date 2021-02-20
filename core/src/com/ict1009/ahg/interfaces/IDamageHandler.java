package com.ict1009.ahg.interfaces;

public interface IDamageHandler { //if you inplement this class, you will take and deal damage

    void takeDamage(float damage, int damageType);

    void onTakeDamage();

}
