package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Security extends AppCompatActivity {
    int index;

    TextView tvName, tvOldPassword, tvNewPassword, tvChange;
    EditText etOldPassword, etNewPassword;
    Button btnChange;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        tvName = findViewById(R.id.name);
        tvOldPassword = findViewById(R.id.labelOldPassword);
        tvNewPassword = findViewById(R.id.labelNewPassword);
        tvChange = findViewById(R.id.change);

        etOldPassword = findViewById(R.id.oldPassword);
        etNewPassword = findViewById(R.id.newPassword);

        btnChange = findViewById(R.id.btnChange);

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

        Bundle args = getIntent().getExtras();
        String strInd = null;
        if(args != null){
            strInd = args.getString("index");
        }
        index = Integer.parseInt(strInd);

        Cursor cursor = mDb.rawQuery("SELECT * FROM Accounts", null);
        cursor.move(index + 1);

        tvName.setText(cursor.getString(3) + " " + cursor.getString(1) + " " + cursor.getString(2));

        String oldPassword = cursor.getString(5);

        btnChange.setOnClickListener(v -> {
            if(oldPassword.equals(etOldPassword.getText().toString()) && !etNewPassword.getText().toString().equals("")) {
                Toast.makeText(this, "Вы успешно поменяли свой пароль", Toast.LENGTH_SHORT).show();
                String query = "UPDATE Accounts SET password = '" + etNewPassword.getText().toString() + "' WHERE id = " + (index + 1);
                mDb.execSQL(query);
            }
            else if(etOldPassword.getText().toString().equals("")){
                Toast.makeText(this, "Введите текущий пароль", Toast.LENGTH_SHORT).show();
            }
            else if(!oldPassword.equals(etOldPassword.getText().toString())){
                Toast.makeText(this, "Неправильно введен текущий пароль", Toast.LENGTH_SHORT).show();
            }
            else if(etNewPassword.getText().toString().equals("")) {
                Toast.makeText(this, "Введите новый пароль", Toast.LENGTH_SHORT).show();
            }
        });
    }
}