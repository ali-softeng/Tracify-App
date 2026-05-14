package com.example.tracify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {

    authViewModel authVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        authVM = new ViewModelProvider(this).get(authViewModel.class);

        ImageView img = findViewById(R.id.splash);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade_in_scale);
        img.startAnimation(animation);

        new Handler().postDelayed(() -> {
            FirebaseUser user = authVM.getCurrentUser();
            if(user != null){
                getRole(user.getUid());
            } else {
                startActivity(new Intent(SplashActivity.this, Login.class));
            }
        },2000);
    }

    private void getRole(String uid){
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener( t -> {
                    String role = t.getString("role");

                    if(role != null){
                        if(role.equals("Rider")){
                            startActivity(new Intent(SplashActivity.this, RiderActivity.class));
                            finish();
                        } else if (role.equals("Customer")) {
                            startActivity(new Intent(SplashActivity.this, CustomerActivity.class));
                            finish();
                        }
                        overridePendingTransition(0,0);
                    }

                });
    }
}