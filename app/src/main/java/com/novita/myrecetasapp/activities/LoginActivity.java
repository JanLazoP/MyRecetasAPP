package com.novita.myrecetasapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.novita.myrecetasapp.MainActivity;
import com.novita.myrecetasapp.R;

public class LoginActivity extends AppCompatActivity {
    Button button_ingresar;
    EditText editTextTextEmail, editTextTextPassword;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextTextEmail = findViewById(R.id.editTextTextEmail);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        button_ingresar = findViewById(R.id.button_ingresar);
        mAuth = FirebaseAuth.getInstance();

        button_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUSU = editTextTextEmail.getText().toString().trim();
                String passUsu = editTextTextPassword.getText().toString().trim();

                if (emailUSU.isEmpty() && passUsu.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Llene Todos los Campos", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(emailUSU,passUsu);

                }
            }
        });{

        }
    }

    private void loginUser(String emailUSU, String passUsu) {
        mAuth.signInWithEmailAndPassword(emailUSU, passUsu).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Bienvenido,Disfrute De las recetas", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Contraseña o Usuario Incorrecto", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error al Iniciar Sesion", Toast.LENGTH_SHORT).show();
            }
        });
        }
    }


    //redireccionamiento de botones
    //public void registrar(View view) {
      //  startActivity(new Intent(LoginActivity.this, Registrarse.class));
    //}

    //public void MainActividad(View view) {
      //  startActivity(new Intent(LoginActivity.this, MainActivity.class));
    //}
//}