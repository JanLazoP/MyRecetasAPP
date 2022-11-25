package com.novita.myrecetasapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.modelos.FavoritosVerticalModelo;

import java.util.List;

public class FavoritosVerticalAdapter extends RecyclerView.Adapter<FavoritosVerticalAdapter.ViewHolder> {
    Context context;
    List<FavoritosVerticalModelo> list;
//adapter de los cardview que se mostraran hacia abajo en la app
    public FavoritosVerticalAdapter(Context context, List<FavoritosVerticalModelo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FavoritosVerticalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoritosVerticalAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_verticales,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritosVerticalAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.nombre.setText(list.get(position).getNombre());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                Intent intent = new Intent(context,RecetaActivity.class);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nombre;
        CardView cardView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ver_imagen);
            nombre = itemView.findViewById(R.id.ver_nombre);

            cardView = itemView.findViewById(R.id.CardReceta);


        }
    }
}
