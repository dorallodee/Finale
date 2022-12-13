package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Currency extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    double[] currency = new double[9];
    double smt;

    String buf;
    Spinner spinner11, spinner12, spinner21, spinner22;
    TextView
            tv1, tv2;
    EditText ed1;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDot, btnDelete, btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        String URL = "https://cdn.cur.su/api/cbr.json";


        currency[0] = 42.14;
        currency[1] = 25.23;
        currency[2] = 66.27;
        currency[3] = 77.17;
        currency[4] = 0.46;
        currency[5] = 14.15;
        currency[6] = 1;
        currency[7] = 1.7;
        currency[8] = 62.77;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency,  android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ed1 = findViewById(R.id.value1);

        spinner21 = findViewById(R.id.spinner21);
        spinner22 = findViewById(R.id.spinner22);

        spinner21.setAdapter(adapter);
        spinner22.setAdapter(adapter);

        tv2 = findViewById(R.id.tvCurrency2);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);
        btnDot = findViewById(R.id.btnDot);
        btnDelete = findViewById(R.id.btnBackspace);

        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //new Currency.getURL().execute(URL);
                if(!ed1.getText().toString().isEmpty()){
                    if (ed1.getText().toString().equals("."))
                        return;
                    else if(ed1.getText().toString().toCharArray()[ed1.getText().toString().trim().length() - 1] == '.' && !ed1.getText().toString().equals("\\.")) {
                        String buf = ed1.getText().toString().substring(0, ed1.getText().toString().length() - 1);
                        tv2.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(buf) * currency[spinner21.getSelectedItemPosition()] / currency[spinner22.getSelectedItemPosition()]).setScale(2, RoundingMode.HALF_UP)));
                    }
                    else
                        tv2.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(ed1.getText().toString()) * currency[spinner21.getSelectedItemPosition()] / currency[spinner22.getSelectedItemPosition()]).setScale(2, RoundingMode.HALF_UP)));
                }
                else{
                    tv2.setText(String.valueOf(BigDecimal.valueOf(currency[spinner21.getSelectedItemPosition()] / currency[spinner22.getSelectedItemPosition()]).setScale(2, RoundingMode.HALF_UP)));
                }
            }
        });

        spinner21.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //new Currency.getURL().execute(URL);

                if(!ed1.getText().toString().isEmpty()){
                    if (ed1.getText().toString().equals("."))
                        return;
                    else if(ed1.getText().toString().toCharArray()[ed1.getText().toString().trim().length() - 1] == '.' && !ed1.getText().toString().equals("\\.")) {
                        String buf = ed1.getText().toString().substring(0, ed1.getText().toString().length() - 1);
                        tv2.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(buf) * currency[spinner21.getSelectedItemPosition()] / currency[spinner22.getSelectedItemPosition()]).setScale(2, RoundingMode.HALF_UP)));
                    }
                    else
                        tv2.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(ed1.getText().toString()) * currency[spinner21.getSelectedItemPosition()] / currency[spinner22.getSelectedItemPosition()]).setScale(2, RoundingMode.HALF_UP)));
                }
                else{
                    tv2.setText(String.valueOf(BigDecimal.valueOf(currency[spinner21.getSelectedItemPosition()] / currency[spinner22.getSelectedItemPosition()]).setScale(2, RoundingMode.HALF_UP)));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner22.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //new Currency.getURL().execute(URL);

                if(!ed1.getText().toString().isEmpty()){
                    if (ed1.getText().toString().equals("."))
                        return;
                    else if(ed1.getText().toString().toCharArray()[ed1.getText().toString().trim().length() - 1] == '.' && !ed1.getText().toString().equals("\\.")) {
                        String buf = ed1.getText().toString().substring(0, ed1.getText().toString().length() - 1);
                        tv2.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(buf) * currency[spinner21.getSelectedItemPosition()] / currency[spinner22.getSelectedItemPosition()]).setScale(2, RoundingMode.HALF_UP)));
                    }
                    else
                        tv2.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(ed1.getText().toString()) * currency[spinner21.getSelectedItemPosition()] / currency[spinner22.getSelectedItemPosition()]).setScale(2, RoundingMode.HALF_UP)));
                }
                else{
                    tv2.setText(String.valueOf(BigDecimal.valueOf(currency[spinner21.getSelectedItemPosition()] / currency[spinner22.getSelectedItemPosition()]).setScale(2, RoundingMode.HALF_UP)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                buf += "1";
                ed1.setText(buf);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                buf += "2";
                ed1.setText(buf);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                buf += "3";
                ed1.setText(buf);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                buf += "4";
                ed1.setText(buf);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                buf += "5";
                ed1.setText(buf);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                buf += "6";
                ed1.setText(buf);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                buf += "7";
                ed1.setText(buf);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                buf += "8";
                ed1.setText(buf);
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                buf += "9";
                ed1.setText(buf);
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                buf += "0";
                ed1.setText(buf);
            }
        });

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                if(buf.contains("."))
                    return;
                buf += ".";
                ed1.setText(buf);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buf = ed1.getText().toString();
                if(!buf.isEmpty())
                    ed1.setText(buf.substring(0, buf.length() - 1));
                else
                    return;
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
            //JSONObject obj = new JSONObject(result);
            if(!ed1.getText().toString().isEmpty()){
                if(!ed1.getText().toString().trim().isEmpty() && ed1.getText().toString().trim().toCharArray()[ed1.getText().toString().trim().length() - 1] == '.')
                    if(ed1.getText().toString().trim().isEmpty()){
                        tv2.setText(String.valueOf(currency[spinner22.getSelectedItemPosition()] / currency[spinner21.getSelectedItemPosition()]));
                        //tv2.setText(BigDecimal.valueOf(obj.getJSONObject("rates").getDouble(spinner22.getSelectedItem().toString()) / obj.getJSONObject("rates").getDouble(spinner21.getSelectedItem().toString())).setScale(2, RoundingMode.HALF_UP).toString());
                    }
                tv2.setText(String.valueOf(Double.parseDouble(ed1.getText().toString()) * currency[spinner22.getSelectedItemPosition()] / currency[spinner21.getSelectedItemPosition()]));
                //tv2.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(ed1.getText().toString()) * obj.getJSONObject("rates").getDouble(spinner22.getSelectedItem().toString()) / obj.getJSONObject("rates").getDouble(spinner21.getSelectedItem().toString())).setScale(4, RoundingMode.HALF_UP)));
            }
            else
                tv2.setText("213000");
            //tv2.setText(String.valueOf(BigDecimal.valueOf(obj.getJSONObject("rates").getDouble(spinner22.getSelectedItem().toString()) / obj.getJSONObject("rates").getDouble(spinner21.getSelectedItem().toString())).setScale(2, RoundingMode.HALF_UP)));
        }
    }
}