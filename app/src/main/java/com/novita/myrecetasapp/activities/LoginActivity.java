package com.novita.myrecetasapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.novita.myrecetasapp.MainActivity;
import com.novita.myrecetasapp.R;

public class LoginActivity extends AppCompatActivity {

    EditText correo,contrasena;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.editTextTextPersonName);
        contrasena = findViewById(R.id.editTextTextPassword);
        progressBar = findViewById(R.id.loginProgressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }




    }

    //redireccionamiento de botones
    public void registrar(View view) {
        startActivity(new Intent(LoginActivity.this, Registrarse.class));
    }


    public void IniciarSesion(View view) {

        String email = correo.getText().toString().trim();
        String password = contrasena.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            correo.setError("Email no ingresado");
            return;
        }
        if(TextUtils.isEmpty(password)){
            contrasena.setError("contraseña no ingresada");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else {
                    Snackbar.make(view,"Error!"+ task.getException().getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                    progressBar.setVisibility(View.VISIBLE);
                }

            }
        });
    }


}