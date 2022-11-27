package com.novita.myrecetasapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.novita.myrecetasapp.MainActivity;
import com.novita.myrecetasapp.R;

public class BienvenidaActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }


    //metodos para redirigir al usuario segun la opcion
    public void registrar(View view) {
        startActivity(new Intent(BienvenidaActivity.this, Registrarse.class));
    }
    public void login(View view) {
        startActivity(new Intent(BienvenidaActivity.this, LoginActivity.class));
    }
}