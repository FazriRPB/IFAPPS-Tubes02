package com.example.ifapps_tubes02.adapter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.FragmentActivity;

import com.example.ifapps_tubes02.databinding.ItemListFrsBinding;
import com.example.ifapps_tubes02.model.FRS;

import java.util.ArrayList;
import java.util.HashMap;

public class FRSAdapter extends BaseAdapter {
    private FragmentActivity activity;
    ArrayList<Integer> arrlist;
    ItemListFrsBinding binding;
    int activeyear;
    HashMap<Integer,String> map;

    public FRSAdapter(ArrayList arrlist,FragmentActivity activity,int activeyear){
        this.activity= activity;
        this.arrlist= arrlist;
        this.activeyear = activeyear;
    }
    public void initList(ArrayList<Integer> item){
        for(Integer items: item){
            this.arrlist.add(items);
        }
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return this.arrlist.size();

    }

    @Override
    public Object getItem(int i) {
        return this.arrlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        binding = ItemListFrsBinding.inflate(activity.getLayoutInflater());
        View view = binding.getRoot();
        map = new HashMap<>();
        map.put(1, "Semester Ganjil");
        map.put(2, "Semester Genap");
        map.put(3, "Semester Pendek");
        int tahun = arrlist.get(position) / 10;
        String semester = map.get(arrlist.get(position) % 10);
        binding.semester.setText(semester + " " + tahun + "/" + (tahun + 1));
        if (arrlist.get(position) == activeyear) {
            binding.semester.setTypeface(binding.semester.getTypeface(), Typeface.BOLD_ITALIC);
            binding.containerSemester.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle result = new Bundle();
                    result.putString("page", "tambahMatkul");
                    result.putInt("tahundansem", arrlist.get(position));
                    result.putString("heading", semester + " " + tahun + "/" + (tahun + 1));
                    activity.getSupportFragmentManager().setFragmentResult("changePage", result);
                }
            });
        } else {
            binding.containerSemester.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle result = new Bundle();
                    result.putString("page", "detailSemester");
                    result.putInt("tahundansem", arrlist.get(position));
                    result.putString("heading", semester + " " + tahun + "/" + (tahun + 1));
                    activity.getSupportFragmentManager().setFragmentResult("changePage", result);
                }
            });
        }
        return view;
    }

    public class ViewHolder implements View.OnClickListener {
        //untuk akses per-item/row di listview
        ItemListFrsBinding binding;
        FRSAdapter adapter;
        int i;
        public ViewHolder(ItemListFrsBinding binding, FRSAdapter adapter, int i){
            this.binding= binding;
            this.adapter= adapter;
            this.i= i;

        }
        public void updateTitle(String title){
            this.binding.semester.setText(title);
        }
        @Override
        public void onClick(View v) {

        }


    }
}
