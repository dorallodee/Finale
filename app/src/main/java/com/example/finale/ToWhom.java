package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ToWhom extends AppCompatActivity {

    String[] balances = new String[2];
    int index, getter, from, to;
    TextView tvBalance1,tvCard1, tvCard2, tvBuffer1, tvBuffer2; //tvBalance2
    EditText etMoney;
    Button transfer;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_whom);

        tvBuffer1 = findViewById(R.id.buffer1);
        tvBuffer2 = findViewById(R.id.buffer2);
        tvBalance1 = findViewById(R.id.balance1);
        //tvBalance2 = findViewById(R.id.balance2);
        tvCard1 = findViewById(R.id.cardNum1);
        tvCard2 = findViewById(R.id.cardNum2);

        etMoney = findViewById(R.id.money);
        transfer = findViewById(R.id.transfer);

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
        String strInd = null, value = null, recipient = null, card = null;

        if (arguments != null) {
            strInd = arguments.get("index").toString();
            value = arguments.get("value").toString();
            recipient = arguments.get("recipient").toString();
            card = arguments.get("card").toString();
        }

        index = Integer.parseInt(strInd);
        getter = Integer.parseInt(recipient);

        Cursor cursor = mDb.rawQuery("SELECT * FROM Accounts", null);

        cursor.move(index + 1);

        if (card.equals(cursor.getString(6))){
            balances[0] = cursor.getString(13);
            tvCard1.setText(cursor.getString(6));
            from = 1;
        }

        else if (card.equals(cursor.getString(7))){
            balances[0] = cursor.getString(14);
            tvCard1.setText(cursor.getString(7));
            from = 2;
        }

        tvBalance1.setText((balances[0].length() > 3 ?
                (balances[0].contains(".") ?
                        balances[0].substring(0, balances[0].length() - 5) + " "
                                + balances[0].substring(balances[0].length() - 5) :
                        balances[0].substring(0, balances[0].length() - 3) + " "
                                + balances[0].substring(balances[0].length() - 3)) : balances[0]) + " ₽");

        cursor.close();

        cursor = mDb.rawQuery("SELECT * FROM Accounts", null);
        cursor.move(getter + 1);
        if (value.equals(cursor.getString(6)) || value.equals(cursor.getString(8))){
            balances[1] = cursor.getString(13);
            tvCard2.setText(cursor.getString(6));
            //tvBalance2.setText(cursor.getString(13));
            to = 1;
            Toast.makeText(ToWhom.this, "1st", Toast.LENGTH_SHORT).show();
        }
        else if (value.equals(cursor.getString(7))){
            balances[1] = cursor.getString(14);
            tvCard2.setText(cursor.getString(7));
            //tvBalance2.setText(cursor.getString(14));
            to = 2;
            Toast.makeText(ToWhom.this, "2nd", Toast.LENGTH_SHORT).show();
        }

        transfer.setOnClickListener(v -> {
            double temp1, temp2;
            temp1 = Double.parseDouble(etMoney.getText().toString());
            temp2 = Double.parseDouble(balances[0]);
            Toast.makeText(ToWhom.this, String.valueOf(temp2), Toast.LENGTH_SHORT).show();
            if (temp1 <= temp2) {
                tvBuffer2.setText(String.valueOf(new BigDecimal(Double.parseDouble(balances[1]) + Double.parseDouble(etMoney.getText().toString())).setScale(1, RoundingMode.HALF_UP)));
                tvBuffer1.setText(String.valueOf(new BigDecimal(Double.parseDouble(balances[0]) - Double.parseDouble(etMoney.getText().toString())).setScale(1, RoundingMode.HALF_UP)));
                tvBalance1.setText((tvBuffer1.getText().toString().length() > 3 ?
                        (tvBuffer1.getText().toString().contains(".") ?
                                tvBuffer1.getText().toString().substring(0, tvBuffer1.getText().toString().length() - 5) + " "
                                        + tvBuffer1.getText().toString().substring(tvBuffer1.getText().toString().length() - 5) :
                                tvBuffer1.getText().toString().substring(0, tvBuffer1.getText().toString().length() - 3) + " "
                                        + tvBuffer1.getText().toString().substring(tvBuffer1.getText().toString().length() - 3)) : tvBuffer1.getText().toString()) + " ₽");
                tvBuffer1.setText("");
                tvBuffer2.setText("");
                etMoney.setText("");
            }
        });

        tvBuffer1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!tvBuffer1.getText().toString().equals(""))
                    balances[0] = tvBuffer1.getText().toString();
            }
        });

        tvBuffer2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!tvBuffer2.getText().toString().equals(""))
                    balances[1] = tvBuffer2.getText().toString();
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
        if(from == 1){
            query = "UPDATE Accounts SET balance1 = " + balances[0]  + " WHERE id = " + (index + 1);
        }
        else if (from == 2){
            query = "UPDATE Accounts SET balance2 = " + balances[0]  + " WHERE id = " + (index + 1);
        }
        mDb.execSQL(query);

        if(to == 1){
            query = "UPDATE Accounts SET balance1 = " + balances[1]  + " WHERE id = " + (getter + 1);
        }
        else if (to == 2){
            query = "UPDATE Accounts SET balance2 = " + balances[1]  + " WHERE id = " + (getter + 1);
        }
        mDb.execSQL(query);

        super.onDestroy();
    }
}