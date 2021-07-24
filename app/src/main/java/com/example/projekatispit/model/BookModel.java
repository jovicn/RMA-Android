package com.example.projekatispit.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookModel {

    private int bookId;
    private String name;
    private String numberOfPages;
    private String yearOfIssue;
    private String description;
    private String price;

    public BookModel() {
    }

    public BookModel(int bookId, String name, String numberOfPages, String yearOfIssue, String description, String price) {
        this.bookId = bookId;
        this.name = name;
        this.numberOfPages = numberOfPages;
        this.yearOfIssue = yearOfIssue;
        this.description = description;
        this.price = price;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(String yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public static BookModel parseJsonObject(JSONObject object){
        BookModel bookModel = new BookModel();

        try{
            if(object.has("bookId")){
                bookModel.setBookId(object.getInt("bookId"));
            }if(object.has("name")){
                bookModel.setName(object.getString("name"));
            }if(object.has("numberOfPages")){
                bookModel.setNumberOfPages(object.getString("numberOfPages"));
            }if(object.has("yearOfIssue")){
                bookModel.setYearOfIssue(object.getString("yearOfIssue"));
            }if(object.has("description")){
                bookModel.setDescription(object.getString("description"));
            }if(object.has("price")){
                bookModel.setPrice(object.getString("price"));
            }
        }catch (Exception e){

        }
        return bookModel;
    }

    public static List<BookModel> parseJsonArray(JSONArray array){
        List<BookModel> bookModels = new ArrayList<>();
        try {
            for(int i = 0; i<array.length();i++){
                BookModel bookModel = parseJsonObject(array.getJSONObject(i));
                bookModels.add(bookModel);
            }
        }catch (Exception e){

        }
        return bookModels;
    }

}
