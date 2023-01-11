package com.example.ifapps_tubes02.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.ifapps_tubes02.adapter.PertemuanAdapter;
import com.example.ifapps_tubes02.model.Pertemuan;
import com.example.ifapps_tubes02.presenter.PertemuanInteface;
import com.example.ifapps_tubes02.databinding.PertemuanFragmentBinding;
import com.example.ifapps_tubes02.presenter.PertemuanPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pertemuanfragment extends Fragment implements PertemuanInteface,View.OnClickListener {
    PertemuanFragmentBinding binding;
    protected PertemuanAdapter adapter;
    protected PertemuanPresenter presenter;
    SharedPreferences preferences;
    String retrivedToken;
    String id;

    public static Pertemuanfragment newInstance(String title){
        Pertemuanfragment fragment = new Pertemuanfragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);


        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = PertemuanFragmentBinding.inflate(inflater,container,false);
        this.adapter= new PertemuanAdapter(this.getActivity());
        this.presenter= new PertemuanPresenter(this);
        this.binding.lvItemsPertemuan.setAdapter(this.adapter);

        this.presenter.initData();
        this.binding.btnAdd.setOnClickListener(this);

        return this.binding.getRoot();
    }
    @Override
    public void API() {
        String url= "https://ifportal.labftis.net/api/v1/appointments/users" + id;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    successProcessing(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String hasil= failedProcessing(error);
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
    public void addDatas(ArrayList<Pertemuan> datas) {

    }

    @Override
    public void successProcessing(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        String id, title, started_datetime, end_datetime;

        for(int i=0;i<jsonArray.length();i++){
            id = jsonArray.getJSONObject(i).getString("id");
            title = jsonArray.getJSONObject(i).getString("title");
            started_datetime = jsonArray.getJSONObject(i).getString("started_datetime");
            end_datetime = jsonArray.getJSONObject(i).getString("end_datetime");
        }
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
    private void getBundle() {
        getParentFragmentManager().setFragmentResultListener("identifyRole", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                id = result.getString("id");
            }
        });
    }

   /* @Override
    public void addDatas(String id, String title, String started_datetime, String end_datetime) {
        this.adapter.initList(datas);
        this.adapter.notifyDataSetChanged();
    }*/

    @Override
    public void onClick(View view) {
        if(view==this.binding.btnAdd) {
            ((MainActivity) getActivity()).changePage(7);
        }
    }
}
