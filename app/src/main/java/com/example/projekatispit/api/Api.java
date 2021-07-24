package com.example.projekatispit.api;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {

    public static void getJSON(String url, final ReadDataHandler rdh) {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String response = "";
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String row;
                    while ((row = reader.readLine()) != null) {
                        response += row + "\n";
                    }

                    reader.close();
                    connection.disconnect();
                } catch (Exception e) {
                    response = "[]";

                }

                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                // super.onPostExecute(s);
                rdh.setJson(response);
                rdh.sendEmptyMessage(0);
            }
        };
        task.execute(url);
    }

    public static void postDataJSON(String url, final JSONObject data, final ReadDataHandler rdh) {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPostExecute(String response) {
                rdh.setJson(response);
                rdh.sendEmptyMessage(0);
            }
            @Override
            protected String doInBackground(String... strings) {
                String response = "";
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    connection.setRequestProperty("Accept", "application/json");
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    writer.write(data.toString());
                    writer.flush();
                    writer.close();
                    connection.getOutputStream().close();
                    connection.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String row;
                    while ((row = reader.readLine()) != null) {
                        System.out.println("Row: " + row);
                        response += row + "\n";
                    }
                } catch (Exception e) {
                    response = "[]";
                }
                return response;
            }
        };
        task.execute(url);
    }

}
