package com.novita.myrecetasapp.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.adapters.RecetaAdapter;
import com.novita.myrecetasapp.modelos.RecetaModelo;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    List<RecetaModelo> recetaLista;
    RecetaModelo recetaModelo;
    RecetaAdapter recetaAdapter;
    EditText editBuscar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    String TAG = "TAG";
    ProgressDialog progressDialog;


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

        editBuscar = (EditText)vista.findViewById(R.id.editarBusqueda);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(vista.getContext());
        progressDialog.setMessage("Cargando recetas...");



        recetaLista = new ArrayList<RecetaModelo>();

        recetaAdapter = new RecetaAdapter((FragmentActivity) vista.getContext(),recetaLista);
        recyclerView.setAdapter(recetaAdapter);

        progressDialog.show();


        databaseReference = FirebaseDatabase.getInstance().getReference("Receta");

        progressDialog.show();

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                recetaLista.clear();

                for(DataSnapshot itemSnapshot: snapshot.getChildren()){

                    RecetaModelo recetaModelo = itemSnapshot.getValue(RecetaModelo.class);
                    recetaModelo.setKey(itemSnapshot.getKey());
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

        editBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrar(editable.toString());
            }
        });

        return vista;
    }

    private void filtrar(String toString) {

        ArrayList<RecetaModelo> filtrarLista = new ArrayList<>();
        for (RecetaModelo item:recetaLista){
            if(item.getNombre().toLowerCase().contains(toString.toLowerCase())){

                filtrarLista.add(item);

            }
        }

        recetaAdapter.listaFiltrada(filtrarLista);

    }


}