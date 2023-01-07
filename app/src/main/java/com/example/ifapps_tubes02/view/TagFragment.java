package com.example.ifapps_tubes02.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.example.ifapps_tubes02.databinding.FilterFragmentBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TagFragment extends DialogFragment implements View.OnClickListener {
    FilterFragmentBinding binding;
    ArrayList<String> checked;
    SharedPreferences preferences;
    String retrivedToken;
    HashMap<String,String> hashMap;
    PengumumanFragment pengumumanFragment;
    TambahPengumumanFragment tambahPengumumanFragment;
    ArrayList<String> arr;
    ArrayList<String> arrId;
    public TagFragment(PengumumanFragment pengumumanFragment){
        this.pengumumanFragment = pengumumanFragment;
    }
    public TagFragment(TambahPengumumanFragment pengumumanFragment){
        this.tambahPengumumanFragment = pengumumanFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FilterFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        preferences = getActivity().getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
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
            this.binding.containerFilter.addView(checkBox);
        }
        Button button = new Button(getActivity());
        button.setText("Apply");
        button.setOnClickListener(this);
        this.binding.containerFilter.addView(button);

    }
    public void onFailed(VolleyError error){
        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(tambahPengumumanFragment== null){
            pengumumanFragment.retriveTags();
            pengumumanFragment.API("https://ifportal.labftis.net/api/v1/announcements",false);
        }
        if(pengumumanFragment== null && arrId!= null){
            tambahPengumumanFragment.takeArr(arrId);
            tambahPengumumanFragment.arrToText(arr);
        }
    }
}
