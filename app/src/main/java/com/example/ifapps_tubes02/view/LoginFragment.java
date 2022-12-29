package com.example.ifapps_tubes02.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ifapps_tubes02.databinding.LoginFragmentBinding;

public class LoginFragment extends Fragment implements View.OnClickListener {
    LoginFragmentBinding binding;

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
        return this.binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view == this.binding.btnLogin) {
            Bundle result = new Bundle();
            result.putInt("page",2);
            this.getParentFragmentManager().setFragmentResult("changePage",result);
        }
    }
}
