package com.example.ifapps_tubes02.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.ifapps_tubes02.adapter.PengumumanAdapter;
import com.example.ifapps_tubes02.databinding.PengumumanFragmentBinding;
import com.example.ifapps_tubes02.model.Pengumuman;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PengumumanFragment extends Fragment implements View.OnClickListener{
    PengumumanFragmentBinding binding;
    PengumumanAdapter adapter;
    String next;
    SharedPreferences preferences;
    ArrayList<Pengumuman> arrlist;
    ArrayList<String> tag;
    ArrayList<String> idTag;
    Gson gson;
    String retrivedToken;
    String role;
    public static PengumumanFragment newInstance(String title){
        PengumumanFragment fragment = new PengumumanFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = PengumumanFragmentBinding.inflate(inflater,container,false);
        preferences = getActivity().getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
        arrlist= new ArrayList<>();
        tag= new ArrayList<>();
        idTag= new ArrayList<>();
        getBundle();
        gson =new Gson();
        this.adapter = new PengumumanAdapter(this.getActivity(), arrlist, this.role);
        this.binding.lvItemsPengumuman.setAdapter(adapter);
        API(false);
        retriveTags();
        methodSearch();

        this.binding.btnAdd.setOnClickListener(this);
        this.binding.btnNext.setOnClickListener(this);
        this.binding.btnFilter.setOnClickListener(this);
        return this.binding.getRoot();
    }
    private void getBundle() {
        getParentFragmentManager().setFragmentResultListener("identifyRole", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String role= result.getString("role");
                authorization(role);
            }
        });
    }

    private void methodSearch() {
        this.binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                API(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void API(boolean isCursor){
        String url= "https://ifportal.labftis.net/api/v1/announcements";
        if(!isCursor){
            url+="?filter[title]="+this.binding.etSearch.getText().toString();
            if(idTag.size()!=0){
                for(int i=0;i<idTag.size();i++){
                    url+="&filter[tags][]="+idTag.get(i);
                }
            }
        }else{
            url= "https://ifportal.labftis.net/api/v1/announcements?cursor="+next;
        }
        this.binding.lvItemsPengumuman.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_list_pengumuman,R.id.tv_judul,new String[]{""}));
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
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
    public void retriveTags(){
        if(preferences.getString("tag","").equals("")){
            tag.clear();
            this.binding.filterStat.setText("Filter : -");
        }
        else{
            tag = new ArrayList<>(Arrays.asList(preferences.getString("tag","").split(",")));
            this.binding.filterStat.setText("Filter:"+preferences.getString("tag",""));
        }
        if(preferences.getString("idTag","").equals("")){
            idTag.clear();
        }else{
            idTag = new ArrayList<>(Arrays.asList(preferences.getString("idTag","").split(",")));
        }
    }

    private void onSuccess(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        Object object = jsonObject.getJSONObject("metadata").get("next");
        if(object.equals(null)){
            this.binding.btnNext.setVisibility(View.INVISIBLE);
        }
        else{
            this.binding.btnNext.setVisibility(View.VISIBLE);
            next = object.toString();
        }
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        arrlist = gson.fromJson(jsonArray.toString(),new TypeToken<ArrayList<Pengumuman>>(){}.getType());
        if(arrlist.size()==0){
            this.binding.lvItemsPengumuman.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.item_list_pengumuman,R.id.tv_judul,new String[]{"Hasil tidak ditemukan!"}));
        }
        else{
            adapter.addPengumuman(arrlist);
            this.binding.lvItemsPengumuman.setAdapter(adapter);
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

    private void authorization(String role) {
        this.role= role;
        if(!role.equals("student")){
            this.binding.btnAdd.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        if(view==this.binding.btnAdd){
            ((MainActivity)getActivity()).changePage(6);
        }else if(view==this.binding.btnNext){
            API(true);
        }else if(view==this.binding.btnFilter){
            TagFragment filter = new TagFragment(this);
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            filter.show(ft,"a");
        }
    }
}