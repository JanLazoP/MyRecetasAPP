package com.novita.myrecetasapp.adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.novita.myrecetasapp.R;

public class RecetaActivity extends AppCompatActivity {

    //actividad donde se mostrara la receta
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);
    }
}