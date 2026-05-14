package com.example.tracify;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthRepository {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public AuthRepository(){
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void login(String email, String password, OnCompleteListener<AuthResult> listener){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(listener);
    }

    public void signUp(String name, String email, String password, String role, OnCompleteListener<AuthResult> listener){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if(auth.getCurrentUser() != null){
                            String uid = auth.getCurrentUser().getUid();

                            Map<String, Object > user = new HashMap<>();
                            user.put("uid", uid);
                            user.put("name", name);
                            user.put("email",email);
                            user.put("role", role);


                            firestore.collection("users").document(uid).set(user)
                                    .addOnSuccessListener(s -> {
                                        listener.onComplete(task);
                                    })
                                    .addOnFailureListener( f -> {
                                        listener.onComplete(task);
                                    });
                        }
                    } else {
                        listener.onComplete(task);

                    }

                });
    }


    public FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }

    public LiveData<List<Model>> getRiders(){
       MutableLiveData<List<Model>> list = new MutableLiveData<>();

       firestore.collection("users").addSnapshotListener((value, error) -> {
           if(error != null){
               return;
           }

           List<Model> riders = new ArrayList<>();
           if(value != null){
               for(DocumentSnapshot doc : value.getDocuments()){
                   Model model = doc.toObject(Model.class);
                   if(model != null && model.getRole() != null){
                       if(model.getRole().equals("Rider")){
                           riders.add(model);
                       }
                   }
               }
           }
           list.setValue(riders);
       });
       return list;
    }
}
