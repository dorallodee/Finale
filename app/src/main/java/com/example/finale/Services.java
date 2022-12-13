package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Services extends AppCompatActivity {

    TextView tvBetween, tvMobileNet, tvCommunalServices, tvTransport, tvEducation, tvTaxes, tvHealth, tvRecreation, tv7;
    EditText phoneOrCardNumber;
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
            card = cursor.getString(6);
            balance = String.valueOf(cursor.getDouble(13));
        }
        else{
            card = cursor.getString(7);
            balance = String.valueOf(cursor.getDouble(14));
        }
        String CARD = card;
        cursor.moveToFirst();

        String[] cards1 = new String[3];
        String[] cards2 = new String[3];
        String[] phNumbers = new String[3];
        String[] names = new String[3];

        int n = 3, i = 0;

        while (!cursor.isAfterLast()) {
            if(i != Integer.parseInt(strInd)){
                cards1[i] = cursor.getString(6);
                cards2[i] = cursor.getString(7);
                phNumbers[i] = cursor.getString(8);
                names[i] = cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3);
            }
            cursor.moveToNext();
            i++;
        }

        tvBetween.setOnClickListener(v -> {
            Intent intent = new Intent(Services.this, BWYours.class);
            intent.putExtra("index", strInd);
            intent.putExtra("card", CARD);
            startActivity(intent);
        });

        tvMobileNet.setOnClickListener(v -> {
            Intent intent = new Intent(Services.this, Transfer.class);
            intent.putExtra("index", strInd);
            intent.putExtra("card", CARD);
            intent.putExtra("service", "mobile");
            startActivity(intent);
        });

        tvCommunalServices.setOnClickListener(v -> {
            Intent intent = new Intent(Services.this, Transfer.class);
            intent.putExtra("index", strInd);
            intent.putExtra("card", CARD);
            intent.putExtra("service", "communal");
            startActivity(intent);
        });

        tvTransport.setOnClickListener(v -> {
            Intent intent = new Intent(Services.this, Transfer.class);
            intent.putExtra("index", strInd);
            intent.putExtra("card", CARD);
            intent.putExtra("service", "transport");
            startActivity(intent);
        });

        tvEducation.setOnClickListener(v -> {
            Intent intent = new Intent(Services.this, Transfer.class);
            intent.putExtra("index", strInd);
            intent.putExtra("card", CARD);
            intent.putExtra("service", "education");
            startActivity(intent);
        });

        tvTaxes.setOnClickListener(v -> {
            Intent intent = new Intent(Services.this, Transfer.class);
            intent.putExtra("index", strInd);
            intent.putExtra("card", CARD);
            intent.putExtra("service", "taxes");
            startActivity(intent);
        });

        tvHealth.setOnClickListener(v -> {
            Intent intent = new Intent(Services.this, Transfer.class);
            intent.putExtra("index", strInd);
            intent.putExtra("card", CARD);
            intent.putExtra("service", "health");
            startActivity(intent);
        });

        tvRecreation.setOnClickListener(v -> {
            Intent intent = new Intent(Services.this, Transfer.class);
            intent.putExtra("index", strInd);
            intent.putExtra("card", CARD);
            intent.putExtra("service", "recreation");
            startActivity(intent);
        });

        phoneOrCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(phoneOrCardNumber.getText().toString().length() > 10) {
                    tv7.setVisibility(View.INVISIBLE);
                }
                else {
                    tv7.setVisibility(View.VISIBLE);
                }

                if(phoneOrCardNumber.getText().toString().length() == 10 || (phoneOrCardNumber.getText().toString().length() == 16)) {
                    doTransfer.setVisibility(View.VISIBLE);
                }
                else {
                    doTransfer.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        doTransfer.setOnClickListener(v -> {
            if(phoneOrCardNumber.getText().toString().equals("")) {
                Toast.makeText(Services.this, "Введите номер телефона или карты", Toast.LENGTH_SHORT).show();
            }
            else {
                if(((phoneOrCardNumber.getText().toString().equals(cards1[0]) || phoneOrCardNumber.getText().toString().equals(cards1[1]) ||
                        phoneOrCardNumber.getText().toString().equals(cards1[2]) || phoneOrCardNumber.getText().toString().equals(cards2[0]) ||
                        phoneOrCardNumber.getText().toString().equals(cards2[1]) || phoneOrCardNumber.getText().toString().equals(cards2[2])) &&
                        !phoneOrCardNumber.getText().toString().equals(CARD)) ||
                        ((phoneOrCardNumber.getText().toString().equals(phNumbers[0]) || phoneOrCardNumber.getText().toString().equals(phNumbers[1]) ||
                        phoneOrCardNumber.getText().toString().equals(phNumbers[2])) && !phoneOrCardNumber.getText().toString().equals(phNumbers[index])))
                {
                    Intent intent = new Intent(Services.this, ToWhom.class);
                    if (phoneOrCardNumber.getText().toString().equals(cards1[0]) || phoneOrCardNumber.getText().toString().equals(cards2[0]) ||
                            phoneOrCardNumber.getText().toString().equals(phNumbers[0]))
                    {
                        //Toast.makeText(Services.this, "Переведено на " + phoneOrCardNumber.getText().toString() + " на имя " + names[0], Toast.LENGTH_SHORT).show();
                        intent.putExtra("recipient", "0");
                    }
                    else if (phoneOrCardNumber.getText().toString().equals(cards1[1]) || phoneOrCardNumber.getText().toString().equals(cards2[1]) ||
                            phoneOrCardNumber.getText().toString().equals(phNumbers[1]))
                    {
                        //Toast.makeText(Services.this, "Переведено на " + phoneOrCardNumber.getText().toString() + " на имя " + names[1], Toast.LENGTH_SHORT).show();
                        intent.putExtra("recipient", "1");
                    }
                    else if (phoneOrCardNumber.getText().toString().equals(cards1[2]) || phoneOrCardNumber.getText().toString().equals(cards2[2]) ||
                            phoneOrCardNumber.getText().toString().equals(phNumbers[2]))
                    {
                        //Toast.makeText(Services.this, "Переведено на " + phoneOrCardNumber.getText().toString() + " на имя " + names[2], Toast.LENGTH_SHORT).show();
                        intent.putExtra("recipient", "2");
                    }
                    intent.putExtra("card", CARD);
                    intent.putExtra("index", strInd);
                    intent.putExtra("value", phoneOrCardNumber.getText().toString());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Services.this, phoneOrCardNumber.getText().toString() + " не найдено", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}