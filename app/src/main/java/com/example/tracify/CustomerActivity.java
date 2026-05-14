package com.example.tracify;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tracify.databinding.ActivityCustomerBinding;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    ActivityCustomerBinding binding;
    authViewModel authVM;
    MyAdapter adapter = new MyAdapter(new ArrayList<>());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_customer);
       authVM = new ViewModelProvider(this).get(authViewModel.class);

       authVM.getRiders().observe(this,rider -> {
          adapter.setList(rider);
          binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
       });

        binding.recyclerView.setAdapter(adapter);
    }
}