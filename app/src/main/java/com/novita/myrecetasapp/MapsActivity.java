package com.novita.myrecetasapp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.novita.myrecetasapp.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Chile2 = new LatLng(-29.90504166041276, -71.25779386675524);
        LatLng Chile = new LatLng(-29.908412576325546, -71.25720040311022);
        LatLng Chile3 = new LatLng(-29.9056914844093, -71.26116723901846);
        LatLng Chile4 = new LatLng(-29.911444949340872, -71.27445770107744);
        mMap.addMarker(new MarkerOptions().position(Chile3).title("Espacio Sibarita"));
        mMap.addMarker(new MarkerOptions().position(Chile2).title("Chuck E. Cheese's"));
        mMap.addMarker(new MarkerOptions().position(Chile).title("Universidad Santo tomas"));
        mMap.addMarker(new MarkerOptions().position(Chile4).title("Restaurante Brisa"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Chile,15));
    }
}