package com.example.ifapps_tubes02.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ifapps_tubes02.databinding.LoginFragmentBinding;
import com.example.ifapps_tubes02.presenter.LoginPresenter;

public class LoginFragment extends Fragment implements View.OnClickListener {
    LoginFragmentBinding binding;
    LoginPresenter presenter;

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
        presenter= new LoginPresenter();
        return this.binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view == this.binding.btnLogin) {
            String[] a= presenter.verify(this.binding.etUsername.getText().toString(), this.binding.etPassword.getText().toString());
            if(a== null){
                Toast.makeText(getActivity(), "email atau password yang anda masukkan salah!", Toast.LENGTH_SHORT).show();

            }else{
                Bundle result = new Bundle();
                result.putInt("page",2);
                this.getParentFragmentManager().setFragmentResult("changePage",result);
                if(a[1].equals("Admin")){
                    result.putString("nama", a[0]);
                    result.putString("role", a[1]);
                    this.getParentFragmentManager().setFragmentResult("identifyRole",result);
                }else if(a[1].equals("Mahasiswa")){
                    result.putString("nama", a[0]);
                    result.putString("role", a[1]);
                    this.getParentFragmentManager().setFragmentResult("identifyRole",result);
                }else if(a[1].equals("Dosen")){
                    result.putString("nama", a[0]);
                    result.putString("role", a[1]);
                    this.getParentFragmentManager().setFragmentResult("identifyRole",result);
                }
            }
        }
    }
}
