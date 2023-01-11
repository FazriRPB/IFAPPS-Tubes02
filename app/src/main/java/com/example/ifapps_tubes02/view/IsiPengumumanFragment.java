package com.example.ifapps_tubes02.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ifapps_tubes02.databinding.IsiPengumumanFragmentBinding;
import com.example.ifapps_tubes02.presenter.pengumuman.IsiPengumumanPresenter;
import com.example.ifapps_tubes02.presenter.pengumuman.IsiPengumumanUI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class IsiPengumumanFragment extends Fragment implements IsiPengumumanUI.view {
    IsiPengumumanFragmentBinding binding;
    String retrivedToken;
    Gson gson;
    String id;
    IsiPengumumanPresenter presenter;

    public static IsiPengumumanFragment newInstance(String title){
        IsiPengumumanFragment fragment = new IsiPengumumanFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = IsiPengumumanFragmentBinding.inflate(inflater,container,false);
        View view = this.binding.getRoot();
        id = getArguments().getString("id");
        gson = new Gson();
        SharedPreferences preferences = getActivity().getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
        presenter= new IsiPengumumanPresenter(this, getActivity());
        presenter.API(id);
        return view;
    }

    @Override
    public void updateView(JSONObject jsonObject, String collectTags) throws JSONException {
        this.binding.tvTitle.setText(jsonObject.getString("title"));
        this.binding.tvContent.setText(jsonObject.getString("content"));
        this.binding.tvTags.setText(collectTags);
        String dateTime = jsonObject.getString("created_at");
        String tanggal = dateTime.substring(0,10);
        String jam = dateTime.substring(11,16);
        this.binding.tvCreatedAt1.setText(tanggal);
        this.binding.tvCreatedAt2.setText(jam);
        this.binding.tvAuthor.setText("Author: "+jsonObject.getJSONObject("author").getString("author"));
    }

    @Override
    public void showToast(String hasil) {
        Toast.makeText(getActivity(),hasil,Toast.LENGTH_LONG).show();
    }


}
