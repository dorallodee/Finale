package com.example.finale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Transfer extends AppCompatActivity {
    int index, which;
    String balance;

    TextView tvService, tvAddress, tvCard, tvBalance, tvBuffer;
    EditText etMoney, etNumber;
    Button btnPay;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        tvService = findViewById(R.id.payFor);
        tvAddress = findViewById(R.id.address);
        tvCard = findViewById(R.id.cardNum);
        tvBalance = findViewById(R.id.balance);
        tvBuffer = findViewById(R.id.buffer);

        etMoney = findViewById(R.id.money);
        etNumber = findViewById(R.id.number);

        btnPay = findViewById(R.id.pay);

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
        String card = null, strInd = null, service = null;

        if(arguments != null){
            strInd = arguments.getString("index");
            card = arguments.getString("card");
            service = arguments.getString("service");
        }

        index = Integer.parseInt(strInd);

        Cursor cursor = mDb.rawQuery("SELECT * FROM Accounts", null);
        cursor.move(index + 1);

        tvCard.setText("···· " + card.substring(card.length() - 4));
        if(card.equals(cursor.getString(6))){
            balance = cursor.getString(13);
            which = 1;
        }
        else if(card.equals(cursor.getString(7))){
            balance = cursor.getString(14);
            which = 2;
        }

        tvBalance.setText((balance.length() > 3 ?
                (balance.contains(".") ?
                        balance.substring(0, balance.length() - 5) + " "
                                + balance.substring(balance.length() - 5) :
                        balance.substring(0, balance.length() - 3) + " "
                                + balance.substring(balance.length() - 3)) : balance) + " ₽");

        InputFilter[] FilterArray = new InputFilter[1];
        int length = 20, Length;

        switch (service)
        {
            case "mobile":
            {
                tvService.setText("Оплата мобильной связи");
                tvAddress.setText("Номер телефона (без +7)");
                length = 10;
                FilterArray[0] = new InputFilter.LengthFilter(length);
                etNumber.setFilters(FilterArray);
                break;
            }
            case "communal":
            {
                tvService.setText("Оплата ЖКХ");
                tvAddress.setText("ИНН");
                length = 12;
                FilterArray[0] = new InputFilter.LengthFilter(length);
                etNumber.setFilters(FilterArray);
                break;
            }
            case "transport":
            {
                tvService.setText("Оплата транспорта");
                tvAddress.setText("Номер транспортной карты");
                length = 19;
                FilterArray[0] = new InputFilter.LengthFilter(length);
                etNumber.setFilters(FilterArray);
                break;
            }
            case "education":
            {
                tvService.setText("Оплата образования");
                tvAddress.setText("Номер телефона (без +7)");
                length = 10;
                FilterArray[0] = new InputFilter.LengthFilter(length);
                etNumber.setFilters(FilterArray);
                break;
            }
            case "taxes":
            {
                tvService.setText("Оплата налогов"); // ИНН
                tvAddress.setText("ИНН");
                length = 12;
                FilterArray[0] = new InputFilter.LengthFilter(length);
                etNumber.setFilters(FilterArray);
                break;
            }
            case "health":
            {
                tvService.setText("Оплата здоровья");
                tvAddress.setText("Номер полиса ОМС");
                length = 15;
                FilterArray[0] = new InputFilter.LengthFilter(length);
                etNumber.setFilters(FilterArray);
                break;
            }
            case "recreation":
            {
                tvService.setText("Оплата отдыха и развлечений");
                tvAddress.setText("Номер телефона (без +7)");
                length = 10;
                FilterArray[0] = new InputFilter.LengthFilter(length);
                etNumber.setFilters(FilterArray);
                break;
            }
        }

        Length = length;

        btnPay.setOnClickListener(v -> {
            if (!etMoney.getText().toString().trim().equals("") && Double.parseDouble(etMoney.getText().toString()) <= Double.parseDouble(balance)){
                tvBuffer.setText(String.valueOf(Double.parseDouble(balance) - Double.parseDouble(etMoney.getText().toString())));
                tvBalance.setText((balance.length() > 3 ?
                        (balance.contains(".") ?
                                balance.substring(0, balance.length() - 5) + " "
                                        + balance.substring(balance.length() - 5) :
                                balance.substring(0, balance.length() - 3) + " "
                                        + balance.substring(balance.length() - 3)) : balance.toString()) + " ₽");
                Toast.makeText(this, "Оплачено", Toast.LENGTH_SHORT).show();
            }
            else if (etNumber.getText().toString().equals(""))
                Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show();
            else if (etNumber.getText().toString().length() != Length)
                Toast.makeText(this, "Введите данные полностью", Toast.LENGTH_SHORT).show();
            else if (etMoney.getText().toString().trim().equals(""))
                Toast.makeText(this, "Введите сумму", Toast.LENGTH_SHORT).show();
            else if (Double.parseDouble(etMoney.getText().toString()) > Double.parseDouble(balance))
                Toast.makeText(this, "Недостаточно средств", Toast.LENGTH_SHORT).show();
        });

        tvBuffer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                balance = tvBuffer.getText().toString();
            }
        });
    }

    @Override
    protected void onDestroy() {
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

        String query = null;

        switch (which)
        {
            case 1:
            {
                query = "UPDATE Accounts SET balance1 = " + balance  + " WHERE id = " + (index + 1);
                break;
            }
            case 2:
            {
                query = "UPDATE Accounts SET balance2 = " + balance  + " WHERE id = " + (index + 1);
                break;
            }
        }
        mDb.execSQL(query);

        super.onDestroy();
    }
}