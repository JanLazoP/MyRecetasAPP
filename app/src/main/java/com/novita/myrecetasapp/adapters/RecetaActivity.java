package com.novita.myrecetasapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.modelos.RecetaModelo;

public class RecetaActivity extends AppCompatActivity {

    TextView nombre,descripcion,ingredientes,pasos;
    ImageView imagen,like,dislike;
    Button update,delete;
    String recetaNombre,recetaDescripcion,recetaIngredientes,recetaPasos;
    String key;
    String compararKey = "";
    String recetaKey = "";
    String imageUrl = "";
    String favoritoKey = "";
    String userID;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    //actividad donde se mostrara la receta
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        nombre = (TextView)findViewById(R.id.txtnombreReceta);
        descripcion = (TextView)findViewById(R.id.txtDescription);
        ingredientes = (TextView)findViewById(R.id.txtIngredientes);
        pasos = (TextView)findViewById(R.id.txtPasos);
        imagen = (ImageView)findViewById(R.id.rImagen);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        like = findViewById(R.id.like_imageView);
        dislike = findViewById(R.id.dislike_imagView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

      // funcionalidad para usuarios sin cuenta, encargarse de verificar si hay sesion de usuario y si no enviarlo al login con un intent que diga necesita una cuenta para realizar accion
      // if(firebaseAuth.getCurrentUser() == null){
      //     finish();
      // }


        userID = firebaseAuth.getCurrentUser().getUid();

        Bundle rBundle = getIntent().getExtras();

        if(rBundle!=null){

            recetaNombre = rBundle.getString("nombre");
            nombre.setText(recetaNombre);
            recetaDescripcion = rBundle.getString("descripcion");
            descripcion.setText(recetaDescripcion);
            recetaIngredientes = rBundle.getString("ingredientes");
            ingredientes.setText(recetaIngredientes);
            recetaPasos = rBundle.getString("pasos");
            pasos.setText(recetaPasos);
            key = rBundle.getString("valorkey");
            recetaKey = rBundle.getString("recetakeyvalor");
            imageUrl = rBundle.getString("imagen");
            Glide.with(this).load(rBundle.getString("imagen")).into(imagen);

        }

        Log.d("TAG activity receta","receta key"+ recetaKey);

        final DocumentReference favoritoDocumentReference = firebaseFirestore.collection("usuarios").document(userID);

        favoritoDocumentReference.collection("favoritos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    RecetaModelo recetaModelo = documentSnapshot.toObject(RecetaModelo.class);
                    if(key.equals(recetaModelo.getKey())){
                        like.setVisibility(View.VISIBLE);
                        dislike.setVisibility(View.GONE);
                        favoritoKey = documentSnapshot.getId();
                        Log.d("TAG","favoritokey : "+ favoritoKey);
                    }
                }
            }
        });
                        if(recetaKey != null){
                            DocumentReference documentReference = firebaseFirestore.collection("usuarios").document(userID).collection("Recetas").document(recetaKey);
                            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(error != null){
                                        Log.d("TAG", "onEvento"+error.getMessage());
                                    }else
                                    {
                                        compararKey = value.getString("key");
                                        if(key.equals(compararKey)){
                                            update.setVisibility(View.VISIBLE);
                                            delete.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            });
                        }
    }

    public void btnDislike(View view){
        //borrar Receta de favoritos

        DocumentReference documentReference = firebaseFirestore.collection("usuarios").document(userID).collection("favoritos").document(favoritoKey);
        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("TAG dislike success","receta eliminada de firestore");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG dislike fail","Receta no eliminada de firestore"+e.getMessage());
                Snackbar.make(findViewById(R.id.txtnombreReceta),"fallo al eliminar de favoritos"+e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        like.setVisibility(View.VISIBLE);
        dislike.setVisibility(View.GONE);
    }

    public void btnLike(final View view){

        favoritoKey = firebaseFirestore.collection("usuarios").document(userID).collection("favoritos").document().getId();
        RecetaModelo fireStoreRecetaModelo = new RecetaModelo(recetaNombre,recetaDescripcion,recetaIngredientes,recetaPasos,imageUrl,key,recetaKey);

        DocumentReference documentReference = firebaseFirestore.collection("usuarios").document(userID).collection("favoritos").document(favoritoKey);
        documentReference.set(fireStoreRecetaModelo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("TAG succes like","Receta añadida a firestore");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG fail like","Receta no añadida a firestore "+e.getMessage());
                Snackbar.make(view,"Fallo al subir receta "+ e.getMessage(),BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        like.setVisibility(View.GONE);
        dislike.setVisibility(View.VISIBLE);


    }
}

