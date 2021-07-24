package com.example.projekatispit.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    private int categoryId;
    private String name;

    public CategoryModel() {
    }

    public CategoryModel(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CategoryModel parseJsonObject(JSONObject object){
        CategoryModel categoryModel = new CategoryModel();

        try{
            if(object.has("categoryId")){
                categoryModel.setCategoryId(object.getInt("categoryId"));
            }if(object.has("name")){
                categoryModel.setName(object.getString("name"));
            }

        }catch (Exception e){

        }
        return categoryModel;
    }

    public static List<CategoryModel> parseJsonArray(JSONArray array){
        List<CategoryModel> categoryModels = new ArrayList<>();
        try {
            for(int i = 0; i<array.length();i++){
                CategoryModel categoryModel = parseJsonObject(array.getJSONObject(i));
                categoryModels.add(categoryModel);
            }
        }catch (Exception e){

        }
        return categoryModels;
    }
}
