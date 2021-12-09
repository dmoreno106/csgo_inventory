package org.izv.dmc.proyectofinal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.dmc.proyectofinal.model.entity.Aspect;
import org.izv.dmc.proyectofinal.model.entity.Weapon;
import org.izv.dmc.proyectofinal.model.entity.WeaponAspect;
import org.izv.dmc.proyectofinal.model.repository.Repository;

import java.util.List;

public class AspectViewModel extends AndroidViewModel {

    private Repository repository;

    public AspectViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
    }
    public void insertAspect(Aspect... aspects) {
        repository.insertAspect(aspects);
    }

    public void updateAspect(Aspect... aspects) {
        repository.updateAspect(aspects);
    }

    public void deleteAspects(Aspect... aspects) {
        repository.deleteAspect(aspects);
    }

    public LiveData<List<Aspect>> getAspects() { return repository.getAspects();}

    public LiveData<Aspect> getAspect(long id) {
        return repository.getAspect(id);
    }

    public void insertAspect(Aspect aspect, Weapon weapon) {
        repository.insertAspect(aspect, weapon); }

    public LiveData<List<WeaponAspect>> getAllAspects() {
        return repository.getAllAspects();
    }

    public MutableLiveData<Long> getInsertResult() {
        return repository.getInsertResult();
    }

    public MutableLiveData<List<Long>> getInsertResults() {
        return repository.getInsertResults();
    }

}
