package com.example.ifapps_tubes02.presenter;

import com.example.ifapps_tubes02.model.FRS;
import com.example.ifapps_tubes02.view.FRSFragment;

import java.util.ArrayList;

public class FRSPresenter {
    private FRSFragment view;
    FRS frs;
    ArrayList<FRS> datas;

    public FRSPresenter(FRSFragment view) {
        this.view = view;
        this.datas= new ArrayList<>();
    }
}
