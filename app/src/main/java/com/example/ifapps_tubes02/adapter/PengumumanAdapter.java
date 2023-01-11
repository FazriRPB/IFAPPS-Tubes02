package com.example.ifapps_tubes02.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

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
import com.example.ifapps_tubes02.R;
import com.example.ifapps_tubes02.databinding.ItemListPengumumanBinding;
import com.example.ifapps_tubes02.model.Pengumuman;
import com.example.ifapps_tubes02.view.IsiPengumumanFragment;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PengumumanAdapter extends BaseAdapter {
    ArrayList<Pengumuman> arrlist;
    FragmentActivity activity;
    ItemListPengumumanBinding binding;
    String role;

    public PengumumanAdapter(FragmentActivity activity, ArrayList<Pengumuman> arrlist, String role){
        this.activity= activity;
        this.arrlist= arrlist;
        this.role= role;
    }

    public void addPengumuman(ArrayList<Pengumuman> pengumuman){
        this.arrlist= pengumuman;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrlist.size();
    }

    @Override
    public Pengumuman getItem(int i) {
        return arrlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        this.binding= ItemListPengumumanBinding.inflate(activity.getLayoutInflater());
        if(view== null){
            view = this.binding.getRoot();
            viewHolder= new ViewHolder(binding, this, i, activity);
            view.setTag(viewHolder);
        }
        String tags= "";
        for(int j= 0; j<arrlist.get(i).getTags().size(); j++){
            tags+= arrlist.get(i).getTags().get(j).getTag()+",";
        }
        updateView(i,tags );

        this.binding.layPengumuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IsiPengumumanFragment isiPengumumanFragment = new IsiPengumumanFragment();
                Bundle arguments = new Bundle();
                arguments.putString( "id" , arrlist.get(i).getId());
                isiPengumumanFragment.setArguments(arguments);
                final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, isiPengumumanFragment , "e").addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

    private void updateView(int i, String tags) {
        this.binding.tvJudul.setText(this.arrlist.get(i).getTitle());
        this.binding.tvTags.setText(tags);
    }

    public class ViewHolder implements View.OnClickListener{
        ItemListPengumumanBinding binding;
        FragmentActivity activity;
        PengumumanAdapter adapter;
        int i;
        SharedPreferences preferences;
        String retrivedToken;

        public ViewHolder(ItemListPengumumanBinding binding, PengumumanAdapter adapter, int i, FragmentActivity activity) {
            this.binding = binding;
            this.adapter = adapter;
            this.i = i;
            this.activity= activity;

            preferences = activity.getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
            retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.

//            this.binding.btnDelete.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {

        }

        private void delete(String Base_URL) {
            RequestQueue queue = Volley.newRequestQueue(activity);
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                    Base_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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
        private void onSuccess(String response) throws JSONException {
            Toast.makeText(activity,"Berhasil menghapus!",Toast.LENGTH_LONG).show();
            notifyDataSetChanged();

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

            Toast.makeText(activity,keluaran,Toast.LENGTH_LONG).show();
        }
    }
}
