package com.example.tracify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.tracify.databinding.ActivityLoginBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    authViewModel authVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        authVM = new ViewModelProvider(this).get(authViewModel.class);


        binding.btnLogin.setOnClickListener( v -> {
            String email = binding.lEmail.getText().toString();
            String password = binding.lPass.getText().toString();

            if(email.isEmpty()){
                binding.lEmail.setError("Email is required");
                binding.lEmail.requestFocus();
                return;

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.lEmail.setError("Please provide a valid email");
                binding.lEmail.requestFocus();
                return;
            }

            if(password.isEmpty()){
                binding.lPass.setError("Password is required");
                binding.lPass.requestFocus();
                return;
            }

            authVM.login(email,password,task -> {
                if(task.isSuccessful()){
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                    String uid = authVM.getCurrentUser().getUid();
                    getRole(uid);

                } else{
                    Toast.makeText(this, "Account has not been created yet.", Toast.LENGTH_SHORT).show();
                }
            });

        });

        binding.textSign.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Signup.class));
            finish();
        });
    }

    private void getRole(String uid){
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener( t -> {
                    String role = t.getString("role");

                    if(role != null){
                        if(role.equals("Rider")){
                            startActivity(new Intent(Login.this, RiderActivity.class));
                            finish();
                        } else if (role.equals("Customer")) {
                            startActivity(new Intent(Login.this, CustomerActivity.class));
                            finish();
                        }
                        overridePendingTransition(0,0);
                    }

                });
    }
}