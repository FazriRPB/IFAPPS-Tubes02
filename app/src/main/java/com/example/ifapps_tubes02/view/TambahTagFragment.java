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
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.databinding.TambahtagFragmentBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahTagFragment extends DialogFragment implements View.OnClickListener{
    TambahtagFragmentBinding binding;
    String retrivedToken;
    TambahPengumumanFragment tambahTagFragment;

    public TambahTagFragment(TambahPengumumanFragment activity) {
        this.tambahTagFragment= activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= TambahtagFragmentBinding.inflate(getLayoutInflater());
        SharedPreferences preferences = getActivity().getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.

        this.binding.btnBuat.setOnClickListener(this);
        return binding.getRoot();
    }

    public void API() throws JSONException {
        String Base_URL= "https://ifportal.labftis.net/api/v1/tags";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JSONObject js = new JSONObject();
        js.put("tag", this.binding.etTag.getText().toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                Base_URL, js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    onFailed(error);
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
    public void onSuccess(JSONObject response) throws JSONException {
        Toast.makeText(getActivity(),"Tag Berhasil dibuat",Toast.LENGTH_LONG).show();
    }
    public void onFailed(VolleyError error) throws JSONException {
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

        Toast.makeText(getActivity(),keluaran,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(this.binding.btnBuat== view){
            try {
                API();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
