package com.example.ifapps_tubes02.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ifapps_tubes02.R;
import com.example.ifapps_tubes02.databinding.LoginFragmentBinding;
import com.example.ifapps_tubes02.presenter.LoginPresenter;
import com.example.ifapps_tubes02.presenter.LoginUI;

public class LoginFragment extends Fragment implements LoginUI.view, View.OnClickListener {
    LoginFragmentBinding binding;
    LoginPresenter presenter;
    String role= "";

    public static LoginFragment newInstance(String title) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = LoginFragmentBinding.inflate(inflater, container, false);
        this.binding.btnLogin.setOnClickListener(this);
        this.binding.admin.setOnClickListener(this);
        this.binding.mahasiswa.setOnClickListener(this);
        this.binding.dosen.setOnClickListener(this);
        presenter= new LoginPresenter(this, getActivity());
        return this.binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view == this.binding.btnLogin) {
            presenter.onClick();
        }
        if(view== this.binding.admin){
            onRadioButtonClicked(view);
        }
        if(view== this.binding.dosen){
            onRadioButtonClicked(view);
        }
        if(view== this.binding.mahasiswa){
            onRadioButtonClicked(view);
        }
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.admin:
                if (checked)
                    role= "admin";
                    break;
            case R.id.mahasiswa:
                if (checked)
                    role= "student";
                break;
            case R.id.dosen:
                if (checked)
                    role= "lecturer";
                break;
        }
    }
    @Override
    public String[] getInput(){
        String[] result= new String[3];
        result[0]= this.binding.etUsername.getText().toString();
        result[1]= this.binding.etPassword.getText().toString();
        result[2]= this.role;

        return result;
    }

    @Override
    public void disabledInput() {
        this.binding.etUsername.setEnabled(false);
        this.binding.etPassword.setEnabled(false);
        this.binding.etRole.setEnabled(false);
        this.binding.btnLogin.setEnabled(false);
    }

    @Override
    public void enabledInput() {
        this.binding.etUsername.setEnabled(true);
        this.binding.etPassword.setEnabled(true);
        this.binding.etRole.setEnabled(true);
        this.binding.btnLogin.setEnabled(true);
    }

    @Override
    public void showToast(String hasil) {
        Toast.makeText(getActivity(),hasil,Toast.LENGTH_LONG).show();
    }
}
