package com.example.ifapps_tubes02.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.presenter.pengumuman.TambahPengumumanUI;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TambahPengumuman implements TambahPengumumanUI.main {
    Gson gson;
    FragmentActivity fragmentActivity;
    String retrivedToken;

    public TambahPengumuman(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        gson= new Gson();
        SharedPreferences preferences = fragmentActivity.getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
    }
    @Override
    public void API(String title, String content, String[] tags, TambahPengumumanUI.main.OnFinsihed onFinsihed){
        String Base_URL= "https://ifportal.labftis.net/api/v1/announcements";
        RequestQueue queue = Volley.newRequestQueue(fragmentActivity);
        JSONArray jsonArray= new JSONArray(Arrays.asList(tags));
        JSONObject js = new JSONObject();
        try {
            js.put("title", title);
            js.put("content", content);
            js.put("tags", jsonArray);

        }catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                Base_URL, js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String hasil= onSuccess(response);
//                    onFinsihed.onSuccess(hasil);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String hasil=
                    onFailed(error);
//                    onFinsihed.onSuccess(hasil);
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
    public String onSuccess(JSONObject response) throws JSONException {
        return "berhasil";
    }
    @Override
    public String onFailed(VolleyError error) throws JSONException {
        String keluaran= "";
        if(error instanceof NoConnectionError){
            keluaran= "Tidak ada koneksi internet";
        }else if(error instanceof TimeoutError){
            keluaran= "Tidak dapat terhubung dengan jaringan! \n Silahkan Coba Lagi!";
        }
        else{
            String output = new String(error.networkResponse.data);
            JSONObject jsonObject = new JSONObject(output);
            keluaran = jsonObject.toString();
        }

        return keluaran;
    }
}
