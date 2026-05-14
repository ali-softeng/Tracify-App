package com.example.tracify;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class authViewModel extends ViewModel {

    private AuthRepository authRepository;

    public authViewModel(){
        authRepository = new AuthRepository();
    }

    public void login(String email, String password, OnCompleteListener<AuthResult> listener){
        authRepository.login(email,password,listener);
    }

    public void signUp(String name,String email, String password,String role, OnCompleteListener<AuthResult> listener){
        authRepository.signUp(name,email,password,role,listener);
    }

    public FirebaseUser getCurrentUser(){
        return authRepository.getCurrentUser();
    }

    public LiveData<List<Model>> getRiders(){
        return authRepository.getRiders();
    }
}
