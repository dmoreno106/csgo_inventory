package org.izv.dmc.proyectofinal.view.adapter;

import android.content.Context;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.izv.dmc.proyectofinal.R;
import org.izv.dmc.proyectofinal.model.entity.Aspect;
import org.izv.dmc.proyectofinal.model.entity.Weapon;
import org.izv.dmc.proyectofinal.model.entity.WeaponAspect;
import org.izv.dmc.proyectofinal.view.adapter.viewholder.AspectViewHolder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AspectAdapter extends RecyclerView.Adapter<AspectViewHolder> {
    private List<WeaponAspect> aspectList;
    private Context context;

    public AspectAdapter(Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public AspectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aspect,parent,false);
        return new AspectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AspectViewHolder holder, int position) {
        WeaponAspect weaponAspect = aspectList.get(position);
        Aspect aspect = weaponAspect.aspect;
        holder.aspect = aspect;
        Weapon weapon = weaponAspect.weapon;
        holder.tvUrl.setText(aspect.url);
        holder.tvCondition.setText(aspect.condition);
        holder.tvRarity.setText(aspect.rarity );
        String url = aspect.url;

        //si la url existe la colocamos de imagen sino colocamos la de por defecto
        if(isValid(url)){
            Glide.with(context).load(url).into(holder.ivAspect);
        }else{
           url="https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Question_mark.png/380px-Question_mark.png" ;
           Glide.with(context).load(url).into(holder.ivAspect);
        }
        switch (holder.tvRarity.getText().toString()){
            case "Common":
                holder.ivAspect.setBackground(context.getDrawable(R.drawable.common));
                break;
            case "Uncommon":
                holder.ivAspect.setBackground(context.getDrawable(R.drawable.uncommon));
                break;
            case "Rare":
                holder.ivAspect.setBackground(context.getDrawable(R.drawable.rare));
                break;
            case "Mythical":
                holder.ivAspect.setBackground(context.getDrawable(R.drawable.mythical));
                break;
            case "Legendary":
                holder.ivAspect.setBackground(context.getDrawable(R.drawable.legendary));
                break;
            case "Global":
                holder.ivAspect.setBackground(context.getDrawable(R.drawable.global));
                break;
        }
        holder.tvWeapon.setText(weapon.name + " (" + weapon.id + ")");
        holder.tvName.setText(aspect.name);
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
    @Override
    public int getItemCount() {
        if(aspectList == null) {
            return 0;
        }
        return aspectList.size();
    }

    public void setAspectList(List<WeaponAspect> aspectList) {
        this.aspectList = aspectList;
        notifyDataSetChanged();
    }
}
