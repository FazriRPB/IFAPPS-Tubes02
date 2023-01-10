package com.example.ifapps_tubes02.presenter;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.VolleyError;
import com.example.ifapps_tubes02.R;
import com.example.ifapps_tubes02.databinding.ItemListPengumumanBinding;
import com.example.ifapps_tubes02.model.Pengumuman;
import com.example.ifapps_tubes02.model.PengumumanModel;

import org.json.JSONException;

import java.util.ArrayList;

public class PengumumanPresenter implements PengumumanUI.presenter, PengumumanUI.main.OnFinished, PengumumanUI.main.OnLoad{
    PengumumanUI.view view;
    PengumumanUI.main main;
    FragmentActivity fragmentActivity;
    public PengumumanPresenter(PengumumanUI.view view, FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
        this.view = view;
        this.main = new PengumumanModel(fragmentActivity);
    }
    @Override
    public void API(boolean cursor) {
        main.API(cursor, view.getSearch(), this, this);
    }

    @Override
    public void onSuccess(String hasil) {
        view.showToast(hasil);
    }

    @Override
    public void onFailed(String hasil) {
        view.showToast(hasil);
    }

    @Override
    public void disableNext() {
        view.disableNext();
    }

    @Override
    public void enableNext() {
        view.enableNext();
    }

    @Override
    public void setFilter(String hasil) {
        view.setFilter(hasil);
    }

    @Override
    public void adapterSet(ArrayList<Pengumuman> arr) {
        view.adapterSet(arr);
    }

    @Override
    public void adapterSet(String hasil) {
        view.adapterSet(hasil);
    }
}
