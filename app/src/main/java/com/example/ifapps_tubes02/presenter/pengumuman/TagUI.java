package com.example.ifapps_tubes02.presenter;

import android.widget.CheckBox;

public interface TagUI {
    interface main{
        void API(TagUI.main.OnFinished onFinished);
        interface OnFinished{
            void addView(CheckBox checkBox);
        }
    }
    interface presenter{
        void API();
    }
    interface view{
        void addView(CheckBox checkBox);
    }
}
