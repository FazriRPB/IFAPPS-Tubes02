package com.example.ifapps_tubes02.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.presenter.pengumuman.PengumumanUI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PengumumanModel implements PengumumanUI.main {
    private final FragmentActivity fragmentActivity;
    String next;
    ArrayList<Pengumuman> arrlist;
    ArrayList<String> idTag;
    Gson gson;
    String retrivedToken;
    private SharedPreferences preferences;
    ArrayList<String> tag;

    public PengumumanModel(FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
        gson = new Gson();
        tag= new ArrayList<>();
        idTag= new ArrayList<>();
        preferences = fragmentActivity.getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
    }
    @Override
    public void API(boolean isCursor, String cari, PengumumanUI.main.OnFinished onFinished, PengumumanUI.main.OnLoad onLoad) {
        retriveTags(onLoad);
        String url= "https://ifportal.labftis.net/api/v1/announcements";
        if(!isCursor){
            cari= "="+cari;
            url+="?filter[title]"+cari;
            if(idTag.size()!=0){
                for(int i=0;i<idTag.size();i++){
                    url+="&filter[tags][]="+idTag.get(i);
                }
            }
            url+= "&limit=10";
        }else{
            url= "https://ifportal.labftis.net/api/v1/announcements?cursor="+next+"&limit=10";
        }
        onLoad.adapterSet("");
        RequestQueue queue = Volley.newRequestQueue(fragmentActivity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    successProcessing(response, onLoad);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String hasil= failedProcessing(error);
                    onFinished.onFailed(hasil);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    public String successProcessing(String response, PengumumanUI.main.OnLoad onLoad) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        Object object = jsonObject.getJSONObject("metadata").get("next");
        if(object.equals(null)){
            onLoad.disableNext();
        }
        else{
            onLoad.enableNext();
            next = object.toString();
        }
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        arrlist = gson.fromJson(jsonArray.toString(),new TypeToken<ArrayList<Pengumuman>>(){}.getType());
        if(arrlist.size()==0){
            onLoad.adapterSet("Hasil tidak ditemukam!");
        }
        else{
            onLoad.adapterSet(arrlist);
        }
        return "data berhasil diload";
    }

    @Override
    public String failedProcessing(VolleyError error) throws JSONException {
        String keluaran= "";
        if(error instanceof NoConnectionError){
            keluaran= "Tidak ada koneksi internet";
        }else if(error instanceof TimeoutError){
            keluaran= "Tidak dapat terhubung dengan jaringan! \n Silahkan Coba Lagi!";
        }
        else{
            String output = new String(error.networkResponse.data);
            JSONObject jsonObject = new JSONObject(output);
            keluaran = jsonObject.get("errcode").toString();
        }
        return keluaran;
    }
    public void retriveTags(PengumumanUI.main.OnLoad onLoad){
        if(this.preferences.getString("tag","").equals("")){
            tag.clear();
            onLoad.setFilter("Filter : -");
        }
        else{
            tag = new ArrayList<>(Arrays.asList(preferences.getString("tag","").split(",")));
            onLoad.setFilter("Filter:"+preferences.getString("tag",""));
        }
        if(preferences.getString("idTag","").equals("")){
            idTag.clear();
        }else{
            idTag = new ArrayList<>(Arrays.asList(preferences.getString("idTag","").split(",")));
        }
    }
}
