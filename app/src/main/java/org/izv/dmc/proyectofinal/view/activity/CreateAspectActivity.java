package org.izv.dmc.proyectofinal.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import  com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import org.izv.dmc.proyectofinal.R;
import org.izv.dmc.proyectofinal.model.entity.Aspect;
import org.izv.dmc.proyectofinal.model.entity.Weapon;
import org.izv.dmc.proyectofinal.viewmodel.AspectViewModel;
import org.izv.dmc.proyectofinal.viewmodel.WeaponViewModel;

import java.net.MalformedURLException;
import java.net.URL;

public class CreateAspectActivity extends AppCompatActivity {

    private TextInputEditText tiName, tiCondition, tiUrl,tiDate;

    private Spinner spWeapon,spRarity;
    private ImageView ivImage;
    private Aspect aspect;
    private AspectViewModel avm;
    private boolean firstTime = true;
    private  Button btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_aspect);
        initialize();
    }

    private void initialize() {
        spWeapon = findViewById(R.id.spWeapon);
        tiName = findViewById(R.id.tiName);
        tiDate=findViewById(R.id.tiDate);
        tiCondition = findViewById(R.id.tiCondition);
        spRarity = findViewById(R.id.spRarity);
        tiUrl = findViewById(R.id.tiUrl);
        ivImage = findViewById(R.id.ivImage);
        btCancel=findViewById(R.id.btCancel);
        tiDate.setOnClickListener(view -> {
            DatePickerFragment newFragment = new DatePickerFragment(tiDate);
            newFragment.show(getSupportFragmentManager(), "datepicker");
        });

        Glide.with(this).load(getString(R.string.url_default)).into(ivImage);
        tiUrl.setOnFocusChangeListener((v, hasFocus) -> {
             String url;



            if(!hasFocus) {
                if(!tiUrl.getText().toString().isEmpty() && isValid(tiUrl.getText().toString())) {

                    url = tiUrl.getText().toString();
                    Glide.with(this).load(url).into(ivImage);
                }else {

                   Glide.with(this).load(getString(R.string.url_default)).into(ivImage);
                }

            }

        });
        getViewModel();
        defineAddListener();
        defineCancelListener();
    }

    private void defineCancelListener() {

        btCancel.setOnClickListener(view -> {
            finish();
        });
    }

    private void defineAddListener() {
        Button btAdd = findViewById(R.id.btEdit);
        btAdd.setOnClickListener(v -> {
            Aspect aspect = getAspect();
            if(aspect.isValid()) {
                addAspect(aspect);
            } else {
                Toast.makeText(this, R.string.incorrect_fields, Toast.LENGTH_LONG).show();
            }
        });
    }

    private Aspect getAspect() {
        String name = tiName.getText().toString().trim();
        String rarity = spRarity.getSelectedItem().toString().trim();
        String condition = tiCondition.getText().toString().trim();
        String url = tiUrl.getText().toString().trim();
        String fecha=tiDate.getText().toString().trim();
        Weapon weapon = (Weapon) spWeapon.getSelectedItem();
        Aspect aspect = new Aspect();
        aspect.name = name;
        aspect.rarity = rarity;
        aspect.condition = condition;
        aspect.fecha=fecha;
        aspect.url = url;
        aspect.idWeapon = weapon.id;
        return aspect;
    }

    private void addAspect(Aspect aspect) {
        avm.insertAspect(aspect);
    }

    private void getViewModel() {
        avm = new ViewModelProvider(this).get(AspectViewModel.class);


        avm.getInsertResults().observe(this, list -> {
            if(list.get(0) > 0) {
                if(firstTime) {
                    firstTime = false;
                    alert();
                } else {
                    cleanFields();
                }
            } else {
                Toast.makeText(this, R.string.already_saved, Toast.LENGTH_LONG).show();
            }
        });

        WeaponViewModel tvm = new ViewModelProvider(this).get(WeaponViewModel.class);
        tvm.getWeapons().observe(this, types -> {
            Weapon weapon = new Weapon();
            weapon.id = 0;
            weapon.name = getString(R.string.default_weapon);
            types.add(0, weapon);
            ArrayAdapter<Weapon> adapter =
                    new ArrayAdapter<Weapon>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
            spWeapon.setAdapter(adapter);
        });
    }


    private boolean isValid(String urlString) {
        try {
            URL url = new URL(urlString);
            return URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString).matches();
        } catch (MalformedURLException e) {

        }

        return false;
    }

    private void alert() {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_alert)
                .setMessage(R.string.message_alert)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cleanFields();
                        btCancel.setText(R.string.Finish);
                    }
                });
        builder.create().show();
    }

    private void cleanFields() {
        Glide.with(this).load("").into(ivImage);
        tiUrl.setText("");
        tiCondition.setText("");
        spRarity.setSelection(0);
        tiName.setText("");
        tiDate.setText("");
        spWeapon.setSelection(0);
        Toast.makeText(this, R.string.inserted,Toast.LENGTH_LONG).show();
        Glide.with(this).load(getString(R.string.url_default)).into(ivImage);
    }
}
