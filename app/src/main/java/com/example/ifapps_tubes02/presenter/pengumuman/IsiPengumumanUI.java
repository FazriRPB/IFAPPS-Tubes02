package com.example.ifapps_tubes02.presenter.pengumuman;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public interface IsiPengumumanUI {
    interface main{
        void API(String id, IsiPengumumanUI.main.OnFinished onFinished);
        void onSuccess(String response, IsiPengumumanUI.main.OnFinished onFinished) throws JSONException;
        String onFailed(VolleyError error);
        interface OnFinished{
            void updateView(JSONObject jsonObject, String collectTags) throws JSONException;
            void onFailed(String hasil);
        }
    }
    interface presenter{
        void API(String id);
    }
    interface view{
        void updateView(JSONObject jsonObject, String collectTags) throws JSONException;
        void showToast(String hasil);
    }
}
