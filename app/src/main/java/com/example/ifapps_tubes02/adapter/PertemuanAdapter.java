package com.example.ifapps_tubes02.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ifapps_tubes02.databinding.ItemListPertemuanBinding;
import com.example.ifapps_tubes02.model.Pertemuan;
import com.example.ifapps_tubes02.presenter.PertemuanPresenter;

import java.util.ArrayList;

public class PertemuanAdapter extends BaseAdapter {
    private Activity activity;
    protected ArrayList<Pertemuan> arrlist;
    ItemListPertemuanBinding binding;

    public PertemuanAdapter(Activity activity) {
        this.activity = activity;
        this.arrlist = new ArrayList<>();
    }

    public void initList(String id, String title, String started_datetime, String end_datetime) {
        Pertemuan pertemuan = new Pertemuan(id, title, started_datetime, end_datetime);
        arrlist.add(pertemuan);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.arrlist.size();

    }

    @Override
    public Pertemuan getItem(int i) {
        return this.arrlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        /* ViewHolder viewHolder;*/

        if (view == null) {
            binding = ItemListPertemuanBinding.inflate(this.activity.getLayoutInflater());
            view = binding.getRoot();
            /*viewHolder= new ViewHolder(binding, this, i);
            view.setTag(viewHolder);*/
        } else {
            this.notifyDataSetChanged();
        }
        this.binding.title.setText(arrlist.get(i).gettitle());
        this.binding.startedDatetime.setText(arrlist.get(i).getStart_datetime());
        this.binding.endDatetime.setText(arrlist.get(i).getEnd_datetime());
        return view;
    }

    public void initList(ArrayList<Pertemuan> datas) {
    }
}

    /*public class ViewHolder implements View.OnClickListener {
        //untuk akses per-item/row di listview
        ItemListPertemuanBinding binding;
        PertemuanAdapter adapter;
        PertemuanPresenter presenter;
        int i;
        public ViewHolder(ItemListPertemuanBinding binding, PertemuanAdapter adapter, int i){
            this.binding= binding;
            this.adapter= adapter;
            this.i= i;

        }
        public void updateTitle(String title){
            this.binding.title.setText(title);
        }
        public void updateDetail(String detail){
            this.binding.owner.setText(detail);
        }
        public void dateDetail(String detail){
            this.binding.date.setText(detail);
        }
        @Override
        public void onClick(View v) {
            if(v== this.binding.button){
//                Toast toast= Toast.makeText(activity.getApplicationContext(), a, Toast.LENGTH_SHORT);
//                toast.show();
            }
        }


    }
}*/
