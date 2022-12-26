package com.example.ifapps_tubes02.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ifapps_tubes02.adapter.FRSAdapter;
import com.example.ifapps_tubes02.databinding.FrsFragmentBinding;
import com.example.ifapps_tubes02.model.FRS;
import com.example.ifapps_tubes02.presenter.FRSInterface;
import com.example.ifapps_tubes02.presenter.FRSPresenter;

import java.util.ArrayList;

public class FRSFragment extends Fragment implements FRSInterface {
    FrsFragmentBinding binding;
    protected FRSAdapter adapter;
    protected FRSPresenter presenter;



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
        this.adapter= new FRSAdapter(this.getActivity());
        this.presenter= new FRSPresenter(this);
        this.binding.lvItemsFrs.setAdapter(this.adapter);

        this.presenter.initData();

        return this.binding.getRoot();
    }

    @Override
    public void addDatas(ArrayList<FRS> datas) {
        this.adapter.initList(datas);
        this.adapter.notifyDataSetChanged();
    }
}
