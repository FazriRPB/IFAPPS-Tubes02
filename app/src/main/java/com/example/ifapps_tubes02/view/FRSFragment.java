package com.example.ifapps_tubes02.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ifapps_tubes02.databinding.FrsFragmentBinding;

public class FRSFragment extends Fragment {
    FrsFragmentBinding binding;

    public static FRSFragment newInstance(String title){
        FRSFragment fragment = new FRSFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = FrsFragmentBinding.inflate(inflater,container,false);

        return this.binding.getRoot();
    }
}
