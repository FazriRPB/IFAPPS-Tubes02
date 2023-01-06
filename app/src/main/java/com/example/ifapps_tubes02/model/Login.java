package com.example.ifapps_tubes02.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifapps_tubes02.presenter.LoginUI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Login implements LoginUI.main {
    Gson gson;
    FragmentActivity fragmentActivity;

    public Login(FragmentActivity fragmentActivity){
        this.fragmentActivity= fragmentActivity;
        gson= new Gson();
    }


    @Override
    public void API(String[] inputArray, LoginUI.main.OnFinished onFinished) {
        String Base_URL = "https://ifportal.labftis.net/api/v1/authenticate";
        Input input =new Input(inputArray[0],
                inputArray[1],inputArray[2]);
        String inputJson = gson.toJson(input);
        RequestQueue queue = Volley.newRequestQueue(fragmentActivity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Base_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String hasil =successProcessing(response);
                onFinished.onSuccess(hasil);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String hasil = failedProcessing(error);
                    onFinished.onFailed(hasil);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return inputJson.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public String successProcessing(String response) {
        Output output = gson.fromJson(response,Output.class);
        Bundle result = new Bundle();
        result.putInt("page",2);
        result.putString("token",output.token);

        fragmentActivity.getSupportFragmentManager().setFragmentResult("changePage",result);
//        Toast.makeText(fragmentActivity,hasil,Toast.LENGTH_LONG).show();
        return "Login Berhasil";
    }

    @Override
    public String failedProcessing(VolleyError error) throws JSONException {
        String msg="";
        if(error instanceof NoConnectionError){
//            Toast.makeText(fragmentActivity,"Tidak ada koneksi internet",Toast.LENGTH_LONG).show();
            msg="Tidak ada koneksi internet";
        }else if(error instanceof TimeoutError){
            msg= "Tidak dapat terhubung dengan jaringan! \n Silahkan Coba Lagi!";
        }
        else{
            String jsonKeluaran = new String(error.networkResponse.data);
            JSONObject jsonObject = new JSONObject(jsonKeluaran);
            String keluaran = jsonObject.get("errcode").toString();
            msg = "Tidak dapat Login \n Silahkan Coba Beberapa Saat Lagi!";
            if(keluaran.equals("E_AUTH_FAILED")){
                msg = "Email, Password atau Role yang anda masukkan salah!";
            }
        }
        return msg;
    }
}
class Input{
    String email;
    String password;
    String role;
    public Input(String email,String password,String role){
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
class Output {
    String token;
    public Output(String token){
        this.token = token;
    }
}
