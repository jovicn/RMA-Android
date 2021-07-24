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
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekatispit.api.Api;
import com.example.projekatispit.api.ReadDataHandler;
import com.example.projekatispit.model.AuthorModel;
import com.example.projekatispit.model.BookModel;
import com.example.projekatispit.model.CategoryModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class BookDetailFragment extends Fragment implements View.OnClickListener{

    private TextView labelNaslov, labelAutor, labelOpis, labelStranice, labelGodinaKnjiga, labelCenaKnjiga, labelCategory;
    private Button buttonDodajUKorpu;

    private static final String ARG_BOOK_ID = "bookId";

    private BookModel book;
    private String bookId;


    public BookDetailFragment() {

    }


    public static BookDetailFragment newInstance(String param1) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BOOK_ID, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookId = getArguments().getString(ARG_BOOK_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_book_detail, container, false);
        init(v);
        return v;
    }
    @SuppressLint("HandlerLeak")
    private void init(View v){
        labelNaslov = v.findViewById(R.id.labelNaslov);
        labelAutor = v.findViewById(R.id.labelAutor);
        labelOpis = v.findViewById(R.id.labelOpis);
        labelStranice = v.findViewById(R.id.labelStranice);
        labelGodinaKnjiga = v.findViewById(R.id.labelGodinaKnjiga);
        labelCenaKnjiga = v.findViewById(R.id.labelCenaKnjiga);
        labelCategory = v.findViewById(R.id.labelCategory);
        buttonDodajUKorpu = v.findViewById(R.id.buttonDodajUKorpu);
        buttonDodajUKorpu.setOnClickListener(this);


        Api.getJSON("http://192.168.1.100:8080/book/" + bookId, new ReadDataHandler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String response = getJson();
                try{
                    JSONObject object= new JSONObject(response);
                    book = BookModel.parseJsonObject(object);
                    labelNaslov.setText(book.getName());
                    labelOpis.setText(book.getDescription());
                    labelCenaKnjiga.setText(book.getPrice() + "din");
                    labelGodinaKnjiga.setText(book.getYearOfIssue());
                    labelStranice.setText(book.getNumberOfPages());
                    Api.getJSON("http://192.168.1.100:8080/book/" + bookId + "/authors", new ReadDataHandler(){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            String response = getJson();
                            try{
                                JSONArray array= new JSONArray(response);
                                List<AuthorModel> authors = AuthorModel.parseJsonArray(array);
                                String authorsString = "";
                                for(AuthorModel a : authors){
                                    authorsString += a.getForename() + "\n" + a.getSurname() + "\n";
                                }
                                labelAutor.setText(authorsString);

                            }catch(Exception e){

                            }
                        }
                    });

                    Api.getJSON("http://192.168.1.100:8080/book/" + bookId + "/categories", new ReadDataHandler(){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            String response = getJson();
                            try{
                                JSONArray array= new JSONArray(response);
                                List<CategoryModel> categories = CategoryModel.parseJsonArray(array);
                                String categoriesString = "";
                                for(CategoryModel c : categories){
                                    categoriesString += c.getName() + ", " + "\n";
                                }
                                labelCategory.setText(categoriesString);

                            }catch(Exception e){

                            }
                        }
                    });

                }catch(Exception e){

                }
            }
        });

    }

    private void dodajUKorpu(){

        OrderList.porudzbina.add(book);
       // System.out.println("Dodato u korpu");
        Toast.makeText(getContext(),"Knjiga je dodata u korpu", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDodajUKorpu:
                dodajUKorpu();
                break;
        }
    }




}