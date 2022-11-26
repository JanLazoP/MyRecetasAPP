package com.novita.myrecetasapp.adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.novita.myrecetasapp.R;

public class RecetaActivity extends AppCompatActivity {

    TextView nombre,descripcion,ingredientes,pasos;
    ImageView imagen;

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

        Bundle rBundle = getIntent().getExtras();

        if(rBundle!=null){

            nombre.setText(rBundle.getString("nombre"));
            descripcion.setText(rBundle.getString("descripcion"));
            ingredientes.setText(rBundle.getString("ingredientes"));
            pasos.setText(rBundle.getString("pasos"));

            Glide.with(this).load(rBundle.getString("imagen")).into(imagen);

        }

    }
}