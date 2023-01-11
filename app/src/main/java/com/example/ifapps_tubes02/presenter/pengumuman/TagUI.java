package com.example.ifapps_tubes02.presenter.pengumuman;

import android.widget.CheckBox;

import java.util.ArrayList;

public interface TagUI {
    interface main{
        void API(TagUI.main.OnFinished onFinished);
        interface OnFinished{
            void addView(CheckBox checkBox);
            void onSuccess(ArrayList<String> map);
        }
    }
    interface presenter{
        void API();
    }
    interface view{
        void addView(CheckBox checkBox);
        void mapping(ArrayList<String> map);
    }
}
