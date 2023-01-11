package com.example.ifapps_tubes02.presenter.pengumuman;

import androidx.fragment.app.FragmentActivity;

import com.example.ifapps_tubes02.model.TambahPengumuman;

import java.util.ArrayList;

public class TambahPengumumanPresenter implements TambahPengumumanUI.presenter, TambahPengumumanUI.main.OnFinsihed{
    TambahPengumumanUI.view view;
    TambahPengumumanUI.main main;
    FragmentActivity fragmentActivity;
    public TambahPengumumanPresenter(TambahPengumumanUI.view view, FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
        this.view = view;
        this.main = new TambahPengumuman(fragmentActivity);
    }
    @Override
    public void API(String title, String content, String[] tags) {
        main.API(title,content,tags,this);
    }

    @Override
    public void takeArr(ArrayList<String> arr) {
        view.takeArr(arr);
    }

    @Override
    public void arrToText(ArrayList<String> arr) {
        view.arrToText(arr);
    }

    @Override
    public void onSuccess(String hasil) {
        view.showToast(hasil);
    }

    @Override
    public void onFailed(String hasil) {
        view.showToast(hasil);
    }
}
