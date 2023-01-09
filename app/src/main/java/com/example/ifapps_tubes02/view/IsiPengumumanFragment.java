package com.example.ifapps_tubes02.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.databinding.IsiPengumumanFragmentBinding;
import com.example.ifapps_tubes02.model.Tags;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IsiPengumumanFragment extends Fragment {
    IsiPengumumanFragmentBinding binding;
    String retrivedToken;
    Gson gson;
    String id;

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
        API();
        return view;
    }
    public void API(){
        String url= "https://ifportal.labftis.net/api/v1/announcements/"+id;
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
                onFailed(error);
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
    public void onSuccess(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        Tags[] tags = gson.fromJson(jsonObject.getJSONArray("tags").toString(),Tags[].class);
        String collectTags = "";
        for(int i=0;i<tags.length;i++){
            collectTags+=tags[i].getTag()+",";
        }
        collectTags = collectTags.substring(0,collectTags.length()-1);
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
    public void onFailed(VolleyError error){
        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
    }
}