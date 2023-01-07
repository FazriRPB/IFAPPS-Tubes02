package com.example.ifapps_tubes02.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.ifapps_tubes02.MainActivity;
import com.example.ifapps_tubes02.adapter.PengumumanAdapter;
import com.example.ifapps_tubes02.databinding.PengumumanFragmentBinding;
import com.example.ifapps_tubes02.model.Pengumuman;

public class PengumumanFragment extends Fragment implements View.OnClickListener{
    PengumumanFragmentBinding binding;
    PengumumanAdapter adapter;

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

        this.adapter = new PengumumanAdapter(this.getActivity());
        this.binding.lvItemsPengumuman.setAdapter(adapter);

        this.binding.btnAdd.setOnClickListener(this);

        this.binding.lvItemsPengumuman.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position==0){
                    ((MainActivity)getActivity()).changePage(7);
                }else if(position==1){
                    ((MainActivity)getActivity()).changePage(7);
                }else if(position==2){
                    ((MainActivity)getActivity()).changePage(7);
                }else{

                }
            }
        });

        getParentFragmentManager().setFragmentResultListener("pengumuman", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Pengumuman pengumuman = new Pengumuman(result.getString("judul"),result.getString("tag"),result.getString("deskripsi"),false);
                adapter.addPengumuman(pengumuman);
            }
        });

        return this.binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view==this.binding.btnAdd){
            ((MainActivity)getActivity()).changePage(6);
        }
    }
}
