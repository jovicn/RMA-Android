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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekatispit.api.Api;
import com.example.projekatispit.api.ReadDataHandler;
import com.example.projekatispit.model.AccountModel;
import com.example.projekatispit.model.BookModel;

import org.json.JSONObject;


public class OrderFinishFragment extends Fragment implements View.OnClickListener {

    private TextView labelImePotvrda,labelPrezimePotvrda, labelTelefonPotvrda, labelAdresaPotvrda, labelGradPotvrda, labelPostanskoBrojPotvrda;
    private UserData userData;
    private Button buttonFinishOrder;


    private void listaPotvrda(View v){

        LinearLayout mainScrollPotvrda = v.findViewById(R.id.mainScrolPotvrda);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (BookModel b : OrderList.porudzbina) {
            ConstraintLayout item = (ConstraintLayout) inflater.inflate(R.layout.order_list_item, null);
            ((TextView) item.findViewById(R.id.labelImeKnjigePotvrda)).setText(b.getName());
            ((TextView) item.findViewById(R.id.labelCenaPotvrda)).setText(b.getPrice() + "din");
            mainScrollPotvrda.addView(item);

        }
    }

    public OrderFinishFragment() {

    }

    public static OrderFinishFragment newInstance(String param1, String param2) {
        OrderFinishFragment fragment = new OrderFinishFragment();
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

        View v = inflater.inflate(R.layout.fragment_order_finish, container, false);
        prikazKorisnika(v);
        listaPotvrda(v);
        return v;
    }

    @SuppressLint("HandlerLeak")
    public void prikazKorisnika(View v){
        buttonFinishOrder = v.findViewById(R.id.buttonFinishOrder);
        buttonFinishOrder.setOnClickListener(this);
        labelImePotvrda = v.findViewById(R.id.labelImePotvrda);
        labelPrezimePotvrda = v.findViewById(R.id.labelPrezimePotvrda);
        labelTelefonPotvrda=v.findViewById(R.id.labelTelefonPotvrda);
        labelAdresaPotvrda= v.findViewById(R.id.labelAdresaPotvrda);
        labelGradPotvrda = v.findViewById(R.id.labelGradPotvrda);
        labelPostanskoBrojPotvrda = v.findViewById(R.id.labelPostanskiBrojPotvrda);

        Api.getJSON("http://192.168.1.100:8080/account/" + UserData.userId, new ReadDataHandler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String response = getJson();
                try{
                    JSONObject object = new JSONObject(response);
                    AccountModel a = AccountModel.parseJsonObject(object);
                    labelImePotvrda.setText(a.getForename());
                    labelPrezimePotvrda.setText(a.getSurname());
                    labelTelefonPotvrda.setText(a.getPhoneNumber());
                    labelAdresaPotvrda.setText(a.getAddres());
                    labelGradPotvrda.setText(a.getCity());
                    labelPostanskoBrojPotvrda.setText(a.getPostalCode());


                }catch(Exception e){

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonFinishOrder:
                finishOrder();
                break;
        }
    }

    private void finishOrder() {
        Toast.makeText(getContext(), "Porudzbina izvrsena!", Toast.LENGTH_SHORT).show();
        listener.onFinishOrder();
    }

    private OrderFinishFragment.OnFragmentInteractionListener listener;
    public interface OnFragmentInteractionListener {
        void onFinishOrder();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OrderFinishFragment.OnFragmentInteractionListener) {
            listener = (OrderFinishFragment.OnFragmentInteractionListener) context;
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