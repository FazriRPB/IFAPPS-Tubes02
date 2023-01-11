package com.example.ifapps_tubes02.presenter.pengumuman;


import com.android.volley.VolleyError;
import com.example.ifapps_tubes02.model.Pengumuman;
import org.json.JSONException;
import java.util.ArrayList;

public interface PengumumanUI {
    interface main{
        void API(boolean isCursor, String cari, PengumumanUI.main.OnFinished onFinished, PengumumanUI.main.OnLoad onLoad);
        String successProcessing(String response, PengumumanUI.main.OnLoad onLoad) throws JSONException;
        String failedProcessing(VolleyError error) throws JSONException;
        interface OnFinished{
            void onSuccess(String hasil);
            void onFailed(String hasil);
        }
        interface OnLoad{
            void adapterSet(ArrayList<Pengumuman> arr);
            void adapterSet(String hasil);
            void disableNext();
            void enableNext();
            void setFilter(String hasil);
        }
    }
    interface presenter{
        void API(boolean cursor);
    }
    interface view{
        void showToast(String hasil);
        String getSearch();
        void disableNext();
        void enableNext();
        void adapterSet(ArrayList<Pengumuman> arr);
        void adapterSet(String hasil);
        void setFilter(String hasil);


    }
}
