package com.novita.myrecetasapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.modelos.RecetaModelo;

import java.text.DateFormat;
import java.util.Calendar;

public class SubirRecetaActivity extends AppCompatActivity {

    ImageView imgReceta;
    Uri uri;
    EditText txtNombre,txtDecripcion,txtIngredientes,txtPasos;
    String imageUrl,userID;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_receta);

        imgReceta = (ImageView)findViewById(R.id.r_imagen);
        txtNombre = (EditText)findViewById(R.id.etxtNombre);
        txtDecripcion = (EditText)findViewById(R.id.etxtDescripcion);
        txtIngredientes = (EditText)findViewById(R.id.etxtIngredientes);
        txtPasos = (EditText)findViewById(R.id.etxtPasos);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userID = firebaseAuth.getCurrentUser().getUid();

    }

     public void btnSeleccionarImg(View view) {


         Intent selImagen = new Intent(Intent.ACTION_PICK);
         selImagen.setType("image/*");
         startActivityForResult(selImagen,1);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if(resultCode == RESULT_OK){
             uri = data.getData();
             imgReceta.setImageURI(uri);
         }
         else{
             Toast.makeText(this, "No se ha seleccionado una imagen", Toast.LENGTH_SHORT).show();
         }
    
     }

     public void subirImagen(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Receta subiendose...");
        progressDialog.show();

         StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("ImagenReceta").child(uri.getLastPathSegment());

         storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                 Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                 while(!uriTask.isComplete());
                 Uri urlImage = uriTask.getResult();
                 imageUrl = urlImage.toString();
                 subirReceta();
                 progressDialog.dismiss();

             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 progressDialog.dismiss();
             }
         });

        
     }

    public void btnsubirReceta(View view) {

        if(uri == null){
            Toast.makeText(this, "Por favor subir imagen", Toast.LENGTH_SHORT).show();
        }
        else if(txtNombre.getText().toString().isEmpty()){
            Toast.makeText(this, "Nombre no puede estar vacio", Toast.LENGTH_SHORT).show();
        }
        else if(txtDecripcion.getText().toString().isEmpty()){
            Toast.makeText(this, "Descripción no puede estar vacio", Toast.LENGTH_SHORT).show();
        }
        else if(txtIngredientes.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingredientes no puede estar vacio", Toast.LENGTH_SHORT).show();
        }
        else if(txtPasos.getText().toString().isEmpty()){
            Toast.makeText(this, "Pasos no puede estar vacio", Toast.LENGTH_SHORT).show();
        }else{
        subirImagen();
        }
    }

    public void subirReceta(){

        RecetaModelo recetaModelo = new RecetaModelo(
          txtNombre.getText().toString(),txtDecripcion.getText().toString(),txtIngredientes.getText().toString(),txtPasos.getText().toString(),imageUrl);

        String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Receta").child(myCurrentDateTime).setValue(recetaModelo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Log.d("TAG", " Receta añadida a base de datos");
                    
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", " la receta no se ha podido subir " + e.getMessage());

            }
        });

        String recetaID = firebaseFirestore.collection("usuarios").document(userID).collection("Recetas").document().getId();
        RecetaModelo fireStoreRecetaModelo = new RecetaModelo(txtNombre.getText().toString(),txtDecripcion.getText().toString(),txtIngredientes.getText().toString(),txtPasos.getText().toString(),imageUrl,myCurrentDateTime,recetaID);

        DocumentReference documentReference = firebaseFirestore.collection("usuarios").document(userID).collection("Recetas").document(recetaID);
        documentReference.set(fireStoreRecetaModelo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("TAG","receta añadida a firestore");
                Toast toast = Toast.makeText(getApplicationContext(),"Receta subida",Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","Receta no añadida a firestore"+ e.getMessage());
                Toast toast = Toast.makeText(getApplicationContext(),"Fallo al subir la receta "+ e.getMessage(),Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}