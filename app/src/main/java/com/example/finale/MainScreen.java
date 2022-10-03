package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView txtDeposits, txtLoans, txtSecurity, currency1, currency2;

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
        currency1 = findViewById(R.id.currency1);
        currency2 = findViewById(R.id.currency2);

        String URL = "https://cdn.cur.su/api/cbr.json";
        //String URL = "https://currate.ru/api/?get=rates&pairs=USDRUB,EURRUB&key=a13a7960473c386a7658bb47d0723489";
        new getURL().execute(URL);

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
    }

    private class getURL extends AsyncTask<String, String, String>{

        protected void onPreExecute(){
            super.onPreExecute();
            currency1.setText("Maybe You have no internet connection");
            currency2.setText("Maybe You have no internet connection");
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
                currency1.setText("USD - RUB: 1 USD = " + obj.getJSONObject("rates").getDouble("RUB") + " RUB");
                currency2.setText("EUR - RUB: 1 EUR = " + BigDecimal.valueOf(obj.getJSONObject("rates").getDouble("RUB") / obj.getJSONObject("rates").getDouble("EUR")).setScale(4, RoundingMode.HALF_UP).doubleValue() + " RUB");
                //currency1.setText("USD - RUB: 1 USD = " + obj.getJSONObject("data").getDouble("USDRUB") + " RUB");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}