package com.example.ifapps_tubes02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ifapps_tubes02.databinding.PertemuanFragmentBinding;
import com.example.ifapps_tubes02.model.Pertemuan;

import java.util.ArrayList;

public class Pertemuanfragment extends Fragment implements PertemuanInteface{
    PertemuanFragmentBinding binding;
    protected PertemuanAdapter adapter;
    protected PertemuanPresenter presenter;

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
        this.adapter= new PertemuanAdapter(this.getActivity());
        this.presenter= new PertemuanPresenter(this);
        this.binding.lvItemsPertemuan.setAdapter(this.adapter);

        this.presenter.initData();

        return this.binding.getRoot();
    }

    @Override
    public void addDatas(ArrayList<Pertemuan> datas) {
        this.adapter.initList(datas);
        this.adapter.notifyDataSetChanged();
    }
}
