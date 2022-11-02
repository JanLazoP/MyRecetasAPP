package com.novita.myrecetasapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.modelos.HomeHorizontalModelo;

import java.util.List;

public class HomeHorizontalAdapter extends RecyclerView.Adapter<HomeHorizontalAdapter.ViewHolder> {

   Context context;
   List<HomeHorizontalModelo> list;

    public HomeHorizontalAdapter(Context context, List<HomeHorizontalModelo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontales,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImagen());
        holder.nombre.setText(list.get(position).getNombre());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_horizontal);
            nombre = itemView.findViewById(R.id.texto_horizontal);
        }
    }
}
