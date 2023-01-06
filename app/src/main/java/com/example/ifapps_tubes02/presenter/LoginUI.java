package com.example.ifapps_tubes02.presenter;

import com.android.volley.VolleyError;

import org.json.JSONException;

public interface LoginUI {
    interface main{
        void API(String[] inputArray, LoginUI.main.OnFinished onfFinished);
        String successProcessing(String response);
        String failedProcessing(VolleyError error) throws JSONException;
        interface OnFinished{
            void onSuccess(String hasil);
            void onFailed(String hasil);
        }
    }
    interface presenter{
        void onClick();
    }
    interface view{
        String[] getInput();
        void disabledInput();
        void enabledInput();
        void showToast(String hasil);
    }
}
