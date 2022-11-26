package com.novita.myrecetasapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.modelos.RecetaModelo;

import java.util.ArrayList;
import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaViewHolder> {


    FragmentActivity context;
    List<RecetaModelo> recetaModeloList;

    public RecetaAdapter(FragmentActivity context, List<RecetaModelo> recetaModeloList) {
        this.context = context;
        this.recetaModeloList = recetaModeloList;
    }

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        return new RecetaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_verticales,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecetaViewHolder holder, int position) {

        Glide.with(context).load(recetaModeloList.get(position).getImg()).into(holder.imageView);
        holder.nombre.setText(recetaModeloList.get(position).getNombre());
        holder.descripcion.setText(recetaModeloList.get(position).getDescripcion());
        holder.ingredientes.setText(recetaModeloList.get(position).getIngredientes());
        holder.pasos.setText(recetaModeloList.get(position).getPasos());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RecetaActivity.class);
                intent.putExtra("imagen",recetaModeloList.get(holder.getAdapterPosition()).getImg());
                intent.putExtra("nombre",recetaModeloList.get(holder.getAdapterPosition()).getNombre());
                intent.putExtra("descripcion",recetaModeloList.get(holder.getAdapterPosition()).getDescripcion());
                intent.putExtra("ingredientes",recetaModeloList.get(holder.getAdapterPosition()).getIngredientes());
                intent.putExtra("pasos",recetaModeloList.get(holder.getAdapterPosition()).getPasos());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return recetaModeloList.size();
    }
}

class RecetaViewHolder extends RecyclerView.ViewHolder{



    ImageView imageView;
    TextView nombre,descripcion,ingredientes,pasos;
    CardView cardView;



    public RecetaViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ver_imagen);
        nombre = itemView.findViewById(R.id.ver_nombre);
        descripcion = itemView.findViewById(R.id.ver_descripcion);
        ingredientes = itemView.findViewById(R.id.ver_ingredientes);
        pasos = itemView.findViewById(R.id.ver_pasos);

        cardView = itemView.findViewById(R.id.CardReceta);


    }
}
