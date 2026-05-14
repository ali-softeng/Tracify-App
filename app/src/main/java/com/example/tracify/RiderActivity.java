package com.example.tracify;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tracify.databinding.ActivityRiderBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RiderActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityRiderBinding binding;

    private FusedLocationProviderClient fusedClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isTracking = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRiderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,10000)
                .build();

        binding.btnStart.setOnClickListener(v -> {
            if(!isTracking){
                funLocationCallBack();
                startLocationUpdates();
                isTracking = true;
                Toast.makeText(this, "Tracking Started", Toast.LENGTH_SHORT).show();
            }

        });

        binding.btnStop.setOnClickListener( v -> {
            if(isTracking){
                fusedClient.removeLocationUpdates(locationCallback);
                isTracking = false;
                Toast.makeText(this, "Tracking Stopped", Toast.LENGTH_SHORT).show();
            }

        } );
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                     startLocationUpdates();

            } else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
   }

    private void funLocationCallBack(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {

                for(Location location : locationResult.getLocations()){

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    binding.tvLatLng.setText("Lat: " + latitude + "Lng : " + longitude);

                    LatLng latLng = new LatLng(latitude,longitude);

                    String uid = FirebaseAuth.getInstance().getUid();

                    Map<String, Object> data = new HashMap<>();
                    data.put("lat",latitude);
                    data.put("lng",longitude);

                    if(uid != null){
                        db.collection("locations")
                                .document(uid).set(data);
                    }


                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Rider"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                }

            }
        };
    }

    private void startLocationUpdates(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 1
            );
            return;
        }
            fusedClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }
}