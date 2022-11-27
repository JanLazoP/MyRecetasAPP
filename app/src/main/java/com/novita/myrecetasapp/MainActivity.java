package com.novita.myrecetasapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.novita.myrecetasapp.activities.BienvenidaActivity;
import com.novita.myrecetasapp.databinding.ActivityMainBinding;

//autor jan lazo, daniel vespa
public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView correoUsuario,nombreUsuario;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_map, R.id.nav_favoritos)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();                     
        userID = firebaseAuth.getCurrentUser().getUid();

        View headerView = navigationView.getHeaderView(0);


        correoUsuario = (TextView)headerView.findViewById(R.id.textViewCorreo);
        nombreUsuario = (TextView)headerView.findViewById(R.id.textNombreUsuario);




     DocumentReference documentReference = firebaseFirestore.collection("usuarios").document(userID);
     documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
         @Override
         public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
             if(error != null){
                 Log.d("TAG error n_usuario","onEvent: "+error.getMessage());
             }else{
                 nombreUsuario.setText(value.getString("Nombre"));
                 correoUsuario.setText(value.getString("correo"));
             }
         }
     });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void cerrarSesion(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), BienvenidaActivity.class));
        finish();
    }
}