package org.izv.dmc.proyectofinal.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.dmc.proyectofinal.R;
import org.izv.dmc.proyectofinal.model.entity.WeaponAspect;
import org.izv.dmc.proyectofinal.view.adapter.AspectAdapter;
import org.izv.dmc.proyectofinal.viewmodel.AspectViewModel;

import java.util.List;

public class AspectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }
    private void initialize() {
        //lista de aspectos
        RecyclerView rvAspect = findViewById(R.id.rvAspect);
        rvAspect.setLayoutManager(new LinearLayoutManager(this));

        AspectViewModel avm = new ViewModelProvider(this).get(AspectViewModel.class);
        AspectAdapter aspectAdapter = new AspectAdapter(this);

        rvAspect.setAdapter(aspectAdapter);


        LiveData<List<WeaponAspect>> listaWeaponAspect = avm.getAllAspects();
        listaWeaponAspect.observe(this, ascpects -> {
            aspectAdapter.setAspectList(ascpects);
        });

        FloatingActionButton fab = findViewById(R.id.fabAddAspect);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateAspectActivity.class);
            startActivity(intent);
        });
    }
}