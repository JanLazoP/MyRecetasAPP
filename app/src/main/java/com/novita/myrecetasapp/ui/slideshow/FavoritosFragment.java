package com.novita.myrecetasapp.ui.slideshow;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.adapters.FavoritosVerticalAdapter;
import com.novita.myrecetasapp.adapters.HomeHorizontalAdapter;
import com.novita.myrecetasapp.adapters.HomeVerticalAdapter;
import com.novita.myrecetasapp.modelos.FavoritosVerticalModelo;
import com.novita.myrecetasapp.modelos.HomeHorizontalModelo;
import com.novita.myrecetasapp.modelos.HomeVerticalModelo;

import java.util.ArrayList;
import java.util.List;

public class FavoritosFragment extends Fragment {
    RecyclerView favoritosVerticalRecycler;


    ////////////////////////////// verticales modelo y adaptar, igual que el fragment home
    List<FavoritosVerticalModelo> favoritosVerticalModeloList;
    FavoritosVerticalAdapter favoritosVerticalAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflar la vista

        View root = inflater.inflate(R.layout.fragment_favoritos,container,false);

        favoritosVerticalRecycler = root.findViewById(R.id.fav_ver_recycler);


        /////////// recycler vertical
        favoritosVerticalModeloList = new ArrayList<>();

        favoritosVerticalModeloList.add(new FavoritosVerticalModelo(R.drawable.almuerzo_receta,"Pollo Coreano"));
        favoritosVerticalModeloList.add(new FavoritosVerticalModelo(R.drawable.almuerzo_receta2,"Pollo y arroz"));
        favoritosVerticalModeloList.add(new FavoritosVerticalModelo(R.drawable.almuerzo_receta3,"Pasta italiana"));
        favoritosVerticalModeloList.add(new FavoritosVerticalModelo(R.drawable.almuerzo_receta4,"Tortillas saludables"));

        favoritosVerticalAdapter = new FavoritosVerticalAdapter(getActivity(),favoritosVerticalModeloList);

        favoritosVerticalRecycler.setAdapter(favoritosVerticalAdapter);
        favoritosVerticalRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        favoritosVerticalRecycler.setHasFixedSize(true);
        favoritosVerticalRecycler.setNestedScrollingEnabled(false);



        return root;
    }
}