package com.example.finale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class Transfer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        String[] items = {"Между своими", "Другому человеку", "Оплата услуг"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0:
                    {
                        Intent intent = new Intent(Transfer.this, BWYours.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:
                    {
                        Intent intent = new Intent(Transfer.this, ToWhom.class);
                        startActivity(intent);
                        break;
                    }
                    case 2:
                    {
                        Intent intent = new Intent(Transfer.this, Services.class);
                        startActivity(intent);
                        break;
                    }
                }

                //Toast.makeText(Transfer.this, "Выбранный: " + which, Toast.LENGTH_SHORT).show();
            }
                });
        Dialog buf = builder.create();
        buf.show();
    }
}