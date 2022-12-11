package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BWYours extends AppCompatActivity {
    int index;
    String card;

    String[] cards = new String[2];
    String[] balances = new String[2];

    TextView tvCard1, tvCard2, tvBalance1, tvBalance2, tvBuffer1, tvBuffer2;
    EditText etMoney;
    Button transfer;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> tabConnection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bw_yours);

        tvCard1 = findViewById(R.id.cardNum1);
        tvCard2 = findViewById(R.id.cardNum2);
        tvBalance1 = findViewById(R.id.balance1);
        tvBalance2 = findViewById(R.id.balance2);
        tvBuffer1 = findViewById(R.id.buffer1);
        tvBuffer2 = findViewById(R.id.buffer2);

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

        String strInd = null;
        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            strInd = arguments.get("index").toString();
            card = arguments.get("card").toString();
        }

        index = Integer.parseInt(strInd);

        Cursor cursor = mDb.rawQuery("SELECT * FROM Accounts", null);
        cursor.move(index + 1);

        cards[0] = cursor.getString(6);
        cards[1] = cursor.getString(7);

        balances[0] = String.valueOf(cursor.getDouble(13));
        balances[1] = String.valueOf(cursor.getDouble(14));

        if (card.equals(cards[0])){
            tvCard1.setText("···· " + cards[0].substring(cards[0].length() - 4));
            tvBalance1.setText((balances[0].length() > 3 ?
                    (balances[0].contains(".") ?
                            balances[0].substring(0, balances[0].length() - 5) + " "
                                    + balances[0].substring(balances[0].length() - 5) :
                            balances[0].substring(0, balances[0].length() - 3) + " "
                                    + balances[0].substring(balances[0].length() - 3)) : balances[0]) + " ₽");
            tvCard2.setText("···· " + cards[1].substring(cards[1].length() - 4));
            tvBalance2.setText((balances[1].length() > 3 ?
                    (balances[1].contains(".") ?
                            balances[1].substring(0, balances[1].length() - 5) + " "
                                    + balances[1].substring(balances[1].length() - 5) :
                            balances[1].substring(0, balances[1].length() - 3) + " "
                                    + balances[1].substring(balances[1].length() - 3)) : balances[1]) + " ₽");
        }
        else if(card.equals(cards[1])){
            tvCard1.setText("···· " + cards[1].substring(cards[1].length() - 4));
            tvBalance1.setText((balances[1].length() > 3 ?
                    (balances[1].contains(".") ?
                            balances[1].substring(0, balances[1].length() - 5) + " "
                                    + balances[1].substring(balances[1].length() - 5) :
                            balances[1].substring(0, balances[1].length() - 3) + " "
                                    + balances[1].substring(balances[1].length() - 3)) : balances[1]) + " ₽");
            tvCard2.setText("···· " + cards[0].substring(cards[0].length() - 4));
            tvBalance2.setText((balances[0].length() > 3 ?
                    (balances[0].contains(".") ?
                            balances[0].substring(0, balances[0].length() - 5) + " "
                                    + balances[0].substring(balances[0].length() - 5) :
                            balances[0].substring(0, balances[0].length() - 3) + " "
                                    + balances[0].substring(balances[0].length() - 3)) : balances[0]) + " ₽");
        }


        createGroupList();
        createCollection(cards);
        expandableListView = findViewById(R.id.elvMobile);
        expandableListAdapter = new MyExpandableListAdapter(this, groupList, tabConnection);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener((expandableListView, view, i, i1, l) -> {
            String selected = expandableListAdapter.getChild(i, i1).toString();
            Toast.makeText(getApplicationContext(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
            if (selected.equals(cards[0])) {
                tvCard1.setText("···· " + cards[0].substring(cards[0].length() - 4));
                tvBalance1.setText((balances[0].length() > 3 ?
                        (balances[0].contains(".") ?
                                balances[0].substring(0, balances[0].length() - 5) + " "
                                        + balances[0].substring(balances[0].length() - 5) :
                                balances[0].substring(0, balances[0].length() - 3) + " "
                                        + balances[0].substring(balances[0].length() - 3)) : balances[0]) + " ₽");
                tvCard2.setText("···· " + cards[1].substring(cards[1].length() - 4));
                tvBalance2.setText((balances[1].length() > 3 ?
                        (balances[1].contains(".") ?
                                balances[1].substring(0, balances[1].length() - 5) + " "
                                        + balances[1].substring(balances[1].length() - 5) :
                                balances[1].substring(0, balances[1].length() - 3) + " "
                                        + balances[1].substring(balances[1].length() - 3)) : balances[1]) + " ₽");
            } else if (selected.equals(cards[1])) {
                tvCard1.setText("···· " + cards[1].substring(cards[1].length() - 4));
                tvBalance1.setText((balances[1].length() > 3 ?
                        (balances[1].contains(".") ?
                                balances[1].substring(0, balances[1].length() - 5) + " "
                                        + balances[1].substring(balances[1].length() - 5) :
                                balances[1].substring(0, balances[1].length() - 3) + " "
                                        + balances[1].substring(balances[1].length() - 3)) : balances[1]) + " ₽");
                tvCard2.setText("···· " + cards[0].substring(cards[0].length() - 4));
                tvBalance2.setText((balances[0].length() > 3 ?
                        (balances[0].contains(".") ?
                                balances[0].substring(0, balances[0].length() - 5) + " "
                                        + balances[0].substring(balances[0].length() - 5) :
                                balances[0].substring(0, balances[0].length() - 3) + " "
                                        + balances[0].substring(balances[0].length() - 3)) : balances[0]) + " ₽");
            }
            return true;
        });

        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        transfer.setOnClickListener(v -> {
            if (tvCard1.getText().toString().contains(cards[0].substring(cards[0].length() - 4))) {
                if(Double.parseDouble(etMoney.getText().toString()) <= Double.parseDouble(balances[0])) {
                    tvBuffer2.setText(String.valueOf(new BigDecimal(Double.parseDouble(balances[1]) + Double.parseDouble(etMoney.getText().toString())).setScale(1, RoundingMode.HALF_UP)));
                    tvBalance2.setText((tvBuffer2.getText().toString().length() > 3 ?
                            (tvBuffer2.getText().toString().contains(".") ?
                                    tvBuffer2.getText().toString().substring(0, tvBuffer2.getText().toString().length() - 5) + " "
                                            + tvBuffer2.getText().toString().substring(tvBuffer2.getText().toString().length() - 5) :
                                    tvBuffer2.getText().toString().substring(0, tvBuffer2.getText().toString().length() - 3) + " "
                                            + tvBuffer2.getText().toString().substring(tvBuffer2.getText().toString().length() - 3)) : tvBuffer2.getText().toString()) + " ₽");
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
            }
            else if (tvCard1.getText().toString().contains(cards[1].substring(cards[1].length() - 4))) {
                if(Double.parseDouble(etMoney.getText().toString()) <= Double.parseDouble(balances[1])) {
                    tvBuffer1.setText(String.valueOf(new BigDecimal(Double.parseDouble(balances[0]) + Double.parseDouble(etMoney.getText().toString())).setScale(1, RoundingMode.HALF_UP)));
                    tvBalance2.setText((tvBuffer1.getText().toString().length() > 3 ?
                            (tvBuffer1.getText().toString().contains(".") ?
                                    tvBuffer1.getText().toString().substring(0, tvBuffer1.getText().toString().length() - 5) + " "
                                            + tvBuffer1.getText().toString().substring(tvBuffer1.getText().toString().length() - 5) :
                                    tvBuffer1.getText().toString().substring(0, tvBuffer1.getText().toString().length() - 3) + " "
                                            + tvBuffer1.getText().toString().substring(tvBuffer1.getText().toString().length() - 3)) : tvBuffer1.getText().toString()) + " ₽");
                    tvBuffer2.setText(String.valueOf(new BigDecimal(Double.parseDouble(balances[1]) - Double.parseDouble(etMoney.getText().toString())).setScale(1, RoundingMode.HALF_UP)));
                    tvBalance1.setText((tvBuffer2.getText().toString().length() > 3 ?
                            (tvBuffer2.getText().toString().contains(".") ?
                                    tvBuffer2.getText().toString().substring(0, tvBuffer2.getText().toString().length() - 5) + " "
                                            + tvBuffer2.getText().toString().substring(tvBuffer2.getText().toString().length() - 5) :
                                    tvBuffer2.getText().toString().substring(0, tvBuffer2.getText().toString().length() - 3) + " "
                                            + tvBuffer2.getText().toString().substring(tvBuffer2.getText().toString().length() - 3)) : tvBuffer2.getText().toString()) + " ₽");
                    tvBuffer1.setText("");
                    tvBuffer2.setText("");
                    etMoney.setText("");
                }
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

        tvCard1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cursor.close();
    }

    private void createCollection(String[] cards) {
        String[] from = {cards[0], cards[1]};
        tabConnection = new HashMap<String, List<String>>();
        for(String group: groupList)
        {
            if(group.equals("Откуда"))
                loadChild(from);
            tabConnection.put(group, childList);
        }
    }

    private void loadChild(String[] tabs) {
        childList = new ArrayList<>();
        for(String tab: tabs)
            childList.add(tab);
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Откуда");
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

        String query = "UPDATE Accounts SET balance1 =" + balances[0] + ", balance2 = " + balances[1] + " WHERE id = " + (index + 1);
        mDb.execSQL(query);
        super.onDestroy();
    }
}