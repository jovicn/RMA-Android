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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekatispit.api.Api;
import com.example.projekatispit.api.ReadDataHandler;
import com.example.projekatispit.model.BookModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class BookListFragment extends Fragment {


    private List<BookModel> books;

    @SuppressLint("HandlerLeak")
    private void loadBooks(View v){
        Api.getJSON("http://192.168.1.100:8080/book", new ReadDataHandler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String response = getJson();
                try{
                    JSONArray array = new JSONArray(response);
                    books = BookModel.parseJsonArray(array);
                    LinearLayout mainScrollView = v.findViewById(R.id.mainScrollView);
                    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    for(BookModel b : books){
                        ConstraintLayout item = (ConstraintLayout)inflater.inflate(R.layout.book_list_item,null);
                        ((TextView)item.findViewById(R.id.labelImeKnjige)).setText(b.getName());
                        ((TextView)item.findViewById(R.id.labelGodina)).setText(b.getYearOfIssue() + "god");
                        ((TextView)item.findViewById(R.id.labelCena)).setText(b.getPrice() + "din");

                        item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("onClick");
                                listener.onBookSelect(Integer.toString(b.getBookId()));
                            }
                        });
                        mainScrollView.addView(item);

                    }
                }catch(Exception e){

                }
            }
        });
    }


    public BookListFragment() {

    }

    public static BookListFragment newInstance(String param1, String param2) {
        BookListFragment fragment = new BookListFragment();
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
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);
        System.out.println("BookList oncreateview");
        loadBooks(v);
        return v;
    }

    private BookListFragment.OnFragmentInteractionListener listener;
    public interface OnFragmentInteractionListener {
        void onBookSelect(String bookId);
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BookListFragment.OnFragmentInteractionListener) {
            listener = (BookListFragment.OnFragmentInteractionListener) context;
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