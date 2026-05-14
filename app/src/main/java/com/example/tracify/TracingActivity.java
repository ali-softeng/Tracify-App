package com.example.tracify;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tracify.databinding.ActivityTracingBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class TracingActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTracingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTracingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getRiderLocation();

    }

    private void getRiderLocation(){
        String riderID = getIntent().getStringExtra("riderID");
        if(riderID != null){
            FirebaseFirestore.getInstance().collection("locations").
                    document(riderID).addSnapshotListener((value, error) -> {
                        if(error != null){
                            return;
                        }

                        if(value != null && value.exists()){
                            Double lat = value.getDouble("lat");
                            Double lng = value.getDouble("lng");

                            if(lat != null && lng != null){
                                LatLng latLng = new LatLng(lat,lng);
                                mMap.clear();
                                mMap.addMarker(new MarkerOptions().position(latLng).title("Rider Position"));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Rider ID not found", Toast.LENGTH_SHORT).show();
        }
    }
}