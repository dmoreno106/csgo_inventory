package org.izv.dmc.proyectofinal.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.dmc.proyectofinal.R;

public class WeaponViewHolder extends RecyclerView.ViewHolder {
    private TextView tvWeapon;
    public WeaponViewHolder(@NonNull View itemView) {
        super(itemView);
        tvWeapon=itemView.findViewById(R.id.tvWeapon);
    }
    public TextView getWeaponItemView() {
        return tvWeapon;
    }
}
