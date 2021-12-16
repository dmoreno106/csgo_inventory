package org.izv.dmc.proyectofinal.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.izv.dmc.proyectofinal.R;
import org.izv.dmc.proyectofinal.model.entity.Aspect;
import org.izv.dmc.proyectofinal.model.entity.Weapon;
import org.izv.dmc.proyectofinal.viewmodel.AspectViewModel;
import org.izv.dmc.proyectofinal.viewmodel.WeaponViewModel;

import java.net.MalformedURLException;
import java.net.URL;

public class EditAspect extends AppCompatActivity {
        private TextInputEditText tiName, tiCondition, tiUrl,tiDate;
        private Spinner spWeapon,spRarity;
        private ImageView ivImage;
        private ImageButton ibDelete;
        private Aspect aspect;

        private AspectViewModel avm;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_aspect);

            initialize();
        }

        private void initialize() {
            aspect = getIntent().getExtras().getParcelable("aspect");


            spWeapon = findViewById(R.id.spWeapon);
            tiName = findViewById(R.id.tiName);
            spRarity = findViewById(R.id.spRarity);
            tiCondition = findViewById(R.id.tiCondition);
            tiUrl = findViewById(R.id.tiUrl);
            tiDate=findViewById(R.id.tiDate);
            ivImage = findViewById(R.id.ivImage);
            ibDelete=findViewById(R.id.ibDelete);

            tiName.setText(aspect.name);
            tiCondition.setText(aspect.condition);
            tiUrl.setText(aspect.url);
            tiDate.setText(aspect.fecha);

            tiDate.setOnClickListener(view -> {
                DatePickerFragment newFragment = new DatePickerFragment(tiDate);
                newFragment.show(getSupportFragmentManager(), "datepicker");
            });
            putRarity();
            //si la url existe la colocamos de imagen sino colocamos la de por defecto

            if(isValid(tiUrl.getText().toString())){
                Glide.with(this).load(tiUrl.getText().toString()).into(ivImage);
            }else {
                Glide.with(this).load(getString(R.string.url_default)).into(ivImage);
            }


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

            defineEditListener();
            defineCancelListener();
            defineDeleteListener();
        }

        //indicamos la rareza del objeto para actualizarla cada vez
        public void putRarity(){
            switch (aspect.rarity){
                case "common":
                spRarity.setSelection(0);
                    break;
                case "Uncommon":
                    spRarity.setSelection(1);
                    break;
                case "Rare":
                    spRarity.setSelection(2);
                    break;
                case "Mythical":
                    spRarity.setSelection(3);
                    break;
                case "Legendary":
                    spRarity.setSelection(4);
                    break;
                case "Ancestral":
                    spRarity.setSelection(5);
                    break;
                case "Global":
                    spRarity.setSelection(6);
                    break;
            }
        }
        //para el boton  borrar
    private void defineDeleteListener() {
        AspectViewModel avm = new ViewModelProvider(this).get(AspectViewModel.class);
            ibDelete.setOnClickListener(view -> {
                AlertDialog.Builder builder  = new AlertDialog.Builder(this);
                builder.setTitle(R.string.title_erase)
                        .setMessage(R.string.message_erase)
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                avm.deleteAspects(aspect);
                                finish();
                            }
                        });
                builder.create().show();

            });
    }

    //para el boton editar
    private void defineEditListener() {
            AspectViewModel avm = new ViewModelProvider(this).get(AspectViewModel.class);
            WeaponViewModel wvm = new ViewModelProvider(this).get(WeaponViewModel.class);
            wvm.getWeapons().observe(this, weapons -> {
                Weapon weapon = new Weapon();
                weapon.id = 0;
                weapon.name = getString(R.string.default_weapon);
                weapons.add(0, weapon);
                ArrayAdapter<Weapon> adapter =
                        new ArrayAdapter<Weapon>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, weapons);
                spWeapon.setAdapter(adapter);
                spWeapon.setSelection((int) aspect.idWeapon);
            });

            Button btEdit = findViewById(R.id.btEdit);
            btEdit.setOnClickListener(v -> {
                Aspect aspect = getAspect();
                if (aspect.isValid()){
                    avm.updateAspect(aspect);
                    finish();
                }else{
                    Toast.makeText(this, R.string.incorrect_fields, Toast.LENGTH_LONG).show();
                }
            });
        }

    //este método comprueba que la url exista
    private boolean isValid(String urlString) {
        try {
            URL url = new URL(urlString);
            return URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString).matches();
        } catch (MalformedURLException e) {

        }

        return false;
    }

        private void defineCancelListener() {
            Button btCancel=findViewById(R.id.btCancel);
            btCancel.setOnClickListener(view -> {
                finish();
            });
        }



        //crea el nuevo aspecto con los campos editados
        private Aspect getAspect() {
            String name = tiName.getText().toString().trim();
            String condition = tiCondition.getText().toString().trim();
            String rarity = spRarity.getSelectedItem().toString().trim();
            String url = tiUrl.getText().toString().trim();
            String fecha=tiDate.getText().toString().trim();
            Weapon weapon = (Weapon) spWeapon.getSelectedItem();
            Aspect aspect = new Aspect();
            aspect.name = name;
            aspect.rarity =rarity;
            aspect.condition = condition;
            aspect.fecha=fecha;
            aspect.url = url;
            aspect.idWeapon = weapon.id;
            aspect.id = this.aspect.id;
            return aspect;
        }
}
