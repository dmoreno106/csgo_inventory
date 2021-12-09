package org.izv.dmc.proyectofinal.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import org.izv.dmc.proyectofinal.R;
import org.izv.dmc.proyectofinal.model.entity.Aspect;
import org.izv.dmc.proyectofinal.model.entity.Weapon;
import org.izv.dmc.proyectofinal.viewmodel.AspectViewModel;
import org.izv.dmc.proyectofinal.viewmodel.WeaponViewModel;

public class CreateAspectActivity extends AppCompatActivity {

    private EditText etName,etCondition, etUrl;
    private Spinner spWeapon,spRarity;
    private ImageView ivImage;
    private Aspect aspect;
    private AspectViewModel avm;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_aspect);
        initialize();
    }

    private void initialize() {
        spWeapon = findViewById(R.id.spWeapon);
        etName = findViewById(R.id.etName);
        etCondition = findViewById(R.id.etCondition);
        spRarity = findViewById(R.id.spRarity);
        etUrl = findViewById(R.id.etUrl);
        ivImage = findViewById(R.id.ivImage);
        etUrl.setOnFocusChangeListener((v, hasFocus) -> {
            String url;
            if(!hasFocus) {
                if(!etUrl.getText().toString().isEmpty()) {
                    url =etUrl.getText().toString();
                    Glide.with(this).load(url).into(ivImage);

                }else {
                 url="https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Question_mark.png/380px-Question_mark.png";
                   Glide.with(this).load(url).into(ivImage);
                }

            }

        });
        getViewModel();
        defineAddListener();
        defineCancelListener();
    }

    private void defineCancelListener() {
        Button btCancel=findViewById(R.id.btCancel);
        btCancel.setOnClickListener(view -> {
            finish();
        });
    }

    private void defineAddListener() {
        Button btAdd = findViewById(R.id.btEdit);
        btAdd.setOnClickListener(v -> {
            Aspect aspect = getAspect();
            if(aspect.isValid()) {
                addPokemon(aspect);
            } else {
                Toast.makeText(this, "fields are not correct", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Aspect getAspect() {
        String name = etName.getText().toString().trim();
        String rarity = spRarity.getSelectedItem().toString().trim();
        String condition = etCondition.getText().toString().trim();
        String url = etUrl.getText().toString().trim();
        Weapon weapon = (Weapon) spWeapon.getSelectedItem();
        Aspect aspect = new Aspect();
        aspect.name = name;
        aspect.rarity = rarity;
        aspect.condition = condition;
        aspect.url = url;
        aspect.idWeapon = weapon.id;
        return aspect;
    }

    private void addPokemon(Aspect aspect) {
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
                Toast.makeText(this, "select a weapon", Toast.LENGTH_LONG).show();
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

    private void alert() {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        builder.setTitle("¿Keep adding weapons?")
                .setMessage("the aspect was inserted succesfully, do you desire keep adding aspects?")
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
                    }
                });
        builder.create().show();
    }

    private void cleanFields() {
        Glide.with(this).load("").into(ivImage);
        etUrl.setText("");
        etCondition.setText("");
        spRarity.setSelection(0);
        etName.setText("");
        spWeapon.setSelection(0);
        Toast.makeText(this, "Aspect inserted", Toast.LENGTH_LONG).show();
    }
}
