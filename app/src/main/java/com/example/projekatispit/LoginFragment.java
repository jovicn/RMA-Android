package com.example.projekatispit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekatispit.api.Api;
import com.example.projekatispit.api.ReadDataHandler;
import com.example.projekatispit.model.AccountModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText inputUsername, inputLozinkaPrijava;
    private Button buttonPrijaviSe, buttonRegistrujSe;

    private void init(View v){
        inputUsername = v.findViewById(R.id.inputUsername);
        inputLozinkaPrijava = v.findViewById(R.id.inputLozinkaPrijava);
        buttonPrijaviSe = v.findViewById(R.id.buttonPrijaviSe);
        buttonPrijaviSe.setOnClickListener(this);
        buttonRegistrujSe = v.findViewById(R.id.buttonRegistrujSe);
        buttonRegistrujSe.setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private void logIn(){
        Map<String, String> data = new HashMap<>();
        data.put("email", inputUsername.getText().toString());
        data.put("password", inputLozinkaPrijava.getText().toString());
        JSONObject json = new JSONObject(data);
        Api.postDataJSON("http://192.168.1.100:8080/account/login",json,new ReadDataHandler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String response = getJson();
                System.out.println(response);

                try {
                    if(!response.isEmpty()){
                        JSONObject object = new JSONObject(response);
                        AccountModel accountModel = AccountModel.parseJsonObject(object);
                        UserData.userId = Integer.toString(accountModel.getAccountId());
                        ((DrawerLocker)getActivity()).unlockDrawer();
                        listener.onLoginSuccess();
                    }else{

                    }
                }catch (Exception e){
                    //System.out.println("Usao u catch");
                }
            }
        });
    }

    public LoginFragment() {


    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ((DrawerLocker)getActivity()).lockDrawer();
        init(v);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPrijaviSe:
                logIn();
                break;
            case R.id.buttonRegistrujSe:
                listener.onRegisterOpen();
                break;

        }
    }
    private OnFragmentInteractionListener listener;
    public interface OnFragmentInteractionListener {
        void onLoginSuccess();
        void onRegisterOpen();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.OnFragmentInteractionListener) {
            listener = (LoginFragment.OnFragmentInteractionListener) context;
        } else {
            // ...
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}