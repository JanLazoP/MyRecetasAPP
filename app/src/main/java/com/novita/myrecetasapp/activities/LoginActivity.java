package com.novita.myrecetasapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.novita.myrecetasapp.MainActivity;
import com.novita.myrecetasapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //redireccionamiento de botones
    public void registrar(View view) {
        startActivity(new Intent(LoginActivity.this, Registrarse.class));
    }

    public void MainActividad(View view) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}