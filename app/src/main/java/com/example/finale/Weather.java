package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class Weather extends AppCompatActivity {
    private String cityLat, cityLon;
    private EditText user_field;
    private Button button;
    private TextView resultInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        user_field = findViewById(R.id.user_field);
        button = findViewById(R.id.button);
        resultInfo = findViewById(R.id.result);


        button.setOnClickListener(new View.OnClickListener() {
            String city, key, URLWeather, URLGeocoding;
            @Override
            public void onClick(View view) {
                if(user_field.getText().toString().trim().equals(""))
                    Toast.makeText(Weather.this, "Пусто", Toast.LENGTH_LONG).show();
                else{

                city = user_field.getText().toString().trim();
                key = "771f71c52bc5003a745ac9074cdb920f";
                //URLGeocoding = "http://api.openweathermap.org/geo/1.0/direct?q=" + getCity + "&limit=1&appid=" + key;

                //new getURLForGeocode().execute(URLGeocoding);

                URLWeather = "api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + key + "&units=metric";

                new getURLForWeather().execute(URLWeather);

                }
            }
        });
    }

/*
    private class getURLForGeocode extends AsyncTask<String, String, String> {

        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL URL = new URL(strings[0]);
                connection = (HttpURLConnection) URL.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while((line = reader.readLine()) != null){
                    buffer.append(line).append("\n");

                    return  buffer.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();

                    try {
                        if(reader != null){
                            reader.close();
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return "";
        }

        @SuppressLint("SetTextI18n")
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject obj = new JSONObject(result);
                cityLat = obj.getJSONObject("name").getString("lat");
                Toast.makeText(Weather.this, cityLat, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
*/

    private class getURLForWeather extends AsyncTask<String, String, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            resultInfo.setText("If the search takes too long, then you may have entered the wrong city name...");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL URL = new URL(strings[0]);
                connection = (HttpURLConnection) URL.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while((line = reader.readLine()) != null){
                    buffer.append(line).append("\n");

                    return  buffer.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();

                    try {
                        if(reader != null){
                            reader.close();
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return "";
        }

        @SuppressLint("SetTextI18n")
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject obj = new JSONObject(result);


/*                // Считываем json

// Достаём firstName and lastName
                String firstName = (String) obj.get("firstName");
                String lastName = (String) obj.get("lastName");
                System.out.println("fio: " + firstName + " " + lastName);
// Достаем массив номеров
                JSONArray phoneNumbersArr = (JSONArray) obj.get("phoneNumbers");
                Iterator phonesItr = phoneNumbersArr.iterator();
                System.out.println("phoneNumbers:");
// Выводим в цикле данные массива
                while (phonesItr.hasNext()) {
                    JSONObject test = (JSONObject) phonesItr.next();
                    System.out.println("- type: " + test.get("type") + ", phone: " + test.get("number"));
                }*/






                //for(String str: obj.getJSONObject("list"))

                resultInfo.setText(obj.getJSONObject("main").getDouble("temp") + "°");
                /*resultInfo.setText("Температура: " + obj.getJSONObject("main").getDouble("temp") + "°" + "\nОщущается как: " + obj.getJSONObject("main").getDouble("feels_like") + "°" +
                        "\nВлажность: " + obj.getJSONObject("main").getDouble("humidity") + "%");*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}