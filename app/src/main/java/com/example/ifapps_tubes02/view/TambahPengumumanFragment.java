package com.example.ifapps_tubes02.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.MainActivity;
import com.example.ifapps_tubes02.databinding.TambahPengumumanFragmentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TambahPengumumanFragment extends Fragment implements View.OnClickListener{
    TambahPengumumanFragmentBinding binding;
    String retrivedToken;
    String[] arrId;
    TagFragment filter;

    public static TambahPengumumanFragment newInstance(String title){
        TambahPengumumanFragment fragment = new TambahPengumumanFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = TambahPengumumanFragmentBinding.inflate(inflater,container,false);
        SharedPreferences preferences = getActivity().getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
        this.binding.btnSimpan.setOnClickListener(this);
        this.binding.btnTag.setOnClickListener(this);
        filter = new TagFragment(this);
        return this.binding.getRoot();
    }
    public void takeArr(ArrayList<String> arr){
        this.arrId= new String[arr.size()];
        for(int i= 0; i<arrId.length; i++){
            arrId[i]= arr.get(i);
        }
    }
    public void arrToText(ArrayList<String> arr){
        String result="";
        for(int i= 0; i<arr.size(); i++){
            if(i==arr.size()-1){
                result+= arr.get(i)+".";
            }else{
                result+= arr.get(i)+", ";
            }
        }
        this.binding.btnTag.setText(result);

    }
    public void API(String title, String content, String[] tags){
        String Base_URL= "https://ifportal.labftis.net/api/v1/announcements";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
            keluaran = jsonObject.toString();
        }

        Toast.makeText(getActivity(),keluaran,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(view==this.binding.btnSimpan){
            if(this.binding.etJudul.getText().toString().equals("") || this.binding.tvTag.getText().toString().equals("")
                    || this.binding.etDeskripsi.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Lengkapi terlebih dahulu edit box diatas", Toast.LENGTH_LONG).show();
            }else{
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Yakin dengan data yang anda buat?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                API(binding.etJudul.getText().toString(), binding.etDeskripsi.getText().toString(), arrId);
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                filter.preferences.edit().clear();
            }

        }
        if(view== this.binding.btnTag){
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            filter.show(ft,"a");
        }
    }

}
