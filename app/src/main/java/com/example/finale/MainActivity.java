package com.example.finale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText login, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button but1 = findViewById(R.id.button1);
        login = findViewById(R.id.Login);
        password = findViewById(R.id.Password);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.getText().toString().trim().equals("login") && password.getText().toString().trim().equals("password")) {
                    Toast.makeText(MainActivity.this, "Вы вошли в свой профиль", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, MainScreen.class);
                    startActivity(intent);
                } else if (login.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, "Введите логин", Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, "Введите пароль", Toast.LENGTH_LONG).show();
                } else if (login.getText().toString().trim().equals("") && password.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, "Введите логин и пароль", Toast.LENGTH_LONG).show();
                } else if (!login.getText().toString().trim().equals("DashaLohushka") || !password.getText().toString().trim().equals("SashaTozhe")) {
                    Toast.makeText(MainActivity.this, "Данные не найдены", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}