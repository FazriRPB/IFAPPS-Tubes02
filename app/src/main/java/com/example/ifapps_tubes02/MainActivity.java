package com.example.ifapps_tubes02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.ifapps_tubes02.databinding.ActivityMainBinding;
import com.example.ifapps_tubes02.view.FRSFragment;
import com.example.ifapps_tubes02.view.HomeFragment;
import com.example.ifapps_tubes02.view.LoginFragment;
import com.example.ifapps_tubes02.view.PengumumanFragment;
import com.example.ifapps_tubes02.view.Pertemuanfragment;
import com.example.ifapps_tubes02.view.TambahPengumumanFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    LoginFragment loginFragment;
    HomeFragment homeFragment;
    Pertemuanfragment pertemuanfragment;
    PengumumanFragment pengumumanFragment;
    FRSFragment frsFragment;
    TambahPengumumanFragment tambahPengumumanfragment;
    FragmentManager fm;
    DrawerLayout dl;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());

        loginFragment = LoginFragment.newInstance("loginFrament");
        homeFragment = HomeFragment.newInstance("homeFrament");
        pertemuanfragment = Pertemuanfragment.newInstance("pertemuanFrament");
        pengumumanFragment = PengumumanFragment.newInstance("pengumumanFrament");
        frsFragment = FRSFragment.newInstance("frsFrament");
        tambahPengumumanfragment = TambahPengumumanFragment.newInstance("tambahPengumumanFragment");


//        this.toolbar = binding.toolbar;
//        this.setSupportActionBar(toolbar);

//        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, dl, toolbar, R.string.openDrawer, R.string.closeDrawer);
//        this.dl.addDrawerListener(abdt);
//        abdt.syncState();

        this.fm = this.getSupportFragmentManager();
        changePage(1);

        this.getSupportFragmentManager().setFragmentResultListener(
                "changePage", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        int page = result.getInt("page");
                        changePage(page);
                    }
                });
    }

    public void changePage(int page) {
        FragmentTransaction ft = this.fm.beginTransaction();
        if (page == 1) {
            ft.add(R.id.fragment_container, this.loginFragment)
                    .addToBackStack(null);
        }else if (page == 2) {
            ft.replace(R.id.fragment_container, this.homeFragment)
                    .addToBackStack(null);
        }else if (page == 3) {
            ft.replace(R.id.fragment_container, this.pengumumanFragment)
                    .addToBackStack(null);
        }else if (page == 4) {
            ft.replace(R.id.fragment_container, this.pertemuanfragment)
                    .addToBackStack(null);
        }else if (page == 5) {
            ft.replace(R.id.fragment_container, this.frsFragment)
                    .addToBackStack(null);
        }else if (page == 6) {
            ft.replace(R.id.fragment_container, this.tambahPengumumanfragment)
                    .addToBackStack(null);
        }
        ft.commit();
    }
}