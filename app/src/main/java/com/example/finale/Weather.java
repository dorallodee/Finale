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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather extends AppCompatActivity {

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
            @Override
            public void onClick(View view) {
                if(user_field.getText().toString().trim().equals(""))
                    Toast.makeText(Weather.this, R.string.enter, Toast.LENGTH_LONG).show();
                else{
                String city = user_field.getText().toString().trim();
                String key = "771f71c52bc5003a745ac9074cdb920f";
                String URL = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key + "&units=metric";
                new getURL().execute(URL);
                }
            }
        });
    }

    private class getURL extends AsyncTask<String, String, String> {

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
                resultInfo.setText("Температура: " + obj.getJSONObject("main").getDouble("temp") + "°C" +
                "\nОщущается как: " + obj.getJSONObject("main").getDouble("feels_like") + "°C" +
                "\nВлажность: " + obj.getJSONObject("main").getDouble("humidity") + "%");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}