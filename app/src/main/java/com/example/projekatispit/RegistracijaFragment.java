package com.example.projekatispit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.projekatispit.api.Api;
import com.example.projekatispit.api.ReadDataHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegistracijaFragment extends Fragment implements View.OnClickListener{


    private EditText inputIme, inputPrezime, inputEmail, inputTelefon, inputAdresa, inputGrad, inputPostanskiBroj, inputSifra;
    private Button buttonRegistracija;

    private void init(View v){
        inputIme = v.findViewById(R.id.inputIme);
        inputPrezime = v.findViewById(R.id.inputPrezime);
        inputEmail = v.findViewById(R.id.inputEmail);
        inputTelefon = v.findViewById(R.id.inputTelefon);
        inputAdresa = v.findViewById(R.id.inputAdresa);
        inputGrad = v.findViewById(R.id.inputGrad);
        inputPostanskiBroj = v.findViewById(R.id.inputPostanskiBroj);
        inputSifra = v.findViewById(R.id.inputSifra);
        buttonRegistracija = v.findViewById(R.id.buttonRegistracija);
        buttonRegistracija.setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private void registracija(){
        Map<String, String> data = new HashMap<>();
        data.put("forename", inputIme.getText().toString());
        data.put("surename", inputPrezime.getText().toString());
        data.put("email", inputEmail.getText().toString());
        data.put("phoneNumber", inputTelefon.getText().toString());
        data.put("addres", inputAdresa.getText().toString());
        data.put("city", inputGrad.getText().toString());
        data.put("postalCode", inputPostanskiBroj.getText().toString());
        data.put("passwordHash", inputSifra.getText().toString());
        JSONObject json = new JSONObject(data);
        Api.postDataJSON("http://192.168.1.100:8080/account/reg",json,new ReadDataHandler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String response = getJson();
                System.out.println(response);

                try {
                    if(!response.isEmpty()){
                        JSONObject object = new JSONObject(response);
                        listener.onRegistrySuccess();
                    }else{

                    }
                }catch (Exception e){
                    //System.out.println("Usao u catch");
                }
            }
        });
    }

    public RegistracijaFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static RegistracijaFragment newInstance(String param1, String param2) {
        RegistracijaFragment fragment = new RegistracijaFragment();
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

        View v =inflater.inflate(R.layout.fragment_registracija, container, false);
        ((DrawerLocker)getActivity()).lockDrawer();
        init(v);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegistracija:
                registracija();
                break;
        }

    }

    private RegistracijaFragment.OnFragmentInteractionListener listener;
    public interface OnFragmentInteractionListener {
        void onRegistrySuccess();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.OnFragmentInteractionListener) {
            listener = (RegistracijaFragment.OnFragmentInteractionListener) context;
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