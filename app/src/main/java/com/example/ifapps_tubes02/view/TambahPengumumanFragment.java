package com.example.ifapps_tubes02.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ifapps_tubes02.MainActivity;
import com.example.ifapps_tubes02.databinding.PengumumanFragmentBinding;
import com.example.ifapps_tubes02.databinding.TambahPengumumanFragmentBinding;

public class TambahPengumumanFragment extends Fragment implements View.OnClickListener{
    TambahPengumumanFragmentBinding binding;

    public static TambahPengumumanFragment newInstance(String title){
        TambahPengumumanFragment fragment = new TambahPengumumanFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = TambahPengumumanFragmentBinding.inflate(inflater,container,false);

        this.binding.btnSimpan.setOnClickListener(this);

        return this.binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view==this.binding.btnSimpan){
            Bundle result = new Bundle();
            result.putString("judul",binding.etJudul.getText().toString());
            result.putString("tag",binding.etTag.getText().toString());
            result.putString("deskripsi",binding.etDeskripsi.getText().toString());
            this.getParentFragmentManager().setFragmentResult("pengumuman",result);

            ((MainActivity)getActivity()).changePage(3);
        }
    }
}
