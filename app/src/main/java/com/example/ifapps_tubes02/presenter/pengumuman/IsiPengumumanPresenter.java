package com.example.ifapps_tubes02.presenter;

import androidx.fragment.app.FragmentActivity;

import com.example.ifapps_tubes02.model.IsiPengumuman;

import org.json.JSONException;
import org.json.JSONObject;

public class IsiPengumumanPresenter implements IsiPengumumanUI.presenter, IsiPengumumanUI.main.OnFinished{
    IsiPengumumanUI.view view;
    IsiPengumumanUI.main main;
    FragmentActivity fragmentActivity;
    public IsiPengumumanPresenter(IsiPengumumanUI.view view, FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
        this.view = view;
        this.main = new IsiPengumuman(fragmentActivity);
    }
    @Override
    public void updateView(JSONObject jsonObject, String collectTags) throws JSONException {
        view.updateView(jsonObject,collectTags);
    }

    @Override
    public void onFailed(String hasil) {
        view.showToast(hasil);
    }

    @Override
    public void API(String id) {
        main.API(id,this);
    }
}
