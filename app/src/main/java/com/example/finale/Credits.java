package com.example.finale;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.finale.databinding.ActivityCreditsBinding;

public class Credits extends AppCompatActivity {

    //private ActivityCreditsBinding binding;
    String[] array = {"Кредит 1", "Кредит 2", "Кредит 3"};
    String[] array2 = {"Воьмите этот", "Или этот", "Еще один варик", "Ну или хотя бы этот"};
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

        ArrayAdapter adapter2 = new ArrayAdapter<String>(this, R.layout.activity_listview2, array2);

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