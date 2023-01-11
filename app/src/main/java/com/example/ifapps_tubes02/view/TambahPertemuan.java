package com.example.ifapps_tubes02.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.ifapps_tubes02.R;
import com.example.ifapps_tubes02.databinding.ItemListPertemuanBinding;
import com.example.ifapps_tubes02.databinding.FragmentTambahPertemuanBinding;

import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TambahPertemuan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TambahPertemuan extends Fragment implements View.OnClickListener {

    FragmentTambahPertemuanBinding binding;
    public DatePickerDialog datePickerDialog;
    public TimePickerDialog timePickerDialog;
    int jam,menit;

    public static TambahPertemuan newInstance(String title) {
        TambahPertemuan fragment = new TambahPertemuan();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentTambahPertemuanBinding.inflate(inflater, container, false);
        this.binding.tglbtn.setOnClickListener(this);
        initDatePicker();
        this.binding.timebtn.setOnClickListener(this);
        inittimePicker();
        return this.binding.getRoot();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDataString(day, month, year);
                binding.tglbtn.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this.getContext(), style, dateSetListener, year, month, day);

    }

    private String makeDataString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JANUARI";
        if (month == 2)
            return "FEBRUARI";
        if (month == 3)
            return "MARET";
        if (month == 4)
            return "APRIL";
        if (month == 5)
            return "MEI";
        if (month == 6)
            return "JUNI";
        if (month == 7)
            return "JULI";
        if (month == 8)
            return "AGUSTUS";
        if (month == 9)
            return "SEPTEMBER";
        if (month == 10)
            return "OKTOBER";
        if (month == 11)
            return "NOVEMBER";
        if (month == 12)
            return "DESEMBER";
        return "JANUARI";
    }
    public void inittimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int chooseJam, int chooseMenit) {
                jam = chooseJam;
                menit = chooseMenit;
                binding.timebtn.setText(String.format(Locale.getDefault(), "%02d:%02d", jam, menit));
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this.getContext(), style,  onTimeSetListener, jam, menit, true);
        timePickerDialog.setTitle("Pilih Waktu Pertemuan Dengan Dosen");
    }
    @Override
    public void onClick(View view) {
        if (view == this.binding.tglbtn) {
            datePickerDialog.show();
        }else if (view == this.binding.timebtn) {
            timePickerDialog.show();
        }
    }
}