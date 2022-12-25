package com.example.ifapps_tubes02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ifapps_tubes02.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment {
    HomeFragmentBinding binding;

    public static HomeFragment newInstance(String title){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = HomeFragmentBinding.inflate(inflater,container,false);

        return this.binding.getRoot();
    }
}
