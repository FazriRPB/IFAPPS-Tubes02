package com.example.ifapps_tubes02.presenter;

import com.android.volley.VolleyError;
import com.example.ifapps_tubes02.model.Pertemuan;

import org.json.JSONException;

import java.util.ArrayList;

public interface PertemuanInteface {
    void API();

    void addDatas(ArrayList<Pertemuan> datas);

    void successProcessing(String response) throws JSONException;

    String failedProcessing(VolleyError error) throws JSONException;
}
