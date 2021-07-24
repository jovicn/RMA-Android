package com.example.projekatispit.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountModel {

    private int accountId;
    private String forename;
    private String surname;
    private String email;
    private String phoneNumber;
    private String addres;
    private String city;
    private String postalCode;
    private String passwordHash;

    public AccountModel() {
    }

    public AccountModel(int accountId, String forename, String surname, String email, String phoneNumber, String addres, String city, String postalCode, String passwordHash) {
        this.accountId = accountId;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addres = addres;
        this.city = city;
        this.postalCode = postalCode;
        this.passwordHash = passwordHash;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public static AccountModel parseJsonObject(JSONObject object){
        AccountModel accountModel = new AccountModel();

        try{
            if(object.has("accountId")){
                accountModel.setAccountId(object.getInt("accountId"));
            }if(object.has("forename")){
                accountModel.setForename(object.getString("forename"));
            }if(object.has("surname")){
                accountModel.setSurname(object.getString("surname"));
            }if(object.has("email")){
                accountModel.setEmail(object.getString("email"));
            }if(object.has("phoneNumber")){
                accountModel.setPhoneNumber(object.getString("phoneNumber"));
            }if(object.has("addres")){
                accountModel.setAddres(object.getString("addres"));
            }if(object.has("city")){
                accountModel.setCity(object.getString("city"));
            }if(object.has("postalCode")){
                accountModel.setPostalCode(object.getString("postalCode"));
            }if(object.has("passwordHash")){
                accountModel.setPasswordHash(object.getString("passwordHash"));
            }
        }catch (Exception e){

        }
        return accountModel;
    }

    public static List<AccountModel> parseJsonArray(JSONArray array){
        List<AccountModel> accountModels = new ArrayList<>();
        try {
            for(int i = 0; i<array.length();i++){
                AccountModel accountModel = parseJsonObject(array.getJSONObject(i));
                accountModels.add(accountModel);
            }
        }catch (Exception e){

        }
        return accountModels;
    }
}
