package org.izv.dmc.proyectofinal.view.adapter.viewholder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.dmc.proyectofinal.R;
import org.izv.dmc.proyectofinal.model.entity.Aspect;
import org.izv.dmc.proyectofinal.view.activity.EditAspect;

public class AspectViewHolder extends RecyclerView.ViewHolder {

   public TextView tvName, tvWeapon, tvRarity, tvCondition, tvUrl;
    public ImageView ivAspect;
    public Aspect aspect;

    public AspectViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvWeapon = itemView.findViewById(R.id.tvWeapon);
        tvRarity = itemView.findViewById(R.id.tvRarity);
        tvCondition = itemView.findViewById(R.id.tvCondition);
        tvUrl = itemView.findViewById(R.id.tvUrl);
        ivAspect = itemView.findViewById(R.id.ivAspect);
        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(itemView.getContext(), EditAspect.class);
            intent.putExtra("aspect", aspect);
            itemView.getContext().startActivity(intent);
        });
    }
}
