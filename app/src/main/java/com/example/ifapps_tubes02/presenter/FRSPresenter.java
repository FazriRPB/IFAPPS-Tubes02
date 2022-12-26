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
    public void initData(){
        frs= new FRS("Semester Ganjil","2021-2022");
        datas.add(frs);
        frs= new FRS("Semester Genap","2021-2022");
        datas.add(frs);
        frs= new FRS("Semester Ganjil","2022-2023");
        datas.add(frs);
        frs= new FRS("Semester Genap","2022-2023");
        datas.add(frs);
        frs= new FRS("Semester Ganjil","2023-2024");
        datas.add(frs);
        frs= new FRS("Semester Genap","2023-2024");
        datas.add(frs);

        view.addDatas(datas);
    }
}
