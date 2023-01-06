package com.example.ifapps_tubes02.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.MainActivity;
import com.example.ifapps_tubes02.databinding.HomeFragmentBinding;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements View.OnClickListener {
    HomeFragmentBinding binding;
    String retrivedToken;
    String role;

    public static HomeFragment newInstance(String title){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = HomeFragmentBinding.inflate(inflater,container,false);
        this.binding.btnFRS.setOnClickListener(this);
        this.binding.btnPengumuman.setOnClickListener(this);
        this.binding.btnPertemuan.setOnClickListener(this);

        SharedPreferences preferences = getActivity().getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
        role  = preferences.getString("role",null);//second parameter default value.
        System.out.println(role);
        identifyRole(role);
        callAPI("https://ifportal.labftis.net/api/v1/users/self");

        return this.binding.getRoot();
    }

    private void identifyRole(String input) {
        this.binding.tv3.setText(input);
    }
    public void callAPI(String Base_URL){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Base_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    onSucces(response);
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
    public void onSucces(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        Object object = jsonObject.getString("name");
        JSONArray object1= jsonObject.getJSONArray("roles");
        if(!object.equals(null)){
            this.binding.tv1.setText(object.toString());
            this.binding.tv3.setText(object1.get(0).toString());
        }
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
        if(view== this.binding.btnFRS){
            ((MainActivity)getActivity()).changePage(5);
        }else if(view== this.binding.btnPengumuman){
            ((MainActivity)getActivity()).changePage(3);
        }else if(view== this.binding.btnPertemuan){
            ((MainActivity)getActivity()).changePage(4);
        }
    }
}
