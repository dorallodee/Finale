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

        // Список клиентов
        ArrayList<HashMap<String, Object>> clients = new ArrayList<HashMap<String, Object>>();

        /*
        String[] name = new String[3];
        String[] patronymic = new String[3];
        String[] surname = new String[3];
        */

        String[] logins = new String[3];
        String[] passwords = new String[3];

        /*
        String[] card1 = new String[3];
        String[] card2 = new String[3];
        String[] phoneNum = new String[3];
        String[] pin1 = new String[3];
        String[] pin2 = new String[3];
        String[] cvv1 = new String[3];
        String[] cvv2 = new String[3];
        */
        // Список параметров конкретного клиента
        HashMap<String, Object> client;

        // Отправляем запрос в БД
        Cursor cursor = mDb.rawQuery("SELECT * FROM Accounts", null);
        cursor.moveToFirst();
        int i = 0;
        // Пробегаем по всем клиентам
        while (!cursor.isAfterLast()) {
            logins[i] = cursor.getString(4);
            passwords[i] = cursor.getString(5);
            //client = new HashMap<String, Object>();

            // Заполняем клиента
            //client.put("name",  cursor.getString(4));
            //client.put("age",  cursor.getString(5));

            // Закидываем клиента в список клиентов
            //clients.add(client);

            // Переходим к следующему клиенту
            cursor.moveToNext();
            i++;
        }
        cursor.close();

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.getText().toString().trim().equals(logins[0]) && password.getText().toString().trim().equals(passwords[0]))
                {
                    Toast.makeText(MainActivity.this, "Вы вошли в свой профиль, Гриша", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainScreen.class);
                    intent.putExtra("index", "0");
                    startActivity(intent);
                    Intent i = new Intent(MainActivity.this, StartingScreen.class);
                    startActivity(i);
                }
                else if (login.getText().toString().trim().equals(logins[1]) && password.getText().toString().trim().equals(passwords[1]))
                {
                    Toast.makeText(MainActivity.this, "Вы вошли в свой профиль, Саша", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainScreen.class);
                    intent.putExtra("index", "1");
                    startActivity(intent);
                    Intent i = new Intent(MainActivity.this, StartingScreen.class);
                    startActivity(i);
                }
                else if (login.getText().toString().trim().equals(logins[2]) && password.getText().toString().trim().equals(passwords[2]))
                {
                    Toast.makeText(MainActivity.this, "Вы вошли в свой профиль, Даша", Toast.LENGTH_SHORT).show();
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
}