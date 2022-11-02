package com.novita.myrecetasapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.novita.myrecetasapp.R;

public class Registrarse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
    }

    public void login(View view) {
        startActivity(new Intent(Registrarse.this, LoginActivity.class));
    }

    public void registrar(View view) {
        startActivity(new Intent(Registrarse.this,LoginActivity.class));
    }
}