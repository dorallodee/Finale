package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.ActionBar;
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
import java.util.List;

public class Weather extends AppCompatActivity {
    private EditText user_field;
    private Button button;
    private TextView resultInfo1, resultInfo2, resultInfo3, resultInfo4, resultInfo5, resultInfo6, resultInfo7, resultInfo8;
    private TextView date1, date2,  date3, date4, date5, date6, date7, date8;
    private TextView tvTemp, tvMainDate, tvInfo, minMax;
    private TextView tvDay1, tvDay2, tvDay3, tvDay4, tvDayTemp1, tvDayTemp2, tvDayTemp3, tvDayTemp4, tvDay1Humidity, tvDay2Humidity, tvDay3Humidity, tvDay4Humidity;
    private TextView wind1, wind2, wind3, wind4, wind5, wind6, wind7, wind8;
    private ImageView icon1, icon2 ,icon3, icon4, icon5, icon6, icon7, icon8, iconDay1, iconDay2, iconDay3, iconDay4;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        user_field = findViewById(R.id.city);

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
        tvInfo = findViewById(R.id.tvInfo);
        minMax = findViewById(R.id.minMax);

        tvDay1 = findViewById(R.id.tvDay1);
        tvDayTemp1 = findViewById(R.id.tvDayTemp1);
        tvDay1Humidity = findViewById(R.id.tvDay1Humidity);
        tvDay2 = findViewById(R.id.tvDay2);
        tvDayTemp2 = findViewById(R.id.tvDayTemp2);
        tvDay2Humidity = findViewById(R.id.tvDay2Humidity);
        tvDay3 = findViewById(R.id.tvDay3);
        tvDayTemp3 = findViewById(R.id.tvDayTemp3);
        tvDay3Humidity = findViewById(R.id.tvDay3Humidity);
        tvDay4 = findViewById(R.id.tvDay4);
        tvDayTemp4 = findViewById(R.id.tvDayTemp4);
        tvDay4Humidity = findViewById(R.id.tvDay4Humidity);

        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        date4 = findViewById(R.id.date4);
        date5 = findViewById(R.id.date5);
        date6 = findViewById(R.id.date6);
        date7 = findViewById(R.id.date7);
        date8 = findViewById(R.id.date8);

        wind1 = findViewById(R.id.wind1);
        wind2 = findViewById(R.id.wind2);
        wind3 = findViewById(R.id.wind3);
        wind4 = findViewById(R.id.wind4);
        wind5 = findViewById(R.id.wind5);
        wind6 = findViewById(R.id.wind6);
        wind7 = findViewById(R.id.wind7);
        wind8 = findViewById(R.id.wind8);

        icon1 = findViewById(R.id.icon1);
        icon2 = findViewById(R.id.icon2);
        icon3 = findViewById(R.id.icon3);
        icon4 = findViewById(R.id.icon4);
        icon5 = findViewById(R.id.icon5);
        icon6 = findViewById(R.id.icon6);
        icon7 = findViewById(R.id.icon7);
        icon8 = findViewById(R.id.icon8);
        iconDay1 = findViewById(R.id.iconDay1);
        iconDay2 = findViewById(R.id.iconDay2);
        iconDay3 = findViewById(R.id.iconDay3);
        iconDay4 = findViewById(R.id.iconDay4);

        new getURLDay().execute("https://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=771f71c52bc5003a745ac9074cdb920f&units=metric");
        new getURLForecast().execute("https://api.openweathermap.org/data/2.5/forecast?q=Moscow&appid=771f71c52bc5003a745ac9074cdb920f&units=metric");

        button.setOnClickListener(new View.OnClickListener() {
            String city, key, URLWeatherForecast;
            @Override
            public void onClick(View view) {
                if(user_field.getText().toString().trim().equals(""))
                    Toast.makeText(Weather.this, "Введите название города", Toast.LENGTH_LONG).show();
                else{

                    city = user_field.getText().toString().trim();
                    user_field.setCursorVisible(false);
                    key = "771f71c52bc5003a745ac9074cdb920f";
                    new getURLDay().execute("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=771f71c52bc5003a745ac9074cdb920f&units=metric");
                    URLWeatherForecast = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + key + "&units=metric";
                    new getURLForecast().execute(URLWeatherForecast);
                }
            }
        });

        user_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_field.setCursorVisible(true);
            }
        });
    }

    private class getURLDay extends AsyncTask<String, String, String> {

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
                tvTemp.setText((int) obj.getJSONObject("main").getDouble("temp") + "°");
                tvInfo.setText("ощущается как " + (int) obj.getJSONObject("main").getDouble("feels_like") + "°\nвлажность " + obj.getJSONObject("main").getInt("humidity") + "%\nмин. температура " + (int) obj.getJSONObject("main").getDouble("temp_min") + "°\nмакс. температура " + (int) obj.getJSONObject("main").getDouble("temp_max") + "°");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getURLForecast extends AsyncTask<String, String, String> {

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
                int min = 9999, max = -9999, flag = 0, start = 0;
                JSONObject obj = new JSONObject(result);
                tvMainDate.setText((obj.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(0, 10).substring(8, 10)) + "." + (obj.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(0, 10).substring(5, 7)) + "." + (obj.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(0, 10).substring(0, 4)));
                for(int i = 0; i < 8; i++){
                    if((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp") < min)
                        min = (int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp");
                    if((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp") > max)
                        max = (int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp");
                    if((obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).equals("00:00:00") && flag == 0)) {
                        flag++;
                        start = i;
                    }

                    switch (i){
                        case 0:
                        {
                            resultInfo1.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date1.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                icon1.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon1.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon1.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().equals("few clouds"))
                                icon1.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon1.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                icon1.setBackgroundResource(R.drawable.snow);
                            wind1.setText(obj.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getDouble("speed") + "\nм/с");
                            break;
                        }
                        case 1:
                        {
                            resultInfo2.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date2.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                icon2.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon2.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon2.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                icon2.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon2.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                icon2.setBackgroundResource(R.drawable.snow);
                            wind2.setText(obj.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getDouble("speed") + "\nм/с");
                            break;
                        }
                        case 2:
                        {
                            resultInfo3.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date3.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                icon3.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon3.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon3.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                icon3.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon3.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                icon3.setBackgroundResource(R.drawable.snow);
                            wind3.setText(obj.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getDouble("speed") + "\nм/с");
                            break;
                        }
                        case 3:
                        {
                            resultInfo4.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date4.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                icon4.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon4.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon4.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                icon4.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon4.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                icon4.setBackgroundResource(R.drawable.snow);
                            wind4.setText(obj.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getDouble("speed") + "\nм/с");
                            break;
                        }
                        case 4:
                        {
                            resultInfo5.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date5.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds")|| obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                icon5.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon5.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon5.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                icon5.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon5.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                icon5.setBackgroundResource(R.drawable.snow);
                            wind5.setText(obj.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getDouble("speed") + "\nм/с");
                            break;
                        }
                        case 5:
                        {
                            resultInfo6.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date6.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                icon6.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon6.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon6.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                icon6.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon6.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                icon6.setBackgroundResource(R.drawable.snow);
                            wind6.setText(obj.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getDouble("speed") + "\nм/с");
                            break;
                        }
                        case 6:
                        {
                            resultInfo7.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date7.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                icon7.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon7.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon7.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                icon7.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon7.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                icon7.setBackgroundResource(R.drawable.snow);
                            wind7.setText(obj.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getDouble("speed") + "\nм/с");
                            break;
                        }
                        case 7:
                        {
                            resultInfo8.setText((int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp")+ "°");
                            date8.setText(obj.getJSONArray("list").getJSONObject(i).getString("dt_txt").substring(11).substring(0, 5));
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                icon8.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                icon8.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                icon8.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                icon8.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                icon8.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                icon8.setBackgroundResource(R.drawable.snow);
                            wind8.setText(obj.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getDouble("speed") + "\nм/с");
                            break;
                        }
                    }
                }
                int[] Min = {99, 99, 99, 99};
                int[] Max = {-99, -99, -99, -99};
                int[] Hum = {0, 0, 0, 0};
                double buf = 0;
                int c = 0;
                minMax.setText("мин. " + min + "°, макс. " + max + "°");
                for(int i = start; i < (start + 32); i++){
                    if(Min[c] > (int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp"))
                        Min[c] = (int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp");
                    if(Max[c] < (int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp"))
                        Max[c] = (int)obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp");
                    Hum[c] += obj.getJSONArray("list").getJSONObject(i).getJSONObject("main").getInt("humidity");

                    if((i - start) % 5 == 0){
                        if((i - start) == 5){
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                iconDay1.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                iconDay1.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                iconDay1.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                iconDay1.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                iconDay1.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                iconDay1.setBackgroundResource(R.drawable.snow);

                        }
                        else if((i - start) == 10){
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                iconDay2.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                iconDay2.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                iconDay2.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                iconDay2.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                iconDay2.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                iconDay2.setBackgroundResource(R.drawable.snow);
                        }
                        else if((i - start) == 15){
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                iconDay3.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                iconDay3.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                iconDay3.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                iconDay3.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                iconDay3.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                iconDay3.setBackgroundResource(R.drawable.snow);
                        }
                        else if((i - start) == 20){
                            if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                                iconDay4.setBackgroundResource(R.drawable.cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                                iconDay4.setBackgroundResource(R.drawable.rainy);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                                iconDay4.setBackgroundResource(R.drawable.sunny);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                                iconDay4.setBackgroundResource(R.drawable.partly_cloudly);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                                iconDay4.setBackgroundResource(R.drawable.fog);
                            else if(obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                                iconDay4.setBackgroundResource(R.drawable.snow);
                        }
                    }

                    if(((i - start) % 8 == 0 && (i - start) != 0)){
                        c++;
                    }
                }
                tvDay1.setText((obj.getJSONArray("list").getJSONObject(start).getString("dt_txt").substring(0, 10).substring(8, 10)) + "." + (obj.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(0, 10).substring(5, 7)));
                tvDayTemp1.setText("Днем " + Max[0] + "°\n" + "Ночью " + Min[0] + "°");
                tvDay2.setText((obj.getJSONArray("list").getJSONObject(start + 8).getString("dt_txt").substring(0, 10).substring(8, 10)) + "." + (obj.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(0, 10).substring(5, 7)));
                tvDayTemp2.setText("Днем " + Max[1] + "°\n" + "Ночью " + Min[1] + "°");
                tvDay3.setText((obj.getJSONArray("list").getJSONObject(start + 16).getString("dt_txt").substring(0, 10).substring(8, 10)) + "." + (obj.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(0, 10).substring(5, 7)));
                tvDayTemp3.setText("Днем " + Max[2] + "°\n" + "Ночью " + Min[2] + "°");
                tvDay4.setText((obj.getJSONArray("list").getJSONObject(start + 24).getString("dt_txt").substring(0, 10).substring(8, 10)) + "." + (obj.getJSONArray("list").getJSONObject(0).getString("dt_txt").substring(0, 10).substring(5, 7)));
                tvDayTemp4.setText("Днем " + Max[3] + "°\n" + "Ночью " + Min[3] + "°");

                if(Hum[0]/8 > 100)
                    tvDay1Humidity.setText(99 + "%");
                else
                    tvDay1Humidity.setText(Hum[0]/8 + "%");

                if(Hum[1]/8 > 100)
                    tvDay2Humidity.setText(99 + "%");
                else
                    tvDay2Humidity.setText(Hum[1]/8 + "%");

                if(Hum[2]/8 > 100)
                    tvDay3Humidity.setText(99 + "%");
                else
                    tvDay3Humidity.setText(Hum[2]/8 + "%");

                if(Hum[3]/8 > 100)
                    tvDay4Humidity.setText(99 + "%");
                else
                    tvDay4Humidity.setText(Hum[3]/8 + "%");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}