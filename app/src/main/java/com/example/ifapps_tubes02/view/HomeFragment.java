package com.example.ifapps_tubes02.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.ifapps_tubes02.MainActivity;
import com.example.ifapps_tubes02.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment implements View.OnClickListener {
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
        this.binding.btnFRS.setOnClickListener(this);
        this.binding.btnPengumuman.setOnClickListener(this);
        this.binding.btnPertemuan.setOnClickListener(this);
        getParentFragmentManager().setFragmentResultListener("identifyRole", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String result = bundle.getString("role");
                String nama= bundle.getString("nama");
                // Do something with the result
                identifyRole(result, nama);
                System.out.println(result);
            }
        });


        return this.binding.getRoot();
    }

    private void identifyRole(String input, String nama) {
        this.binding.tv3.setText(input);
        this.binding.tv1.setText(nama);
    }

    @Override
    public void onClick(View view) {
        if(view== this.binding.btnFRS){
            ((MainActivity)getActivity()).changePage(5);
        }else if(view== this.binding.btnPengumuman){
            ((MainActivity)getActivity()).changePage(3);
        }else if(view== this.binding.btnPertemuan){
            ((MainActivity)getActivity()).changePage(4);
        }
    }
}
