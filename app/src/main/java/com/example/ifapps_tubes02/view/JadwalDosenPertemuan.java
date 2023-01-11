package com.example.ifapps_tubes02.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ifapps_tubes02.MainActivity;
import com.example.ifapps_tubes02.databinding.FragmentJadwalDosenPertemuanBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JadwalDosenPertemuan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JadwalDosenPertemuan extends Fragment implements View.OnClickListener {

    FragmentJadwalDosenPertemuanBinding binding;

    // TODO: Rename and change types and number of parameters
    public static JadwalDosenPertemuan newInstance(String title) {
        JadwalDosenPertemuan fragment = new JadwalDosenPertemuan();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentJadwalDosenPertemuanBinding.inflate(inflater, container, false);
        this.binding.btnBack.setOnClickListener(this);
        return this.binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view==this.binding.btnBack) {
            ((MainActivity) getActivity()).changePage(7);
        }
    }
}
