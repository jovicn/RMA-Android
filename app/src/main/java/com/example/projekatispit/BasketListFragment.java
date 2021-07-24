package com.example.projekatispit;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projekatispit.model.BookModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasketListFragment extends Fragment implements View.OnClickListener{


    private Button buttonCheckout;


   private void orderBook(View v){
        buttonCheckout = v.findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(this);

        LinearLayout mainScrollKorpa = v.findViewById(R.id.mainScrolKorpa);
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(BookModel b : OrderList.porudzbina){
            ConstraintLayout item = (ConstraintLayout)inflater.inflate(R.layout.basket_list_item,null);
            ((TextView)item.findViewById(R.id.labelImeKnjigeKorpa)).setText(b.getName());
            ((TextView)item.findViewById(R.id.labelCenaKorpa)).setText(b.getPrice() + "din");
            mainScrollKorpa.addView(item);
            ImageButton imageButton4 = (ImageButton) item.findViewById(R.id.imageButton4);
            imageButton4.setTag(b.getBookId());
            imageButton4.setOnClickListener(this);
        }


    }


    public BasketListFragment() {

    }



    public static BasketListFragment newInstance(String param1, String param2) {
        BasketListFragment fragment = new BasketListFragment();
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


        View v = inflater.inflate(R.layout.fragment_basket_list, container, false);
        orderBook(v);
        return v;
    }

    private BasketListFragment.OnFragmentInteractionListener listener;

   private void removeBookFromList(View v){
      for(BookModel b : OrderList.porudzbina){
          if(b.getBookId() == (Integer)v.getTag()){
              OrderList.porudzbina.remove(b);
              System.out.println("Obrisano");
          }

      }

      listener.onDeleteItemFromList();

   }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton4:
                removeBookFromList(v);
                break;
            case R.id.buttonCheckout:
                checkout();
                break;
        }
    }

    private void checkout() {
       listener.onCheckout();
    }

    public interface OnFragmentInteractionListener {
        void onBookSelect(String bookId);
        void onDeleteItemFromList();
        void onCheckout();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BasketListFragment.OnFragmentInteractionListener) {
            listener = (BasketListFragment.OnFragmentInteractionListener) context;
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