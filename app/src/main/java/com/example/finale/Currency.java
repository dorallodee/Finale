package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class Currency extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int bufTv;

    Spinner spinner11, spinner12;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        spinner11 = findViewById(R.id.spinner11);
        spinner12 = findViewById(R.id.spinner12);

        tv1 = findViewById(R.id.tvCurrency1);

        String URL = "https://cdn.cur.su/api/cbr.json";

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency,  android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner11.setAdapter(adapter);
        spinner11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Currency.this, spinner11.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                new Currency.getURL().execute(URL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner12.setAdapter(adapter);
        spinner12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Currency.this, spinner12.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                new Currency.getURL().execute(URL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

        @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String cur = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    tv1.setText("1 " + spinner11.getSelectedItem().toString() + " = " + BigDecimal.valueOf( obj.getJSONObject("rates").getDouble(spinner12.getSelectedItem().toString()) / obj.getJSONObject("rates").getDouble(spinner11.getSelectedItem().toString())).setScale(4, RoundingMode.HALF_UP).doubleValue() + spinner12.getSelectedItem().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}