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

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.databinding.IsiPengumumanFragmentBinding;
import com.example.ifapps_tubes02.presenter.pengumuman.IsiPengumumanPresenter;
import com.example.ifapps_tubes02.presenter.pengumuman.IsiPengumumanUI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IsiPengumumanFragment extends Fragment implements IsiPengumumanUI.view, View.OnClickListener {
    IsiPengumumanFragmentBinding binding;
    String retrivedToken;
    Gson gson;
    String id;
    IsiPengumumanPresenter presenter;
    String role;

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
        role= preferences.getString("role",null);
        presenter= new IsiPengumumanPresenter(this, getActivity());
        presenter.API(id);
        binding.btnEdit.setOnClickListener(this);
        binding.btnSubmitEdit.setOnClickListener(this);
        this.binding.btnDelete.setOnClickListener(this);

        authorization(role);
        return view;
    }

    private void authorization(String role) {
        if(role.equals("student")){
            this.binding.btnEdit.setVisibility(View.GONE);
        }
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


    @Override
    public void onClick(View view) {
        if(view== this.binding.btnEdit){
            this.binding.sv2.setVisibility(View.VISIBLE);
            this.binding.etContent.setVisibility(View.VISIBLE);
            this.binding.etContent.setText(this.binding.tvContent.getText().toString());
            this.binding.tvContent.setVisibility(View.GONE);
            this.binding.sv1.setVisibility(View.GONE);
            this.binding.btnSubmitEdit.setVisibility(View.VISIBLE);
            this.binding.btnEdit.setVisibility(View.GONE);
            this.binding.btnDelete.setVisibility(View.VISIBLE);
        }
        if(this.binding.btnSubmitEdit== view){
            try {
                API();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(this.binding.btnDelete== view){
            try {
                API1();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void API() throws JSONException {
        String Base_URL= "https://ifportal.labftis.net/api/v1/announcements/"+id;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JSONObject js = new JSONObject();
        js.put("content", this.binding.etContent.getText().toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PATCH,
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
        this.binding.tvContent.setVisibility(View.VISIBLE);
        this.binding.tvContent.setText(this.binding.etContent.getText().toString());
        this.binding.etContent.setVisibility(View.GONE);
        this.binding.btnSubmitEdit.setVisibility(View.GONE);
        this.binding.sv2.setVisibility(View.GONE);
        this.binding.sv1.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(),"Pengumuman Berhasil diubah",Toast.LENGTH_LONG).show();
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
    public void API1() throws JSONException {
        String Base_URL= "https://ifportal.labftis.net/api/v1/announcements/"+id;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JSONObject js = new JSONObject();
        js.put("content", this.binding.etContent.getText().toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.DELETE,
                Base_URL, js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    onSuccess1(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    onFailed1(error);
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
    public void onSuccess1(JSONObject response) throws JSONException {
        getActivity().onBackPressed();
    }
    public void onFailed1(VolleyError error) throws JSONException {
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
}
