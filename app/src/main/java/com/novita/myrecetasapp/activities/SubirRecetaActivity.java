package com.novita.myrecetasapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
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
    String imageUrl;

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

         StorageReference storageReference = FirebaseStorage.getInstance()
                 .getReference().child("ImagenReceta").child(uri.getLastPathSegment());
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
        subirImagen();
    }

    public void subirReceta(){

        RecetaModelo recetaModelo = new RecetaModelo(
          txtNombre.getText().toString(),txtDecripcion.getText().toString(),txtIngredientes.getText().toString(),txtPasos.getText().toString(),imageUrl
        );

        String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Receta").child(myCurrentDateTime).setValue(recetaModelo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(SubirRecetaActivity.this, "La receta ha sido subida", Toast.LENGTH_SHORT).show();

                    finish();
                    
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SubirRecetaActivity.this, "Fallido", Toast.LENGTH_SHORT).show();

            }
        });

        
    }
}