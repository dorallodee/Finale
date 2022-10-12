package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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

public class MainScreen extends AppCompatActivity {
    Button btnFunc, btnSupport, btnHistory;
    TextView txtDeposits, txtLoans, txtSecurity, currency1, currency2, usd, eur, tvWeather;
    ImageView ivWeather, miniHist;
    ImageView dollar, euro;

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
        ivWeather = findViewById(R.id.ivWeather);
        miniHist = findViewById(R.id.miniHistory);

        currency1 = findViewById(R.id.currency1);
        currency2 = findViewById(R.id.currency2);

        usd = findViewById(R.id.USD);
        eur = findViewById(R.id.EUR);

        dollar = findViewById(R.id.Dollar);
        euro = findViewById(R.id.Euro);

        String URLCurrency = "https://cdn.cur.su/api/cbr.json";
        new getURLForCurrency().execute(URLCurrency);

        String key = "771f71c52bc5003a745ac9074cdb920f";
        String URLWeather = "https://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=" + key + "&units=metric";
        new getURLForWeather().execute(URLWeather);

        btnFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, Functions.class);
                startActivity(intent);
            }
        });

        btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, Support.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, History.class);
                startActivity(intent);
            }
        });

        txtDeposits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, DepositsAndInvestments.class);
                startActivity(intent);
            }
        });

        txtLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Loans.class);
                startActivity(intent);
            }
        });

        txtSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Security.class);
                startActivity(intent);
            }
        });

        currency1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Currency.class);
                startActivity(intent);
            }
        });

        currency2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Currency.class);
                startActivity(intent);
            }
        });

        usd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Currency.class);
                startActivity(intent);
            }
        });

        eur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Currency.class);
                startActivity(intent);
            }
        });

        dollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Currency.class);
                startActivity(intent);
            }
        });

        euro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Currency.class);
                startActivity(intent);
            }
        });

        ivWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Weather.class);
                startActivity(intent);
            }
        });

        miniHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, MiniHistories.class);
                startActivity(intent);
            }
        });
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, Home.class);
                startActivity(i);
                finish();
            }
        }, 5*1000);*/
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
                tvWeather.setText(((int)obj.getJSONObject("main").getDouble("temp") + "°"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}