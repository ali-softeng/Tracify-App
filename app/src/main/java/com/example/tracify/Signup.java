package com.example.tracify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.tracify.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {

    ActivitySignupBinding binding;
    authViewModel authVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup);
        authVM = new ViewModelProvider(this).get(authViewModel.class);

        binding.btnSign.setOnClickListener( v -> {
            String name = binding.SName.getText().toString();
            String email = binding.SEmail.getText().toString();
            String password = binding.SPass.getText().toString();
            String role = "";

           if(name.isEmpty()){
               binding.SName.setError("Name is required");
               binding.SName.requestFocus();
               return;
           }

           if(email.isEmpty()){
               binding.SEmail.setError("Email is required");
               binding.SEmail.requestFocus();
               return;

           } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
               binding.SEmail.setError("Please provide a valid email");
               binding.SEmail.requestFocus();
               return;
           }

           if (password.isEmpty()){
               binding.SPass.setError("Password is required");
               binding.SPass.requestFocus();
               return;
           }

           if(binding.riderRole.isChecked()){
               role = "Rider";
           } else if(binding.customerRole.isChecked()){
               role = "Customer";
           } else {
               Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
               return;
           }

            authVM.signUp(name,email,password,role,task -> {
                if(task.isSuccessful()){
                    Toast.makeText(this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signup.this, Login.class));
                    finish();
                } else{
                    Toast.makeText(this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.textLogin.setOnClickListener( v -> {
            startActivity(new Intent(Signup.this, Login.class));
            finish();
        });
    }
}