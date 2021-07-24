package com.example.projekatispit.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AuthorModel {


    private int authorId;
    private String forename;
    private String surname;

    public AuthorModel() {
    }

    public AuthorModel(int authorId, String forename, String surname) {
        this.authorId = authorId;
        this.forename = forename;
        this.surname = surname;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public static AuthorModel parseJsonObject(JSONObject object){
        AuthorModel autorModel = new AuthorModel();

        try{
            if(object.has("authorId")){
                autorModel.setAuthorId(object.getInt("authorId"));
            }if(object.has("forename")){
                autorModel.setForename(object.getString("forename"));
            }if(object.has("surname")){
                autorModel.setSurname(object.getString("surname"));
            }

        }catch (Exception e){

        }
        return autorModel;
    }

    public static List<AuthorModel> parseJsonArray(JSONArray array){
        List<AuthorModel> authorModels = new ArrayList<>();
        try {
            for(int i = 0; i<array.length();i++){
                AuthorModel authorModel = parseJsonObject(array.getJSONObject(i));
                authorModels.add(authorModel);
            }
        }catch (Exception e){

        }
        return authorModels;
    }
}
