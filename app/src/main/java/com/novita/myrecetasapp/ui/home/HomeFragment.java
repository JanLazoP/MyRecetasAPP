package com.novita.myrecetasapp.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.bumptech.glide.load.resource.gif.GifBitmapProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.activities.SubirRecetaActivity;
import com.novita.myrecetasapp.adapters.FavoritosVerticalAdapter;
import com.novita.myrecetasapp.adapters.HomeHorizontalAdapter;
import com.novita.myrecetasapp.adapters.HomeVerticalAdapter;
import com.novita.myrecetasapp.adapters.RecetaActivity;
import com.novita.myrecetasapp.adapters.RecetaAdapter;
import com.novita.myrecetasapp.interfaces.IComunicacionF;
import com.novita.myrecetasapp.modelos.FavoritosVerticalModelo;
import com.novita.myrecetasapp.modelos.HomeHorizontalModelo;
import com.novita.myrecetasapp.modelos.HomeVerticalModelo;
import com.novita.myrecetasapp.modelos.RecetaModelo;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    List<RecetaModelo> recetaLista;
    RecetaModelo recetaModelo;
    RecetaAdapter recetaAdapter;

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_home,container,false);

        recyclerView = vista.findViewById(R.id.home_ver_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(vista.getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recetaLista = new ArrayList<>();
        recetaAdapter = new RecetaAdapter((FragmentActivity) vista.getContext(),recetaLista);
        recyclerView.setAdapter(recetaAdapter);

        ProgressDialog progressDialog = new ProgressDialog(vista.getContext());
        progressDialog.setMessage("Cargando Elementos...");


        databaseReference = FirebaseDatabase.getInstance().getReference("Receta");

        progressDialog.show();

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                recetaLista.clear();

                for(DataSnapshot itemSnapshot: snapshot.getChildren()){

                    RecetaModelo recetaModelo = itemSnapshot.getValue(RecetaModelo.class);
                    recetaLista.add(recetaModelo);

                }

                recetaAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });

        return vista;
    }


}