package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Services extends AppCompatActivity {

    TextView tvBetween, tvMobileNet, tvCommunalServices, tvTransport, tvEducation, tvTaxes, tvHealth, tvRecreation;
    EditText sep0, sep1, sep2, sep3, sep4, sep5, sep6, phoneOrCardNumber;
    Button doTransfer;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        tvBetween = findViewById(R.id.between);
        tvMobileNet = findViewById(R.id.mobileNet);
        tvCommunalServices = findViewById(R.id.communalServices);
        tvTransport = findViewById(R.id.transport);
        tvEducation = findViewById(R.id.education);
        tvTaxes = findViewById(R.id.taxes);
        tvHealth = findViewById(R.id.health);
        tvRecreation = findViewById(R.id.recreation);
        phoneOrCardNumber = findViewById(R.id.phoneOrCardNumber);

        sep0 = findViewById(R.id.separator0);
        sep1 = findViewById(R.id.separator1);
        sep2 = findViewById(R.id.separator2);
        sep3 = findViewById(R.id.separator3);
        sep4 = findViewById(R.id.separator4);
        sep5 = findViewById(R.id.separator5);
        sep6 = findViewById(R.id.separator6);

        doTransfer = findViewById(R.id.doTransfer);

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
        String strInd, card, pin, balance;

        if(arguments != null){
            strInd = arguments.get("index").toString();
            card = arguments.get("card").toString();
        }
        else{
            strInd = "0";
            card = "card1";
        }

        int index = Integer.parseInt(strInd);

        Cursor cursor = mDb.rawQuery("SELECT * FROM Accounts", null);
        cursor.move(index + 1);

        if(card.equals("card1")){
            pin = cursor.getString(9);
            balance = String.valueOf(cursor.getDouble(13));
        }
        else{
            pin = cursor.getString(10);
            balance = String.valueOf(cursor.getDouble(14));
        }

        cursor.moveToFirst();



        String[] cards1 = new String[3];
        String[] cards2 = new String[3];
        String[] phNumbers = new String[3];

        int n = 3, i = 0;

        while (!cursor.isAfterLast()) {
            if(i != Integer.parseInt(strInd)){
                cards1[i] = cursor.getString(6);
                cards2[i] = cursor.getString(7);
                phNumbers[i] = cursor.getString(8);
            }
            cursor.moveToNext();
            i++;
        }
        cursor.close();

        cursor.close();



        tvBetween.setOnClickListener(v -> {

        });

        sep0.setOnClickListener(v -> {

        });

        tvMobileNet.setOnClickListener(v -> {

        });

        sep1.setOnClickListener(v -> {

        });

        tvCommunalServices.setOnClickListener(v -> {

        });

        sep2.setOnClickListener(v -> {

        });

        tvTransport.setOnClickListener(v -> {

        });

        sep3.setOnClickListener(v -> {

        });

        tvEducation.setOnClickListener(v -> {

        });

        sep4.setOnClickListener(v -> {

        });

        tvTaxes.setOnClickListener(v -> {

        });

        sep5.setOnClickListener(v -> {

        });

        tvHealth.setOnClickListener(v -> {

        });

        sep6.setOnClickListener(v -> {

        });

        tvRecreation.setOnClickListener(v -> {

        });

        doTransfer.setOnClickListener(v -> {
            if(phoneOrCardNumber.getText().toString().equals(""))
                Toast.makeText(Services.this, "Введите номер телефона или карты", Toast.LENGTH_SHORT).show();
            //else if()
        });

    }
}