package com.novita.myrecetasapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.novita.myrecetasapp.R;

public class SubirRecetaActivity extends AppCompatActivity {

    ImageView imgReceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_receta);

        imgReceta = (ImageView)findViewById(R.id.r_imagen);
    }

    public void btnSeleccionarImg(View view) {


        Intent selImagen = new Intent(Intent.ACTION_PICK);
        selImagen.setType("image/*");
        startActivityForResult(selImagen,1);




    }
}