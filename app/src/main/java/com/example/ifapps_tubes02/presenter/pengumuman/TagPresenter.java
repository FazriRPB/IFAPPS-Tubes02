package com.example.ifapps_tubes02.presenter;

import android.widget.CheckBox;

import androidx.fragment.app.FragmentActivity;

import com.example.ifapps_tubes02.model.PengumumanModel;
import com.example.ifapps_tubes02.model.Tag;

public class TagPresenter implements TagUI.presenter, TagUI.main.OnFinished{
    TagUI.view view;
    TagUI.main main;
    FragmentActivity fragmentActivity;
    public TagPresenter(TagUI.view view, FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
        this.view = view;
        this.main = new Tag(fragmentActivity);
    }
    @Override
    public void API() {
        main.API(this);
    }


    @Override
    public void addView(CheckBox checkBox) {
        view.addView(checkBox);
    }
}
