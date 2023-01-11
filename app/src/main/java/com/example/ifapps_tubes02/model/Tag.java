package com.example.ifapps_tubes02.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.presenter.pengumuman.TagUI;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tag implements TagUI.main {
    FragmentActivity fragmentActivity;
    Gson gson;
    SharedPreferences preferences;
    String retrivedToken;
    private HashMap<String, String> hashMap;
    ArrayList<String> checked;



    public Tag(FragmentActivity fragmentActivity) {
        this.fragmentActivity= fragmentActivity;
        gson= new Gson();
        preferences = fragmentActivity.getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
        getTags();
    }

    public void API(TagUI.main.OnFinished onFinished){
        String url= "https://ifportal.labftis.net/api/v1/tags";
        RequestQueue queue = Volley.newRequestQueue(fragmentActivity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    onSuccess(response, onFinished);
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
    public void onSuccess(String response, TagUI.main.OnFinished onFinished) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        onFinished.onSuccess(mapping(jsonArray, onFinished));
    }
    public void onFailed(VolleyError error){
        Toast.makeText(fragmentActivity,error.toString(),Toast.LENGTH_LONG).show();
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
    private ArrayList<String> mapping(JSONArray jsonArray, TagUI.main.OnFinished onFinished) throws JSONException {
        for(int i=0;i<jsonArray.length();i++){
            hashMap.put(jsonArray.getJSONObject(i).getString("tag"),jsonArray.getJSONObject(i).getString("id"));
            CheckBox checkBox = new CheckBox(fragmentActivity);
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
            onFinished.addView(checkBox);
        }
        return checked;
    }

}
