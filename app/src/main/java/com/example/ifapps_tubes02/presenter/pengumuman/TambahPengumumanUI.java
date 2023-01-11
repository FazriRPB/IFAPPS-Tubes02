package com.example.ifapps_tubes02.presenter.pengumuman;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public interface TambahPengumumanUI {
    interface main{
        void API(String title, String content, String[] tags, TambahPengumumanUI.main.OnFinsihed onFinsihed);
        String onSuccess(JSONObject response) throws JSONException;
        String onFailed(VolleyError error) throws JSONException;
        interface OnFinsihed{
            void onSuccess(String hasil);
            void onFailed(String hasil);
        }
    }
    interface presenter{
       void API(String title, String content, String[] tags);
       void takeArr(ArrayList<String> arr);
       void arrToText(ArrayList<String> arr);
    }
    interface view{
        void showToast(String hasil);
        void takeArr(ArrayList<String> arr);
        void arrToText(ArrayList<String> arr);
    }

}
