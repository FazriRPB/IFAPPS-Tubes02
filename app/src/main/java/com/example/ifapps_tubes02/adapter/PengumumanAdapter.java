package com.example.ifapps_tubes02.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ifapps_tubes02.databinding.ItemListPengumumanBinding;
import com.example.ifapps_tubes02.model.Pengumuman;

import java.util.ArrayList;

public class PengumumanAdapter extends BaseAdapter {
    private Activity activity;
    protected ArrayList<Pengumuman> arrlist;
    ItemListPengumumanBinding binding;

    public PengumumanAdapter(Activity activity){
        this.activity = activity;
        this.arrlist = new ArrayList<>();
        this.arrlist.add(new Pengumuman("1","tag 1","1",false));
        this.arrlist.add(new Pengumuman("2","tag 2","1",false));
        this.arrlist.add(new Pengumuman("3","tag 3","1",false));
        this.arrlist.add(new Pengumuman("4","tag 4","1",false));
        this.arrlist.add(new Pengumuman("5","tag 5","1",false));
    }

    public void addPengumuman(Pengumuman pengumuman){
        this.arrlist.add(pengumuman);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrlist.size();
    }

    @Override
    public Pengumuman getItem(int i) {
        return arrlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view== null){
            binding= ItemListPengumumanBinding.inflate(LayoutInflater.from(parent.getContext()));
            viewHolder= new ViewHolder();
            view= binding.getRoot();
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.updateView(getItem(i));

        return view;
    }

    public class ViewHolder implements View.OnClickListener{

        public void updateView(Pengumuman pengumuman){
            binding.tvJudul.setText(pengumuman.getJudul());
            binding.tvDesignasi.setText(pengumuman.gettag());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
