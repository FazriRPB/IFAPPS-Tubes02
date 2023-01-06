package com.example.ifapps_tubes02.presenter;

import androidx.fragment.app.FragmentActivity;

import com.example.ifapps_tubes02.model.Login;

public class LoginPresenter implements LoginUI.presenter, LoginUI.main.OnFinished{
    LoginUI.view view;
    LoginUI.main main;
    FragmentActivity fragmentActivity;

    public LoginPresenter(LoginUI.view view, FragmentActivity fragmentActivity) {
        this.view = view;
        this.fragmentActivity = fragmentActivity;
        this.main = new Login(fragmentActivity);
    }
    @Override
    public void onSuccess(String hasil) {
        view.showToast(hasil);

    }

    @Override
    public void onFailed(String hasil) {
        view.enabledInput();
        view.showToast(hasil);
    }

    @Override
    public void onClick() {
        view.disabledInput();
        main.API(view.getInput(), this);
    }

    //    public String[] verify(String email, String password){
//        if(email.equals("6181901068@student.unpar.ac.id")== true && password.equals("123")){
//            String[] arr= new String[2];
//            arr[0]= "Muhammad Fazri Raihan";
//            arr[1]= "Mahasiswa";
//            return arr;
//        }else if(email.equals("6181901062@student.unpar.ac.id")== true && password.equals("123")){
//            String[] arr= new String[2];
//            arr[0]= "CLEMENTHEO CHANSON";
//            arr[1]= "Dosen";
//            return arr;
//        }else if(email.equals("6181901040@student.unpar.ac.id")== true && password.equals("123")){
//            String[] arr= new String[2];
//            arr[0]= "REYGA ELKIGIA CHAYA PUTRA";
//            arr[1]= "Admin";
//            return arr;
//        }else if(email.equals("6181901043@student.unpar.ac.id")== true && password.equals("123")){
//            String[] arr= new String[2];
//            arr[0]= "RANGUASAMAN SALOMON";
//            arr[1]= "Admin";
//            return arr;
//        }else{
//            return null;
//        }
//    }


}
