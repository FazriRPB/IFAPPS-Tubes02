package com.example.ifapps_tubes02.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.presenter.pengumuman.IsiPengumumanUI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IsiPengumuman implements IsiPengumumanUI.main {
    String retrivedToken;
    Gson gson;
    FragmentActivity fragmentActivity;

    public IsiPengumuman(FragmentActivity fragmentActivity) {
        SharedPreferences preferences = fragmentActivity.getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
        this.gson = new Gson();
        this.fragmentActivity = fragmentActivity;
    }

    public void API(String id, IsiPengumumanUI.main.OnFinished onFinished){
        String url= "https://ifportal.labftis.net/api/v1/announcements/"+id;
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
                String hasil= onFailed(error);
                onFinished.onFailed(hasil);
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
    @Override
    public void onSuccess(String response, IsiPengumumanUI.main.OnFinished onFinished) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        Tags[] tags = gson.fromJson(jsonObject.getJSONArray("tags").toString(),Tags[].class);
        String collectTags = "";
        for(int i=0;i<tags.length;i++){
            collectTags+=tags[i].getTag()+",";
        }
        onFinished.updateView(jsonObject, collectTags);
    }
    @Override
    public String onFailed(VolleyError error){
        return error.toString();
    }
}
