package com.example.finale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Card1 extends AppCompatActivity {

    TextView tvCardNum, tvFullName, tvBalance;
    Button transfer, topUp, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card1);

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

        transfer.setOnClickListener(v -> {
            Intent intent = new Intent(Card1.this, Services.class);
            intent.putExtra("index", strInd);
            intent.putExtra("card", "card1");
            startActivity(intent);

            /*

            ДИАЛОГОВОЕ ОКНО С ВЫБОРОМ, ОТ КОТОРОГО, СЧИТАЮ, СТОИТ ОТКАЗАТЬСЯ В ПОЛЬЗУ РАЗМЕЩЕНИЯ ВСЕГО,
            ЧТО СВЯЗАНО С ПЛАТЕЖАМИ, НА ОДНОЙ СТРАНИЦЕ, ПОСКОЛЬКУ И ТАК МНОГО activities

            String[] items = {"Между своими", "Другому человеку", "Оплата услуг"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("").setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case 0:
                        {
                            Intent intent = new Intent(Card1.this, Transfer.class);
                            startActivity(intent);
                            break;
                        }
                        case 1:
                        {
                            Intent intent = new Intent(Card1.this, ToWhom.class);
                            startActivity(intent);
                            break;
                        }
                        case 2:
                        {
                            Intent intent = new Intent(Card1.this, Services.class);
                            startActivity(intent);
                            break;
                        }
                    }

                    //Toast.makeText(Transfer.this, "Выбранный: " + which, Toast.LENGTH_SHORT).show();
                }
            });
            Dialog buf = builder.create();
            buf.show();
            */

            /*
            Intent intent = new Intent(Card1.this, Transfer.class);
            intent.putExtra("index", strInd);
            startActivity(intent);
            */
        });
    }
}