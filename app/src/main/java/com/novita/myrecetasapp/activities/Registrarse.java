package com.novita.myrecetasapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.novita.myrecetasapp.R;
import java.util.Map;

public class Registrarse extends AppCompatActivity {

    Button registrarsebtn;
    EditText nombre,correo,contrasena;
    FirebaseFirestore mFS;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        nombre = findViewById(R.id.nombre);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        registrarsebtn = findViewById(R.id.registrarsebtn);
        mFS = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        registrarsebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomUsu = nombre.getText().toString().trim();
                String emailUsu = correo.getText().toString().trim();
                String passUsu = contrasena.getText().toString().trim();


                if (nomUsu.isEmpty() && emailUsu.isEmpty() && passUsu.isEmpty()){
                    Toast.makeText(Registrarse.this, "LLene Todos Los Campos", Toast.LENGTH_SHORT).show();
                }else {RegisterUser(nomUsu,emailUsu,passUsu);
            }
        }
        });
}
    private void RegisterUser(String nomUsu, String emailUsu, String passUsu) {
   // if (contrasena.getText().equals(repetircontrasena.getText())){

     // }else{
       //     Toast.makeText(this, "Las Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        //}
        mAuth.createUserWithEmailAndPassword(emailUsu,passUsu).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String,Object> map = new HashMap<>();
                map.put("id",id);
                map.put("nombre",nomUsu);
                map.put("correo",emailUsu);
                map.put("contraseña",passUsu);


                mFS.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(Registrarse.this,LoginActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Registrarse.this, "Error Al Guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registrarse.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
