package com.example.finale;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.finale.databinding.ActivityCreditsBinding;

public class Credits extends AppCompatActivity {

    //String nameCredit = "неймкредит", aboutCredit = "о кредите";
    //private ActivityCreditsBinding binding;
    //Object[] object = {nameCredit, aboutCredit};
    String[] array = {"Кредит 1\nКредитная ставка: 10% годовых\nОсталось выплатить: 123 700 РУБ\n", "Кредит 2\nКредитная ставка: 9.8% годовых\nОсталось выплатить: 12 300 РУБ"};
    String[] array2 = {"Прайм-кредит\nКредитная ставка: 8.9%\nСрок погашения: 1-3 года\nСумма: до 250 000 РУБ", "Со сниженной ставкой\nКредитная ставка: 5.1%\nСрок погашения: до 24 месяцев\nСумма: до 170 000 РУБ", "Потребительский\nКредитная ставка: 6.9%\nСрок погашения: 12 месяцев\nСумма: до 60 000 РУБ", "На развитие бизнеса\nКредитная ставка: 10%\nСрок погашения: до 6 лет\nСумма: до 1 000 000 РУБ"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_credits);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, array);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter<Object>(this, R.layout.activity_listview2, array2);

        ListView listView2 = (ListView)findViewById(R.id.list2);
        listView2.setAdapter(adapter2);

        /*
        binding = ActivityCreditsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());
        */
    }
}