package com.example.finale;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String[] logins = new String[3];
    String[] passwords = new String[3];

    private EditText login, password;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button but1 = findViewById(R.id.button1);
        login = findViewById(R.id.Login);
        password = findViewById(R.id.Password);

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

        /*
        String[] name = new String[3];
        String[] patronymic = new String[3];
        String[] surname = new String[3];
        */



        /*
        String[] card1 = new String[3];
        String[] card2 = new String[3];
        String[] phoneNum = new String[3];
        String[] pin1 = new String[3];
        String[] pin2 = new String[3];
        String[] cvv1 = new String[3];
        String[] cvv2 = new String[3];
        */


        // Отправляем запрос в БД
        Cursor cursor = mDb.rawQuery("SELECT * FROM Accounts", null);
        cursor.moveToFirst();

        int i = 0;
        // Пробегаем по всем клиентам
        while (!cursor.isAfterLast()) {
            logins[i] = cursor.getString(4);
            passwords[i] = cursor.getString(5);
            cursor.moveToNext();
            i++;
        }

        // ЧТОБЫ УЗНАТЬ, КАКИЕ ПАРОЛИ ДЛЯ ГРИШИ, ДАШИ И САШИБ РАСКОММЕНТИТЬ СТРОКУ НИЖЕ -
        // В ТОСТЕ ПРИ ПЕРВОМ ЗАПУСКЕ ПОДРЯД БУДУТ ПАРОЛИ ЧЕРЕЗ ПРОБЕЛ
        Toast.makeText(this, passwords[0] + " " + passwords[1] + " " + passwords[2], Toast.LENGTH_SHORT).show();

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Refresh();
                if (login.getText().toString().trim().equals(logins[0]) && password.getText().toString().trim().equals(passwords[0]))
                {
                    Toast.makeText(MainActivity.this, "Вы вошли в свой профиль", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainScreen.class);
                    intent.putExtra("index", "0");
                    startActivity(intent);
                    Intent i = new Intent(MainActivity.this, StartingScreen.class);
                    startActivity(i);
                }
                else if (login.getText().toString().trim().equals(logins[1]) && password.getText().toString().trim().equals(passwords[1]))
                {
                    Toast.makeText(MainActivity.this, "Вы вошли в свой профиль", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainScreen.class);
                    intent.putExtra("index", "1");
                    startActivity(intent);
                    Intent i = new Intent(MainActivity.this, StartingScreen.class);
                    startActivity(i);
                }
                else if (login.getText().toString().trim().equals(logins[2]) && password.getText().toString().trim().equals(passwords[2]))
                {
                    Toast.makeText(MainActivity.this, "Вы вошли в свой профиль", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainScreen.class);
                    intent.putExtra("index", "2");
                    startActivity(intent);
                    Intent i = new Intent(MainActivity.this, StartingScreen.class);
                    startActivity(i);
                }
                else if (login.getText().toString().trim().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Введите логин", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().trim().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                }
                else if (login.getText().toString().trim().equals("") && password.getText().toString().trim().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
                }
                else if ((!login.getText().toString().trim().equals(logins[0]) && !password.getText().toString().trim().equals(passwords[0])) ||
                        (!login.getText().toString().trim().equals(logins[1]) && !password.getText().toString().trim().equals(passwords[1])) ||
                        (!login.getText().toString().trim().equals(logins[2]) && !password.getText().toString().trim().equals(passwords[2])))
                {
                    Toast.makeText(MainActivity.this, "Данные не найдены", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void Refresh(){
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
        cursor.moveToFirst();

        int i = 0;
        // Пробегаем по всем клиентам
        while (!cursor.isAfterLast()) {
            passwords[i] = cursor.getString(5);
            cursor.moveToNext();
            i++;
        }
    }
}