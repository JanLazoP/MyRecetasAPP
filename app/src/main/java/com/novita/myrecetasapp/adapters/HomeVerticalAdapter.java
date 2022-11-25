package com.novita.myrecetasapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.modelos.HomeVerticalModelo;

import java.util.List;


public class HomeVerticalAdapter extends RecyclerView.Adapter<HomeVerticalAdapter.ViewHolder> {

    //adapter de los cardview que se mostraran hacia abajo en la app

    Context context;
    List<HomeVerticalModelo> list;

    public HomeVerticalAdapter(Context context, List<HomeVerticalModelo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeVerticalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_verticales,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVerticalAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
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
            imageView = itemView.findViewById(R.id.ver_imagen);
            nombre = itemView.findViewById(R.id.ver_nombre);
        }
    }
}
