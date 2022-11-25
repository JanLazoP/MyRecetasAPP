package com.novita.myrecetasapp.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.activities.SubirRecetaActivity;
import com.novita.myrecetasapp.adapters.HomeHorizontalAdapter;
import com.novita.myrecetasapp.adapters.HomeVerticalAdapter;
import com.novita.myrecetasapp.adapters.RecetaActivity;
import com.novita.myrecetasapp.interfaces.IComunicacionF;
import com.novita.myrecetasapp.modelos.HomeHorizontalModelo;
import com.novita.myrecetasapp.modelos.HomeVerticalModelo;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //fragment principal donde se mostraran los itemas y categorias

    //definir vista
    View vista;

    //nuestros recicler view, la lista a usar del modelo horizontal y su adaptador
    RecyclerView homeHorizontalRecycler,homeVerticalRecycler;
    List<HomeHorizontalModelo> homeHorizontalModeloList;
    HomeHorizontalAdapter homeHorizontalAdapter;

    ////////////////////////////// verticales lista modelo y adaptador
    List<HomeVerticalModelo> homeVerticalModeloList;
    HomeVerticalAdapter homeVerticalAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
         vista = inflater.inflate(R.layout.fragment_home,container,false);

        //definir la vista de los recycler view
        homeHorizontalRecycler = vista.findViewById(R.id.home_hor_recycler);
        homeVerticalRecycler = vista.findViewById(R.id.home_ver_recycler);

        /////////// recycler horizontal arraylist
        homeHorizontalModeloList = new ArrayList<>();

        //añadiendo los items al arraylist
        homeHorizontalModeloList.add(new HomeHorizontalModelo(R.drawable.almuerzo,"almuerzos"));
        homeHorizontalModeloList.add(new HomeHorizontalModelo(R.drawable.salad,"ensaladas"));
        homeHorizontalModeloList.add(new HomeHorizontalModelo(R.drawable.dinner,"cenas"));
        homeHorizontalModeloList.add(new HomeHorizontalModelo(R.drawable.sandwich,"sandwich"));
        homeHorizontalModeloList.add(new HomeHorizontalModelo(R.drawable.dessert,"postres"));


        //armar adapter
        homeHorizontalAdapter = new HomeHorizontalAdapter(getActivity(),homeHorizontalModeloList);

        homeHorizontalRecycler.setAdapter(homeHorizontalAdapter);
        homeHorizontalRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        homeHorizontalRecycler.setHasFixedSize(true);
        homeHorizontalRecycler.setNestedScrollingEnabled(false);

        /////////// recycler vertical
        homeVerticalModeloList = new ArrayList<>();

        homeVerticalModeloList.add(new HomeVerticalModelo(R.drawable.almuerzo_receta,"Pollo Coreano"));
        homeVerticalModeloList.add(new HomeVerticalModelo(R.drawable.almuerzo_receta2,"Pollo y arroz"));
        homeVerticalModeloList.add(new HomeVerticalModelo(R.drawable.almuerzo_receta3,"Pasta italiana"));
        homeVerticalModeloList.add(new HomeVerticalModelo(R.drawable.almuerzo_receta4,"Tortillas saludables"));

        homeVerticalAdapter = new HomeVerticalAdapter(getActivity(),homeVerticalModeloList);

        homeVerticalRecycler.setAdapter(homeVerticalAdapter);
        homeVerticalRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        homeVerticalRecycler.setHasFixedSize(true);
        homeVerticalRecycler.setNestedScrollingEnabled(false);



        return vista;
    }

    public void fab_subirActivity(View view) {
        Intent intent = new Intent(getActivity(), SubirRecetaActivity.class);
        startActivity(intent);
    }
}