package com.example.ifapps_tubes02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ifapps_tubes02.databinding.PengumumanFragmentBinding;

public class PengumumanFragment extends Fragment {
    PengumumanFragmentBinding binding;

    public static PengumumanFragment newInstance(String title){
        PengumumanFragment fragment = new PengumumanFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = PengumumanFragmentBinding.inflate(inflater,container,false);

        return this.binding.getRoot();
    }

}
