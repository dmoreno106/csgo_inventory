package org.izv.dmc.proyectofinal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.izv.dmc.proyectofinal.model.entity.Weapon;
import org.izv.dmc.proyectofinal.model.repository.Repository;

import java.util.List;

public class WeaponViewModel extends AndroidViewModel
{
    private Repository repository;
    public WeaponViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
    }

    public void insertWeapon(Weapon... weapons) {
        repository.insertWeapon(weapons);
    }

    public void updateWeapon(Weapon... weapons) {
        repository.updateWeapon(weapons);
    }

    public void deleteWeapon(Weapon... weapons) {
        repository.deleteWeapon(weapons);
    }

    public LiveData<List<Weapon>> getWeapons() {
        return repository.getWeapons();
    }

    public LiveData<Weapon> getWeapon(long id) { return repository.getWeapon(id); }

}
