package com.example.projekatispit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projekatispit.api.Api;
import com.example.projekatispit.api.ReadDataHandler;
import com.example.projekatispit.model.AccountModel;
import com.example.projekatispit.model.AuthorModel;
import com.example.projekatispit.model.BookModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class ProfileFragment extends Fragment {

    private TextView labelIme, labelPrezime, labelEmail, labelTelefon, labelAdresa, labelGrad, labelPostanskiBr;

    public ProfileFragment() {

    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        init(v);
        return v;
    }

    @SuppressLint("HandlerLeak")
    private void init(View v){

        labelIme = v.findViewById(R.id.labelIme);
        labelPrezime = v.findViewById(R.id.labelPrezime);
        labelEmail = v.findViewById(R.id.labelEmail);
        labelTelefon = v.findViewById(R.id.labelTelefon);
        labelAdresa = v.findViewById(R.id.labelAdresa);
        labelGrad = v.findViewById(R.id.labelGrad);
        labelPostanskiBr = v.findViewById(R.id.labelPostanskiBr);

        Api.getJSON("http://192.168.1.100:8080/account/" + UserData.userId, new ReadDataHandler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String response = getJson();
                try{
                    JSONObject object = new JSONObject(response);
                    AccountModel a = AccountModel.parseJsonObject(object);
                    labelIme.setText(a.getForename());
                    labelPrezime.setText(a.getSurname());
                    labelEmail.setText(a.getEmail());
                    labelTelefon.setText(a.getPhoneNumber());
                    labelAdresa.setText(a.getAddres());
                    labelGrad.setText(a.getCity());
                    labelPostanskiBr.setText(a.getPostalCode());


                }catch(Exception e){

                }
            }
        });

    }
}