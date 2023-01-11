package com.example.ifapps_tubes02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ifapps_tubes02.databinding.ActivityMainBinding;
import com.example.ifapps_tubes02.view.FRSFragment;
import com.example.ifapps_tubes02.view.HomeFragment;
import com.example.ifapps_tubes02.view.IsiPengumumanFragment;
import com.example.ifapps_tubes02.view.JadwalDosenPertemuan;
import com.example.ifapps_tubes02.view.LoginFragment;
import com.example.ifapps_tubes02.view.PengumumanFragment;
import com.example.ifapps_tubes02.view.Pertemuanfragment;
import com.example.ifapps_tubes02.view.TambahPengumumanFragment;
import com.example.ifapps_tubes02.view.TambahPertemuan;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    LoginFragment loginFragment;
    HomeFragment homeFragment;
    Pertemuanfragment pertemuanfragment;
    PengumumanFragment pengumumanFragment;
    FRSFragment frsFragment;
    TambahPengumumanFragment tambahPengumumanfragment;
    IsiPengumumanFragment isiPengumumanFragment;
    TambahPertemuan tambahPertemuanfragment;
    JadwalDosenPertemuan jadwalDosenPertemuanfragment;
    FragmentManager fm;
    SharedPreferences preferences;
    String role;

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
        isiPengumumanFragment= isiPengumumanFragment.newInstance("isiPengumumanFragment");
        tambahPertemuanfragment = TambahPertemuan.newInstance("tambahPertemuan");
        jadwalDosenPertemuanfragment = JadwalDosenPertemuan.newInstance("jadwalDosenPertemuanfragment");


//        this.toolbar = binding.toolbar;
//        this.setSupportActionBar(toolbar);

//        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, dl, toolbar, R.string.openDrawer, R.string.closeDrawer);
//        this.dl.addDrawerListener(abdt);
//        abdt.syncState();
        this.fm = this.getSupportFragmentManager();

        FragmentTransaction ft = this.fm.beginTransaction();
        preferences = this.getSharedPreferences("IFAPPS-Tubes02", Context.MODE_PRIVATE);
        String retrivedToken  = preferences.getString("TOKEN",null);//second parameter default value.
        if(retrivedToken!= null){
            changePage(2);
        }else{
            ft.add(R.id.fragment_container, this.loginFragment).commit();
        }
        getBundle();
    }

    private void getBundle() {
        this.getSupportFragmentManager().setFragmentResultListener(
                "changePage", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        int page = result.getInt("page");
                        changePage(page);
                        String token = result.getString("token");
                        preferences.edit().putString("TOKEN",token).apply();
                    }
                });

        this.getSupportFragmentManager().setFragmentResultListener(
                "identifyRole", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String role = result.getString("role");
                        preferences.edit().putString("role",role).apply();
                    }
                });
    }

    public void changePage(int page) {
        FragmentTransaction ft = this.fm.beginTransaction();
        if (page == 2) {
            ft.remove(this.loginFragment);
            ft.add(R.id.fragment_container, this.homeFragment);
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
        }else if (page == 7) {
            ft.replace(R.id.fragment_container, this.tambahPertemuanfragment)
                    .addToBackStack(null);
        }else if (page == 8) {
            ft.replace(R.id.fragment_container, this.jadwalDosenPertemuanfragment)
                    .addToBackStack(null);
        }
        ft.commit();
    }
}