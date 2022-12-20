package com.example.ifapps_tubes02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.ifapps_tubes02.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    HomeFragment homeFragment;
    Pertemuanfragment pertemuanfragment;
    PengumumanFragment pengumumanFragment;
    FRSFragment frsFragment;
    DrawerFragment drawer;
    FragmentManager fm;
    DrawerLayout dl;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());

        homeFragment = HomeFragment.newInstance("homeFrament");
        pertemuanfragment = Pertemuanfragment.newInstance("pertemuanFrament");
        pengumumanFragment = PengumumanFragment.newInstance("pengumumanFrament");
        frsFragment = FRSFragment.newInstance("frsFrament");

        this.toolbar = binding.toolbar;
        this.dl = binding.drawerLayout;
        this.setSupportActionBar(toolbar);

        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this,dl,toolbar,R.string.openDrawer,R.string.closeDrawer);
        this.dl.addDrawerListener(abdt);
        abdt.syncState();

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

    public void changePage(int page){
        FragmentTransaction ft = this.fm.beginTransaction();
        if(page==1){
            System.out.println("Change p1");
            if(this.homeFragment.isAdded()){
                ft.show(this.homeFragment);
            }else{
                ft.add(R.id.fragment_container,this.homeFragment);
            }
            if(this.pengumumanFragment.isAdded()){
                ft.hide(this.pengumumanFragment);
            }
            if(this.pertemuanfragment.isAdded()){
                ft.hide(this.pertemuanfragment);
            }
            if(this.frsFragment.isAdded()){
                ft.hide(this.frsFragment);
            }
        }else if(page==2){
            System.out.println("Change p2");
            if(this.pengumumanFragment.isAdded()){
                ft.show(this.pengumumanFragment);
            }else{
                ft.add(R.id.fragment_container,this.pengumumanFragment);
            }
            if(this.homeFragment.isAdded()){
                ft.hide(this.homeFragment);
            }
            if(this.pertemuanfragment.isAdded()){
                ft.hide(this.pertemuanfragment);
            }
            if(this.frsFragment.isAdded()){
                ft.hide(this.frsFragment);
            }
        }else if(page==3){
            System.out.println("Change p3");
            if(this.pertemuanfragment.isAdded()){
                ft.show(this.pertemuanfragment);

            }else{
                ft.add(R.id.fragment_container,this.pertemuanfragment);
            }
            if(this.homeFragment.isAdded()){
                ft.hide(this.homeFragment);
            }
            if(this.pengumumanFragment.isAdded()){
                ft.hide(this.pengumumanFragment);
            }
            if(this.frsFragment.isAdded()){
                ft.hide(this.frsFragment);
            }
        }else if(page==4){
            System.out.println("Change p4");
            if(this.frsFragment.isAdded()){
                ft.show(this.frsFragment);

            }else{
                ft.add(R.id.fragment_container,this.frsFragment);
            }
            if(this.homeFragment.isAdded()){
                ft.hide(this.homeFragment);
            }
            if(this.pengumumanFragment.isAdded()){
                ft.hide(this.pertemuanfragment);
            }
            if(this.pertemuanfragment.isAdded()){
                ft.hide(this.pertemuanfragment);
            }
        }
        ft.commit();
    }
}