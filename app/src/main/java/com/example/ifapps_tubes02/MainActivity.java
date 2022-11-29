package com.example.ifapps_tubes02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ifapps_tubes02.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());
    }
}