package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {
    Button btnFunc, btnSupport, btnHistory;
    TextView txtDeposits, txtLoans, txtSecurity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sreen);
        btnFunc = findViewById(R.id.buttonFunc);
        btnSupport = findViewById(R.id.buttonSupport);
        btnHistory = findViewById(R.id.buttonHistory);

        txtDeposits = findViewById(R.id.depositsAndInvestmentsText);
        txtLoans = findViewById(R.id.loansText);
        txtSecurity = findViewById(R.id.securityText);

        btnFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, Functions.class);
                startActivity(intent);
            }
        });

        btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, Support.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, History.class);
                startActivity(intent);
            }
        });

        txtDeposits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, DepositsAndInvestments.class);
                startActivity(intent);
            }
        });

        txtLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Loans.class);
                startActivity(intent);
            }
        });

        txtSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Security.class);
                startActivity(intent);
            }
        });
    }
}