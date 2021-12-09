package org.izv.dmc.proyectofinal.model.repository;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

import org.izv.dmc.proyectofinal.model.entity.WeaponAspect;
import org.izv.dmc.proyectofinal.model.entity.Aspect;
import org.izv.dmc.proyectofinal.model.entity.Weapon;
import org.izv.dmc.proyectofinal.model.room.AspectDao;
import org.izv.dmc.proyectofinal.model.room.AspectDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Repository
{




        private static final String INIT = "init";


        private AspectDao dao;
        private LiveData<List<WeaponAspect>> allAspects;
        private LiveData<List<Aspect>> liveAspects;
        private LiveData<List<Weapon>> liveWeapons;
        private LiveData<Aspect> liveAspect;
        private LiveData<Weapon> liveWeapon;
        private MutableLiveData<Long> liveInsertResult;
        private MutableLiveData<List<Long>> liveInsertResults;

        private SharedPreferences preferences;


        public Repository(Context context) {
            AspectDatabase db = AspectDatabase.getDatabase(context);

            dao = db.getDao();
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
            liveInsertResult = new MutableLiveData<>();
            liveInsertResults = new MutableLiveData<>();

            if(!getInit()) {
                WeaponPreload();
                setInit();
            }
        }

        public void insertAspect(Aspect aspect, Weapon weapon) {
            Runnable r = () -> {
                aspect.idWeapon = insertWeaponGetId(weapon);
                if(aspect.idWeapon > 0) {
                    dao.insertAspect(aspect);
                }
            };
            new Thread(r).start();
        }

        public MutableLiveData<Long> getInsertResult() {
            return liveInsertResult;
        }

        public MutableLiveData<List<Long>> getInsertResults() {
            return liveInsertResults;
        }

        private long insertWeaponGetId(Weapon weapon) {
            List<Long> ids = dao.insertWeapon(weapon);
            if(ids.get(0) < 1) {
                return dao.getWeaponId(weapon.name);
            } else {
                return ids.get(0);
            }
        }

        public void insertAspect(Aspect... aspects) {
            Runnable r = () -> {
                List<Long> resultList = dao.insertAspects(aspects);
                liveInsertResult.postValue(resultList.get(0));
                liveInsertResults.postValue(resultList);
            };
            new Thread(r).start();
        }

        public void insertWeapon(Weapon... weapons) {
            Runnable r = () -> {
                dao.insertWeapon(weapons);
            };
            new Thread(r).start();
        }

        public void updateAspect(Aspect... aspects) {
            Runnable r = () -> {
                dao.updateAspects(aspects);
            };
            new Thread(r).start();
        }

        public void updateWeapon(Weapon... weapons) {
            Runnable r = () -> {
                dao.updateWeapon(weapons);
            };
            new Thread(r).start();
        }

        public void deleteAspect(Aspect... aspects) {
            Runnable r = () -> {
                dao.deleteAspect(aspects);
            };
            new Thread(r).start();
        }

        public void deleteWeapon(Weapon... weapons) {
            Runnable r = () -> {
                dao.deleteWeapon(weapons);
            };
            new Thread(r).start();
        }

        public LiveData<List<Aspect>> getAspects() {
            if(liveAspects == null) {
                liveAspects = dao.getAspects();
            }
            return liveAspects;
        }

        public LiveData<List<Weapon>> getWeapons() {
            if(liveWeapons == null) {
                liveWeapons = dao.getWeapons();
            }
            return liveWeapons;
        }

        public LiveData<Aspect> getAspect(long id) {
            if(liveAspect == null) {
                liveAspect = dao.getAspect(id);
            }
            return liveAspect;
        }

        public LiveData<Weapon> getWeapon(long id) {
            if(liveWeapon == null) {
                liveWeapon = dao.getWeapon(id);
            }
            return liveWeapon;
        }

        public LiveData<List<WeaponAspect>> getAllAspects() {
            if(allAspects == null) {
                allAspects = dao.getAllAspects();
            }
            return allAspects;
        }

        public void WeaponPreload() {
            String[] weaponNames = new String[] {"AWP","M4A4","Dual Berettas","Glock","AK-47","SSG-08","USP"
            ,"TEC-9","UMP-45","NOVA-12","MP7","Knife","Galil","P90","MAC-10","MP9"};
            Weapon[] weapons = new Weapon[weaponNames.length];
            Weapon weapon;
            int cont = 0;
            for (String weaponName: weaponNames) {
                weapon = new Weapon();
                weapon.name = weaponName;
                weapons[cont] = weapon;
                cont++;
            }
            insertWeapon(weapons);
        }




    public void setInit() {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(INIT, true);
            editor.commit();
        }

        public boolean getInit() {
            return preferences.getBoolean(INIT, false);
        }
    }
