package com.novita.myrecetasapp.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.adapters.RecetaAdapter;
import com.novita.myrecetasapp.modelos.RecetaModelo;

import java.util.ArrayList;
import java.util.List;

public class FavoritosFragment extends Fragment {


    RecyclerView recyclerView;
    List<RecetaModelo> recetaLista;
    RecetaModelo recetaModelo;
    EditText txtBuscar;
    RecetaAdapter recetaAdapter;
    String userID;
    String TAG = "tag";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView textoMensaje;
    ProgressDialog progressDialog;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // inflar la vista

        View root = inflater.inflate(R.layout.fragment_favoritos,container,false);

        recyclerView = root.findViewById(R.id.fav_ver_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(root.getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        txtBuscar = root.findViewById(R.id.editarBusquedaFav);
        textoMensaje = root.findViewById(R.id.textViewMensaje);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            textoMensaje.setText("Debe iniciar sesión");
        }else{
            userID = firebaseAuth.getCurrentUser().getUid();
            progressDialog = new ProgressDialog(root.getContext());
            progressDialog.setMessage("Cargando recetas...");

            recetaLista = new ArrayList<RecetaModelo>();
            progressDialog.show();

            final DocumentReference documentReference = firebaseFirestore.collection("usuarios").document(userID);
            documentReference.collection("favoritos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    recetaLista.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        RecetaModelo recetaModelo = documentSnapshot.toObject(RecetaModelo.class);
                        recetaModelo.setKey(recetaModelo.getKey());
                        recetaModelo.setRecetaKey(documentSnapshot.getId());
                        recetaLista.add(recetaModelo);
                    }
                    recetaAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            });

            txtBuscar.addTextChangedListener(new TextWatcher() {
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

            recetaAdapter = new RecetaAdapter((FragmentActivity) root.getContext(),recetaLista);
            recyclerView.setAdapter(recetaAdapter);

        }

        return root;
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