package org.izv.dmc.proyectofinal.viewmodel;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import org.izv.dmc.proyectofinal.model.repository.Repository;

public class CommonViewModel extends ViewModel {

    private Context context;
    private Repository repository;

    public CommonViewModel() {
    }

    public void setContext(Context context) {
        this.context = context;
    }
}