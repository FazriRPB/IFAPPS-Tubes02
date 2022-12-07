package com.example.ifapps_tubes02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ifapps_tubes02.databinding.PertemuanFragmentBinding;

public class Pertemuanfragment extends Fragment {
    PertemuanFragmentBinding binding;

    public static Pertemuanfragment newInstance(String title){
        Pertemuanfragment fragment = new Pertemuanfragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = PertemuanFragmentBinding.inflate(inflater,container,false);

        return this.binding.getRoot();
    }
}
