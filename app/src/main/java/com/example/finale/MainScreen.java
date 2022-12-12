package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainScreen extends AppCompatActivity {
    int index;

    Button btnFunc, btnSupport, btnHistory;
    TextView txtDeposits, txtLoans, txtSecurity, currency1, currency2,
            tvCard1, tvCard2, tvBalance1, tvBalance2, tvWeather, tvName, tvBuffer;
    ImageView ivWeather, miniHist, ivWeatherIcon;
    ImageView ivCurrency;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sreen);

        btnFunc = findViewById(R.id.buttonFunc);
        btnSupport = findViewById(R.id.buttonSupport);
        btnHistory = findViewById(R.id.buttonHistory);

        txtDeposits = findViewById(R.id.depositsAndInvestmentsText);
        txtLoans = findViewById(R.id.loansText);
        txtSecurity = findViewById(R.id.securityText);
        tvWeather = findViewById(R.id.tvWeather);
        tvName = findViewById(R.id._name);
        tvCard1 = findViewById(R.id.card1);
        tvCard2 = findViewById(R.id.card2);
        tvBalance1 = findViewById(R.id.balance1);
        tvBalance2 = findViewById(R.id.balance2);
        ivWeather = findViewById(R.id.ivWeather);
        miniHist = findViewById(R.id.miniHistory);
        tvBuffer = findViewById(R.id.buffer);

        currency1 = findViewById(R.id.currency1);
        currency2 = findViewById(R.id.currency2);

        ivCurrency = findViewById(R.id.Currency);
        ivWeatherIcon = findViewById(R.id.ivWeatherIcon);

        mDBHelper = new DBHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Bundle arguments = getIntent().getExtras();
        String strInd;

        if(arguments != null)
            strInd = arguments.get("index").toString();
        else
            strInd = "0";

        index = Integer.parseInt(strInd);

        String name, patronymic, surname, login, password, card1, card2,
                phoneNum, pin1, pin2, cvv1, cvv2, balance1, balance2;

        // Отправляем запрос в БД
        Cursor cursor = mDb.rawQuery("SELECT * FROM Accounts", null);
        cursor.move(index + 1);

        // Пробегаем по клиенту
        name = cursor.getString(1);
        patronymic = cursor.getString(2);
        surname = cursor.getString(3);
        login = cursor.getString(4);
        password = cursor.getString(5);

        card1 = cursor.getString(6);
        card2 = cursor.getString(7);

        phoneNum = cursor.getString(8);
        pin1 = cursor.getString(9);
        pin2 = cursor.getString(10);
        cvv1 = cursor.getString(11);
        cvv2 = cursor.getString(12);

        balance1 = String.valueOf(cursor.getDouble(13));

        balance2 = String.valueOf(cursor.getDouble(14));

        tvName.setText(name);

        tvCard1.setText("Карта ···· " + card1.substring(card1.length() - 4));

        tvCard2.setText("Карта ···· " + card2.substring(card2.length() - 4));

        tvBalance1.setText((balance1.length() > 3 ?
                (balance1.contains(".") ?
                        balance1.substring(0, balance1.length() - 5) + " "
                                + balance1.substring(balance1.length() - 5) :
                        balance1.substring(0, balance1.length() - 3) + " "
                                + balance1.substring(balance1.length() - 3)) : balance1) + " ₽");

        tvBalance2.setText((balance2.length() > 3 ?
                (balance2.contains(".") ?
                        balance2.substring(0, balance2.length() - 5) + " "
                                + balance2.substring(balance2.length() - 5) :
                        balance2.substring(0, balance2.length() - 3) + " "
                                + balance2.substring(balance2.length() - 3)) : balance2) + " ₽");

        String URLCurrency = "https://cdn.cur.su/api/cbr.json";
        new getURLForCurrency().execute(URLCurrency);

        String key = "771f71c52bc5003a745ac9074cdb920f";
        //String URLWeather = "https://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=" + key + "&units=metric";
        //new getURLForWeather().execute(URLWeather);

        tvBalance1.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, Card1.class);
            intent.putExtra("index", strInd);
            intent.putExtra("name", name);
            intent.putExtra("patronymic", patronymic);
            intent.putExtra("surname", surname);
            intent.putExtra("card", card1);
            intent.putExtra("balance", balance1);
            startActivity(intent);
        });

        tvBalance2.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, Card2.class);
            intent.putExtra("index", strInd);
            intent.putExtra("name", name);
            intent.putExtra("patronymic", patronymic);
            intent.putExtra("surname", surname);
            intent.putExtra("card", card2);
            intent.putExtra("balance", balance2);
            startActivity(intent);
        });

        tvCard1.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, Card1.class);
            intent.putExtra("index", strInd);
            intent.putExtra("name", name);
            intent.putExtra("patronymic", patronymic);
            intent.putExtra("surname", surname);
            intent.putExtra("card", card1);
            intent.putExtra("balance", balance1);
            startActivity(intent);
        });

        tvCard2.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, Card2.class);
            intent.putExtra("index", strInd);
            intent.putExtra("name", name);
            intent.putExtra("patronymic", patronymic);
            intent.putExtra("surname", surname);
            intent.putExtra("card", card2);
            intent.putExtra("balance", balance2);
            startActivity(intent);
        });

        btnFunc.setOnClickListener(view -> {
            Intent intent = new Intent(MainScreen.this, Functions.class);
            startActivity(intent);
        });

        btnSupport.setOnClickListener(view -> {
            Intent intent = new Intent(MainScreen.this, Support.class);
            startActivity(intent);
        });

        btnHistory.setOnClickListener(view -> {
            Intent intent = new Intent(MainScreen.this, History.class);
            startActivity(intent);
        });

        txtDeposits.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, DepositsAndInvestments.class);
            startActivity(intent);
        });

        txtLoans.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, Credits.class);
            startActivity(intent);
        });

        txtSecurity.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, Security.class);
            intent.putExtra("index", strInd);
            startActivity(intent);
        });

        currency1.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, Currency.class);
            startActivity(intent);
        });

        currency2.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, Currency.class);
            startActivity(intent);
        });

        ivCurrency.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, Currency.class);
            startActivity(intent);
        });

        ivWeather.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, Weather.class);
            startActivity(intent);
        });

        miniHist.setOnClickListener(view -> {
            Intent intent = new Intent(MainScreen.this, MiniHistories.class);
            startActivity(intent);
        });

        cursor.close();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestart() {
        mDBHelper = new DBHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        Cursor cursor = mDb.rawQuery("SELECT * FROM Accounts", null);
        cursor.move(index + 1);

        String balance1 = String.valueOf(cursor.getDouble(13)),
                balance2 = String.valueOf(cursor.getDouble(14));

        tvBalance1.setText((balance1.length() > 3 ?
                (balance1.contains(".") ?
                        balance1.substring(0, balance1.length() - 5) + " "
                                + balance1.substring(balance1.length() - 5) :
                        balance1.substring(0, balance1.length() - 3) + " "
                                + balance1.substring(balance1.length() - 3)) : balance1) + " ₽");

        tvBalance2.setText((balance2.length() > 3 ?
                (balance2.contains(".") ?
                        balance2.substring(0, balance2.length() - 5) + " "
                                + balance2.substring(balance2.length() - 5) :
                        balance2.substring(0, balance2.length() - 3) + " "
                                + balance2.substring(balance2.length() - 3)) : balance2) + " ₽");

        super.onRestart();
    }

    private class getURLForCurrency extends AsyncTask<String, String, String>{

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
                currency1.setText("1 USD = " + BigDecimal.valueOf(obj.getJSONObject("rates").getDouble("RUB")).setScale(2, RoundingMode.HALF_UP) + " RUB");
                currency2.setText("1 EUR = " + BigDecimal.valueOf(obj.getJSONObject("rates").getDouble("RUB") / obj.getJSONObject("rates").getDouble("EUR")).setScale(2, RoundingMode.HALF_UP).doubleValue() + " RUB");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getURLForWeather extends AsyncTask<String, String, String> {

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
                if(obj.getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("scattered clouds") || obj.getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("broken clouds") || obj.getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("overcast clouds"))
                    ivWeatherIcon.setBackgroundResource(R.drawable.cloudly);
                else if(obj.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                    ivWeatherIcon.setBackgroundResource(R.drawable.rainy);
                else if(obj.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                    ivWeatherIcon.setBackgroundResource(R.drawable.sunny);
                else if(obj.getJSONArray("weather").getJSONObject(0).getString("description").toLowerCase().contains("few clouds"))
                    ivWeatherIcon.setBackgroundResource(R.drawable.partly_cloudly);
                else if(obj.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("fog"))
                    ivWeatherIcon.setBackgroundResource(R.drawable.fog);
                tvWeather.setText(((int)obj.getJSONObject("main").getDouble("temp") + "°"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}