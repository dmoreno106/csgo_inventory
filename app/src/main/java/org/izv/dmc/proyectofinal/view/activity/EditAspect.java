package org.izv.dmc.proyectofinal.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.Glide;

import org.izv.dmc.proyectofinal.R;
import org.izv.dmc.proyectofinal.model.entity.Aspect;
import org.izv.dmc.proyectofinal.model.entity.Weapon;
import org.izv.dmc.proyectofinal.viewmodel.AspectViewModel;
import org.izv.dmc.proyectofinal.viewmodel.WeaponViewModel;

import java.net.MalformedURLException;
import java.net.URL;

public class EditAspect extends AppCompatActivity {
        private EditText etName, etCondition,etUrl;
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
            etName = findViewById(R.id.etName);
            spRarity = findViewById(R.id.spRarity);
            etCondition = findViewById(R.id.etCondition);
            etUrl = findViewById(R.id.etUrl);
            ivImage = findViewById(R.id.ivImage);
            ibDelete=findViewById(R.id.ibDelete);
            etName.setText(aspect.name);
            etCondition.setText(aspect.condition);

            etUrl.setText(aspect.url);
            putRarity();
            //si la url existe la colocamos de imagen sino colocamos la de por defecto
            if(isValid(etUrl.getText().toString())){
                Glide.with(this).load(aspect.url).into(ivImage);
            }else{
              String  url="https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Question_mark.png/380px-Question_mark.png" ;
                Glide.with(this).load(url).into(ivImage);
            }

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
                case "Global":
                    spRarity.setSelection(5);
                    break;
            }
        }
        //boton de borrar
    private void defineDeleteListener() {
        AspectViewModel avm = new ViewModelProvider(this).get(AspectViewModel.class);
            ibDelete.setOnClickListener(view -> {
                AlertDialog.Builder builder  = new AlertDialog.Builder(this);
                builder.setTitle("Delete this Aspect")
                        .setMessage("You are about to delete this aspect, this action will be irreversible. Are you sure?")
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
        private Aspect getAspect() {
            String name = etName.getText().toString().trim();
            String condition = etCondition.getText().toString().trim();
            String rarity = spRarity.getSelectedItem().toString().trim();
            String url = etUrl.getText().toString().trim();
            Weapon weapon = (Weapon) spWeapon.getSelectedItem();
            Aspect aspect = new Aspect();
            aspect.name = name;
            aspect.rarity =rarity;
            aspect.condition = condition;
            aspect.url = url;
            aspect.idWeapon = weapon.id;
            aspect.id = this.aspect.id;
            return aspect;
        }
}
