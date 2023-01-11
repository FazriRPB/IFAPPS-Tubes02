package com.example.ifapps_tubes02.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.R;
import com.example.ifapps_tubes02.databinding.TagsFragmentBinding;
import com.example.ifapps_tubes02.presenter.pengumuman.PengumumanPresenter;
import com.example.ifapps_tubes02.presenter.pengumuman.TambahPengumumanPresenter;
import com.example.ifapps_tubes02.presenter.pengumuman.TambahPengumumanUI;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TagFragment extends DialogFragment implements View.OnClickListener {
    TagsFragmentBinding binding;
    ArrayList<String> checked;
    SharedPreferences preferences;
    String retrivedToken;
    HashMap<String,String> hashMap;
    PengumumanFragment pengumumanFragment;
    TambahPengumumanFragment tambahPengumumanFragment;
    ArrayList<String> arr;
    ArrayList<String> arrId;
    PengumumanPresenter presenter;
    TambahPengumumanPresenter presenter1;
    public TagFragment(PengumumanFragment pengumumanFragment, PengumumanPresenter presenter){
        this.pengumumanFragment = pengumumanFragment;
        this.presenter= presenter;
    }
    public TagFragment(TambahPengumumanFragment pengumumanFragment, TambahPengumumanPresenter presenter1){
        this.tambahPengumumanFragment = pengumumanFragment;
        this.presenter1= presenter1;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = TagsFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        preferences = getActivity().getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
        this.binding.btnOk.setOnClickListener(this);
        getTags();
        API("https://ifportal.labftis.net/api/v1/tags");
        return view;
    }

    private void getTags() {
        hashMap = new HashMap<>();
        if(preferences.getString("tag","").equals("")){
            checked = new ArrayList<>();
        }
        else{
            checked = new ArrayList<>(Arrays.asList(preferences.getString("tag","").split(",")));
        }
    }

    public void API(String Base_URL){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Base_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onFailed(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer "+retrivedToken);
                return map;
            }
        };
        queue.add(stringRequest);
    }
    public void onSuccess(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        mapping(jsonArray);
    }

    private void mapping(JSONArray jsonArray) throws JSONException {
        for(int i=0;i<jsonArray.length();i++){
            hashMap.put(jsonArray.getJSONObject(i).getString("tag"),jsonArray.getJSONObject(i).getString("id"));
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(jsonArray.getJSONObject(i).getString("tag"));
            if(checked.contains(jsonArray.getJSONObject(i).getString("tag"))){
                checkBox.setChecked(true);
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        checked.add(buttonView.getText().toString());
                    }
                    else{
                        checked.remove(buttonView.getText().toString());
                    }
                }
            });
            Drawable d= getResources().getDrawable(R.drawable.boxed_edit_text);
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(500, 100));
            checkBox.setBackground(d);
            this.binding.containerFilter.addView(checkBox);
        }
    }

    public void onFailed(VolleyError error){
        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(this.binding.btnOk==view){
            arrId= new ArrayList<>();
            arr= new ArrayList<>();
            SharedPreferences.Editor editor = preferences.edit();
            String id="";
            for(int i=0;i<checked.size();i++){
                id+=hashMap.get(checked.get(i))+",";
                arrId.add(hashMap.get(checked.get(i)));
                arr.add(checked.get(i));
            }
            if(id.length()!=0){
                editor.putString("idTag",id.substring(0,id.length()-1));
            }
            else{
                editor.putString("idTag","");
            }

            String tag = "";
            for(int i=0;i<checked.size();i++){
                tag+=checked.get(i)+",";
            }
            if(tag.length()!=0){
                editor.putString("tag",tag.substring(0,tag.length()-1));
            }
            else{
                editor.putString("tag","");
            }

            editor.apply();
            this.dismiss();
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(presenter1== null){
            presenter.API(false);
        }
        if(presenter== null && arrId!= null){
            presenter1.takeArr(arrId);
            presenter1.arrToText(arr);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter== null){
            preferences.edit().remove("tag").apply();
            preferences.edit().remove("idTag").apply();
        }
    }
}