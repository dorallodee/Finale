package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Weather extends AppCompatActivity {
    private EditText user_field;
    private Button button;
    private TextView resultInfo1, resultInfo2, resultInfo3, resultInfo4, resultInfo5, resultInfo6, resultInfo7, resultInfo8;
    private TextView date1, date2,  date3, date4, date5, date6, date7, date8;
    private TextView tvTemp, tvMainDate;
    private ImageView icon1, icon2 ,icon3, icon4, icon5, icon6, icon7, icon8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        user_field = findViewById(R.id.user_field);

        button = findViewById(R.id.button);

        resultInfo1 = findViewById(R.id.result1);
        resultInfo2 = findViewById(R.id.result2);
        resultInfo3 = findViewById(R.id.result3);
        resultInfo4 = findViewById(R.id.result4);
        resultInfo5 = findViewById(R.id.result5);
        resultInfo6 = findViewById(R.id.result6);
        resultInfo7 = findViewById(R.id.result7);
        resultInfo8 = findViewById(R.id.result8);

        tvTemp = findViewById(R.id.tvTemp);
        tvMainDate = findViewById(R.id.mainDate);

        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        date4 = findViewById(R.id.date4);
        date5 = findViewById(R.id.date5);
        date6 = findViewById(R.id.date6);
        date7 = findViewById(R.id.date7);
        date8 = findViewById(R.id.date8);

        icon1 = findViewById(R.id.icon1);
        icon2 = findViewById(R.id.icon2);
        icon3 = findViewById(R.id.icon3);
        icon4 = findViewById(R.id.icon4);
        icon5 = findViewById(R.id.icon5);
        icon6 = findViewById(R.id.icon6);
        icon7 = findViewById(R.id.icon7);
        icon8 = findViewById(R.id.icon8);

        new getURL().execute("https://api.openweathermap.org/data/2.5/forecast?q=Moscow&appid=771f71c52bc5003a745ac9074cdb920f&units=metric");

        button.setOnClickListener(new View.OnClickListener() {
            String city, key, URLWeather;
            @Override
            public void onClick(View view) {
                if(user_field.getText().toString().trim().equals(""))
                    Toast.makeText(Weather.this, "Введите название города", Toast.LENGTH_LONG).show();
                else{
                    city = user_field.getText().toString().trim();
                    key = "771f71c52bc5003a745ac9074cdb920f";
                    URLWeather = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + key + "&units=metric";
                    new getURL().execute(URLWeather);
                }
            }
        });
    }

    private class getURL extends AsyncTask<String, String, String> {

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
                tvTemp.setText((int)obj.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp") + "°");
                tvMainDate.setText((obj.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(0, 10).substring(8, 10)) + "." + (obj.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(0, 10).substring(5, 7)) + "." + (obj.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(0, 10).substring(0, 4)));
                for(int i = 0; i < 8; i++){
                    switch (i){
                        case 0:
                        {
                            resultInfo1.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date1.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clouds"))
                                icon1.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon1.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon1.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon1.setBackgroundResource(R.drawable.fog);
                            break;
                        }
                        case 1:
                        {
                            resultInfo2.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date2.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clouds"))
                                icon2.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon2.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon2.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon2.setBackgroundResource(R.drawable.fog);
                            break;
                        }
                        case 2:
                        {
                            resultInfo3.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date3.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clouds"))
                                icon3.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon3.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon3.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon3.setBackgroundResource(R.drawable.fog);
                            break;
                        }
                        case 3:
                        {
                            resultInfo4.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date4.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clouds"))
                                icon4.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon4.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon4.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon4.setBackgroundResource(R.drawable.fog);
                            break;
                        }
                        case 4:
                        {
                            resultInfo5.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date5.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clouds"))
                                icon5.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon5.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon5.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon5.setBackgroundResource(R.drawable.fog);
                            break;
                        }
                        case 5:
                        {
                            resultInfo6.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date6.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clouds"))
                                icon6.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon6.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon6.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon6.setBackgroundResource(R.drawable.fog);
                            break;
                        }
                        case 6:
                        {
                            resultInfo7.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date7.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clouds"))
                                icon7.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon7.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon7.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon7.setBackgroundResource(R.drawable.fog);
                            break;
                        }
                        case 7:
                        {
                            resultInfo8.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date8.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clouds"))
                                icon8.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon8.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon8.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon8.setBackgroundResource(R.drawable.fog);
                            break;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}