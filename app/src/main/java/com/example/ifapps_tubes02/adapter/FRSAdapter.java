package com.example.ifapps_tubes02.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ifapps_tubes02.databinding.ItemListFrsBinding;
import com.example.ifapps_tubes02.model.FRS;

import java.util.ArrayList;

public class FRSAdapter extends BaseAdapter {
    private Activity activity;
    protected ArrayList<FRS> arrlist;
    ItemListFrsBinding binding;

    public FRSAdapter(Activity activity){
        this.activity= activity;
        this.arrlist= new ArrayList<>();
    }
    public void initList(ArrayList<FRS> item){
        for(FRS items: item){
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view== null){
            binding= ItemListFrsBinding.inflate(this.activity.getLayoutInflater());
            view= binding.getRoot();
            viewHolder= new FRSAdapter.ViewHolder(binding, this, i);
            view.setTag(viewHolder);
        }else{
            viewHolder= (FRSAdapter.ViewHolder) view.getTag();
        }
        FRS currentFood= (FRS) this.getItem(i);
        viewHolder.updateTitle(currentFood.getSemester().toString()+" "+currentFood.getTahun());
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
