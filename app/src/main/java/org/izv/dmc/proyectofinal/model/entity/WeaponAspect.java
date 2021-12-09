package org.izv.dmc.proyectofinal.model.entity;

import androidx.room.Embedded;

public class WeaponAspect {

    @Embedded
    public Aspect aspect;

    @Embedded(prefix="weapon_")
    public Weapon weapon;
}
