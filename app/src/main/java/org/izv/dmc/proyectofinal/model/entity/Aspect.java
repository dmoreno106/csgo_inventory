package org.izv.dmc.proyectofinal.model.entity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "aspect",
        indices = {@Index(value ={"name","idweapon"}, unique = true)},
        foreignKeys = {@ForeignKey(entity = Weapon.class, parentColumns = "id", childColumns = "idweapon", onDelete = ForeignKey.CASCADE)})
public class Aspect implements Parcelable {

    //id autonumérico
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "idweapon")
    public long idWeapon;

    @ColumnInfo(name = "condition")
    public String condition;

    @ColumnInfo(name = "rarity")
    public String rarity;

    @ColumnInfo(name = "fecha")
    public String fecha;

    @ColumnInfo(name = "url")
    public String url;

    public Aspect() {
    }

    protected Aspect(Parcel in) {
        id = in.readLong();
        name = in.readString();
        idWeapon = in.readLong();
        condition = in.readString();
        rarity = in.readString();
        url = in.readString();
        fecha=in.readString();
    }

    public static final Creator<Aspect> CREATOR = new Creator<Aspect>() {
        @Override
        public Aspect createFromParcel(Parcel in) {
            return new Aspect(in);
        }

        @Override
        public Aspect[] newArray(int size) {
            return new Aspect[size];
        }
    };

    public boolean isValid() {
        return !(name.isEmpty() || rarity.isEmpty() || condition.isEmpty() || url.isEmpty() || fecha.isEmpty() || idWeapon <= 0);
        //shortcut
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(idWeapon);
        dest.writeString(condition);
        dest.writeString(rarity);
        dest.writeString(url);
        dest.writeString(fecha);
    }
}