package org.izv.dmc.proyectofinal.model.entity;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "weapon",
        indices = {@Index(value = "name", unique = true)})

public class Weapon {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @Override
    public String toString() {
        return name;
    }
}
