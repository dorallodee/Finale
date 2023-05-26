package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Card1 extends AppCompatActivity {
    int index;
    String card;

    TextView tvCardNum, tvFullName, tvBalance;
    Button transfer, topUp, info;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card1);

        tvCardNum = findViewById(R.id.cardNum);
        tvFullName = findViewById(R.id.fullName);
        tvBalance = findViewById(R.id.balance);

        transfer = findViewById(R.id.transfer);


        Bundle arguments = getIntent().getExtras();
        String strInd, name, patronymic, surname;

        if (arguments != null) {
            strInd = arguments.get("index").toString();
            name = arguments.get("name").toString();
            patronymic = arguments.get("patronymic").toString();
            surname = arguments.get("surname").toString();
            card = arguments.get("card").toString();
        } else {
            strInd = "0";
            name = "0";
            patronymic = "0";
            surname = "0";
            card = "0";
        }

        index = Integer.parseInt(strInd);

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
        String balance = String.valueOf(cursor.getDouble(13));

        tvCardNum.setText(card.substring(0, 4) + " " + card.substring(4, 8) + " " + card.substring(8, 12) + " " + card.substring(12));

        tvFullName.setText(name + " " + patronymic + " " + surname);

        tvBalance.setText((balance.length() > 3 ?
                (balance.contains(".") ?
                        balance.substring(0, balance.length() - 5) + " "
                                + balance.substring(balance.length() - 5) :
                        balance.substring(0, balance.length() - 3) + " "
                                + balance.substring(balance.length() - 3)) : balance) + " ₽");

        transfer.setOnClickListener(v -> {
            Intent intent = new Intent(Card1.this, Services.class);
            intent.putExtra("index", strInd);
            intent.putExtra("card", "card1");
            startActivity(intent);
        });
    }

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

        String balance = String.valueOf(cursor.getDouble(13));

        tvBalance.setText((balance.length() > 3 ?
                (balance.contains(".") ?
                        balance.substring(0, balance.length() - 5) + " "
                                + balance.substring(balance.length() - 5) :
                        balance.substring(0, balance.length() - 3) + " "
                                + balance.substring(balance.length() - 3)) : balance) + " ₽");

        super.onRestart();
    }
}