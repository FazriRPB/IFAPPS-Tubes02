package com.example.ifapps_tubes02.presenter;

import com.android.volley.VolleyError;

import org.json.JSONException;

public interface PengumumanUI {
    interface main{
        void API(String Base_URL,boolean isCursor, PengumumanUI.main.OnFinished onfFinished);
        String successProcessing(String response) throws JSONException;
        String failedProcessing(VolleyError error) throws JSONException;
        interface OnFinished{
            void authorization(String role);
            void onSuccess(String hasil);
            void onFailed(String hasil);
        }
    }
    interface presenter{
        void onClick();
    }
    interface view{
        void showToast(String hasil);
    }
}
