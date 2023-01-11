package com.example.ifapps_tubes02.presenter.pengumuman;

import android.widget.CheckBox;

import androidx.fragment.app.FragmentActivity;

import com.example.ifapps_tubes02.model.Tag;
import com.example.ifapps_tubes02.presenter.pengumuman.TagUI;

import java.util.ArrayList;

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

    @Override
    public void onSuccess(ArrayList<String> map) {
        view.mapping(map);
    }
}
