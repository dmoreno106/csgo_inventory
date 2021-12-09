package org.izv.dmc.proyectofinal.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.izv.dmc.proyectofinal.model.entity.Aspect;
import org.izv.dmc.proyectofinal.model.entity.Weapon;
import org.izv.dmc.proyectofinal.model.entity.WeaponAspect;

import java.util.List;

@Dao
public interface AspectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertAspects(Aspect... aspects);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertAspect(Aspect aspect);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertWeapon(Weapon... weapon);


    @Update
    int updateAspects(Aspect... aspects);

    @Update
    int updateWeapon(Weapon... weapons);

    @Delete
    int deleteAspect(Aspect... aspects);


    @Delete
    int deleteWeapon(Weapon... weapons);


//para borrar todos las armas y aspectos
    @Query("delete from weapon")
    int deleteAllWeapons();

    @Query("delete from aspect")
    int deleteAllAspects();


    @Query("select * from aspect order by name asc")
    LiveData<List<Aspect>> getAspects();

    @Query("select a.*, w.id weapon_id, w.name weapon_name from aspect a join weapon w on a.idweapon = w.id order by name asc")
    LiveData<List<WeaponAspect>> getAllAspects();

    @Query("select * from weapon ")
    LiveData<List<Weapon>> getWeapons();

    @Query("select * from aspect where id = :id")
    LiveData<Aspect> getAspect(long id);

    @Query("select * from weapon where id = :id")
    LiveData<Weapon> getWeapon(long id);

    @Query("select id from weapon where name = :name")
    long getWeaponId(String name);
}
