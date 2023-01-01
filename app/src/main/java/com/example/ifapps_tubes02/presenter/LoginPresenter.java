package com.example.ifapps_tubes02.presenter;

public class LoginPresenter {

    public String[] verify(String email, String password){
        if(email.equals("6181901068@student.unpar.ac.id")== true && password.equals("123")){
            String[] arr= new String[2];
            arr[0]= "Muhammad Fazri Raihan";
            arr[1]= "Mahasiswa";
            return arr;
        }else if(email.equals("6181901062@student.unpar.ac.id")== true && password.equals("123")){
            String[] arr= new String[2];
            arr[0]= "CLEMENTHEO CHANSON";
            arr[1]= "Dosen";
            return arr;
        }else if(email.equals("6181901040@student.unpar.ac.id")== true && password.equals("123")){
            String[] arr= new String[2];
            arr[0]= "REYGA ELKIGIA CHAYA PUTRA";
            arr[1]= "Admin";
            return arr;
        }else if(email.equals("6181901043@student.unpar.ac.id")== true && password.equals("123")){
            String[] arr= new String[2];
            arr[0]= "RANGUASAMAN SALOMON";
            arr[1]= "Admin";
            return arr;
        }else{
            return null;
        }
    }
}
