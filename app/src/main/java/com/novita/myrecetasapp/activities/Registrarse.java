package com.novita.myrecetasapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.novita.myrecetasapp.MainActivity;
import com.novita.myrecetasapp.R;
import com.novita.myrecetasapp.ui.home.HomeFragment;

import java.util.HashMap;
import java.util.Map;

public class Registrarse extends AppCompatActivity {

    EditText nombre,correo,contrasena,repetircontrasena;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String TAG = "TAG";
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        nombre = findViewById(R.id.registro_nombre);
        correo = findViewById(R.id.registro_Correo);
        contrasena = findViewById(R.id.registro_contrasena);
        repetircontrasena = findViewById(R.id.registro_repetircontrasena);
        progressBar = findViewById(R.id.registrarBar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }





    }

    public void Registrar(final View view){
        String name = nombre.getText().toString().trim();
        final String email = correo.getText().toString().trim();
        String password = contrasena.getText().toString().trim();
        String repeatPassword = repetircontrasena.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            nombre.setError("Ingresar nombre");
            return;
        }
        if(TextUtils.isEmpty(email)){
            correo.setError("Ingresar correo");
            return;
        }
        if(TextUtils.isEmpty(password)){
            contrasena.setError("Ingresar contraseña");
            return;
        }
        if(TextUtils.isEmpty(repeatPassword)){
            repetircontrasena.setError("Ingresar contrasena");
            return;
        }
      // if((contrasena.getText())!=(repetircontrasena.getText())){
      //     repetircontrasena.setError("contraseña no coincide");
      //     return;
      // }

        progressBar.setVisibility(View.VISIBLE);

        //registrar usuario en firebase con firebaseauth
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    userID = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = firebaseFirestore.collection("usuarios").document(userID);
                    final Map<String,Object> mapUser = new HashMap<>();
                    mapUser.put("Nombre",name);
                    mapUser.put("correo",email);
                    documentReference.set(mapUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("TAG","onSuccess: perfil de usuario creado para "+ mapUser);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG","onFailure: "+ e.toString());
                        }
                    });

                    //envio de verificacion

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Snackbar.make(view, "Email de verificación enviado", BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"onFailure: Email no enviado"+ e.getMessage());
                        }
                    });

                    Snackbar.make(view,"Usuario creado", BaseTransientBottomBar.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
                else{
                    Snackbar.make(view,"Error! "+ task.getException().getMessage(),BaseTransientBottomBar.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);

                }

            }
        });


    }

    //redireccionamiento de botones
    public void login(View view) {
        startActivity(new Intent(Registrarse.this, LoginActivity.class));
    }

}