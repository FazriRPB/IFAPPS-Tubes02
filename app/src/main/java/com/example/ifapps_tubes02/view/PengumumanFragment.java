package com.example.ifapps_tubes02.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.ifapps_tubes02.MainActivity;
import com.example.ifapps_tubes02.R;
import com.example.ifapps_tubes02.adapter.PengumumanAdapter;
import com.example.ifapps_tubes02.databinding.PengumumanFragmentBinding;
import com.example.ifapps_tubes02.model.Pengumuman;
import com.example.ifapps_tubes02.presenter.pengumuman.PengumumanPresenter;
import com.example.ifapps_tubes02.presenter.pengumuman.PengumumanUI;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PengumumanFragment extends Fragment implements PengumumanUI.view, View.OnClickListener{
    PengumumanFragmentBinding binding;
    PengumumanAdapter adapter;
    SharedPreferences preferences;
    ArrayList<Pengumuman> arrlist;
    ArrayList<String> tag;
    ArrayList<String> idTag;
    Gson gson;
    String retrivedToken;
    String role;
    PengumumanPresenter presenter;
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
        preferences = getActivity().getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
        arrlist= new ArrayList<>();
        tag= new ArrayList<>();
        idTag= new ArrayList<>();
        getBundle();
        gson =new Gson();
        this.adapter = new PengumumanAdapter(this.getActivity(), arrlist, this.role);
        this.binding.lvItemsPengumuman.setAdapter(adapter);
        this.presenter= new PengumumanPresenter(this, getActivity());
        this.presenter.API(false);
        methodSearch();

        this.binding.btnAdd.setOnClickListener(this);
        this.binding.btnNext.setOnClickListener(this);
        this.binding.btnFilter.setOnClickListener(this);

        return this.binding.getRoot();
    }
    private void getBundle() {
        getParentFragmentManager().setFragmentResultListener("identifyRole", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String role= result.getString("role");
                authorization(role);
            }
        });
    }

    private void methodSearch() {
        this.binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.API(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void authorization(String role) {
        if(!role.equals("student")){
            this.binding.btnAdd.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        if(view==this.binding.btnAdd){
            ((MainActivity)getActivity()).changePage(6);
        }else if(view==this.binding.btnNext){
            presenter.API(true);
        }else if(view==this.binding.btnFilter){
            TagFragment filter = new TagFragment(this, presenter);
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            filter.show(ft,"a");
        }
    }

    @Override
    public void showToast(String hasil) {
        Toast.makeText(getActivity(),hasil,Toast.LENGTH_LONG).show();
    }

    @Override
    public String getSearch() {
        return this.binding.etSearch.getText().toString();
    }

    @Override
    public void disableNext() {
        this.binding.btnNext.setVisibility(View.INVISIBLE);
    }

    @Override
    public void enableNext() {
        this.binding.btnNext.setVisibility(View.VISIBLE);

    }

    @Override
    public void adapterSet(ArrayList<Pengumuman> arr) {
        adapter.addPengumuman(arr);
        this.binding.lvItemsPengumuman.setAdapter(adapter);
    }

    @Override
    public void adapterSet(String hasil) {
        this.binding.lvItemsPengumuman.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.item_list_pengumuman,R.id.tv_judul,new String[]{hasil}));
    }

    @Override
    public void setFilter(String hasil) {
        this.binding.filterStat.setText(hasil);
    }

}