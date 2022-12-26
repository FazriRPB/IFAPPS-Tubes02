package com.example.ifapps_tubes02.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ifapps_tubes02.databinding.DrawerFragmentBinding;

public class DrawerFragment extends Fragment implements View.OnClickListener{
    DrawerFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding= DrawerFragmentBinding.inflate(getLayoutInflater());

        this.binding.tvHome.setOnClickListener(this);
        this.binding.tvPengumuman.setOnClickListener(this);
        this.binding.tvPertemuan.setOnClickListener(this);
        this.binding.tvFrs.setOnClickListener(this);

        return this.binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view==this.binding.tvHome){
            Bundle result = new Bundle();
            result.putInt("page",2);
            this.getParentFragmentManager().setFragmentResult("changePage",result);
        }else if(view==this.binding.tvPengumuman){
            Bundle result = new Bundle();
            result.putInt("page",3);
            this.getParentFragmentManager().setFragmentResult("changePage",result);
        }else if(view==this.binding.tvPertemuan){
            Bundle result = new Bundle();
            result.putInt("page",4);
            this.getParentFragmentManager().setFragmentResult("changePage",result);
        }else if(view==this.binding.tvFrs) {
            Bundle result = new Bundle();
            result.putInt("page",5);
            this.getParentFragmentManager().setFragmentResult("changePage",result);
        }
    }
}
