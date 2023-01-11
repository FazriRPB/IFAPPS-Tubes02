package com.example.ifapps_tubes02.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.R;
import com.example.ifapps_tubes02.adapter.FRSAdapter;
import com.example.ifapps_tubes02.databinding.FrsFragmentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FRSFragment extends Fragment {
    FrsFragmentBinding binding;
    FRSAdapter adapter;
    int initial_year;
    int active_year;
    ArrayList<Integer> semester;
    String token;
    String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrsFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        semester = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        this.token = sharedPreferences.getString("TOKEN",null);
        this.email= sharedPreferences.getString("email",null);
        callAPI("https://ifportal.labftis.net/api/v1/students/email/"+this.email,"cariInitialYear");
        return view;
    }
    public static FRSFragment newInstance(String title){
        FRSFragment fragment = new FRSFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);

        return fragment;
    }
    public void callAPI(String Base_URL,String ngapain){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Base_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(ngapain.equals("cariTahun")){
                        memprosesKeluaranBerhasil(response);
                    }
                    else{
                        memprosesInitialYear(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    memprosesKeluaranGagal(error);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };
        queue.add(stringRequest);
    }
    public void memprosesKeluaranBerhasil(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("academic_years");
        this.active_year = jsonObject.getInt("active_year");
        for(int i=0;i<jsonArray.length();i++){
            int masukan = jsonArray.getInt(i);
            int tahun = masukan/10;
            if(tahun<=(this.initial_year/10)+7 && tahun>=(this.initial_year/10)){
                semester.add(masukan);
            }
        }
        adapter = new FRSAdapter(semester,getActivity(),active_year);
        binding.lvItemsFrs.setAdapter(adapter);
    }
    public void memprosesKeluaranGagal(VolleyError error) throws JSONException {
        if(error instanceof NoConnectionError){
            Toast.makeText(getActivity(),"Tidak ada koneksi internet",Toast.LENGTH_LONG).show();
        }else if(error instanceof TimeoutError){
            Toast.makeText(getActivity(),"Server memakan waktu lama untuk merespon\nCoba Lagi!",Toast.LENGTH_LONG).show();
        }
        else{
            String jsonKeluaran = new String(error.networkResponse.data);
            JSONObject jsonObject = new JSONObject(jsonKeluaran);
            String keluaran = jsonObject.get("errcode").toString();

            Toast.makeText(getActivity(),keluaran,Toast.LENGTH_LONG).show();
        }
    }
    public void memprosesInitialYear(String response) throws JSONException {
        initial_year = new JSONObject(response).getInt("initial_year");
        callAPI("https://ifportal.labftis.net/api/v1/academic-years","cariTahun");
    }
}
