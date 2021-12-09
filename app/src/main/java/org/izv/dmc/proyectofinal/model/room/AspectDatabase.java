package org.izv.dmc.proyectofinal.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.izv.dmc.proyectofinal.model.entity.Aspect;
import org.izv.dmc.proyectofinal.model.entity.Weapon;

@Database(entities = {Aspect.class, Weapon.class}, version = 1, exportSchema = false)
public abstract class AspectDatabase extends RoomDatabase
{
    public abstract AspectDao getDao();

    private static volatile AspectDatabase INSTANCE;

    /* versión simplificada */
    public static AspectDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AspectDatabase.class, "aspect").build();
        }
        return INSTANCE;
    }

}
