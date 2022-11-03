package com.novita.myrecetasapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

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