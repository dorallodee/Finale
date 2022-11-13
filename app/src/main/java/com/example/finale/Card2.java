package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Card2 extends AppCompatActivity {
    TextView tvCardNum, tvFullName, tvBalance;
    Button transfer, topUp, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card2);

        tvCardNum = findViewById(R.id.cardNum);
        tvFullName = findViewById(R.id.fullName);
        tvBalance = findViewById(R.id.balance);

        transfer = findViewById(R.id.transfer);
        topUp = findViewById(R.id.topUp);
        info = findViewById(R.id.info);


        Bundle arguments = getIntent().getExtras();
        String strInd, name, patronymic, surname, card, balance;

        if(arguments != null){
            strInd = arguments.get("index").toString();
            name = arguments.get("name").toString();
            patronymic = arguments.get("patronymic").toString();
            surname = arguments.get("surname").toString();
            card = arguments.get("card").toString();
            balance = arguments.get("balance").toString();
        }
        else{
            strInd = "0";
            name = "0";
            patronymic = "0";
            surname = "0";
            card = "0";
            balance = "0";
        }

        tvCardNum.setText(card);

        tvFullName.setText(name + " " + patronymic + " " + surname);

        tvBalance.setText((balance.length() > 3 ?
                (balance.contains(".") ?
                        balance.substring(0, balance.length() - 5) + " "
                                + balance.substring(balance.length() - 5) :
                        balance.substring(0, balance.length() - 3) + " "
                                + balance.substring(balance.length() - 3)) : balance) + " ₽");
    }
}