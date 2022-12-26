package com.example.ifapps_tubes02.presenter;

import com.example.ifapps_tubes02.view.Pertemuanfragment;
import com.example.ifapps_tubes02.model.Pertemuan;

import java.util.ArrayList;

public class PertemuanPresenter {

    private Pertemuanfragment view;
    Pertemuan pertemuan;
    ArrayList<Pertemuan> datas= new ArrayList<Pertemuan>();

    public PertemuanPresenter(Pertemuanfragment view) {
        this.view= view;
    }

    //logic pertemuan(contoh : ambil inisialisasi)
    public void initData(){
        pertemuan= new Pertemuan("Pembahasan Tubes1", "Fazri", "memenuhi");
        datas.add(pertemuan);
        pertemuan= new Pertemuan("Pembahasan Tubes2", "Fazri", "memenuhi");
        datas.add(pertemuan);
        pertemuan= new Pertemuan("Pembahasan Tubes3", "Fazri", "memenuhi");
        datas.add(pertemuan);
        pertemuan= new Pertemuan("Pembahasan Tubes4", "Fazri", "memenuhi");
        datas.add(pertemuan);

        view.addDatas(datas);
    }
}
