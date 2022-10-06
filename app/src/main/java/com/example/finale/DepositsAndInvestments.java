package com.example.finale;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepositsAndInvestments extends AppCompatActivity {

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> tabConnection; //основные вкладки (вклады и акции)
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposits_and_investments);
        setContentView(R.layout.activity_deposits_and_investments);
        createGroupList();
        createCollection();
        expandableListView = findViewById(R.id.elvMobile);
        expandableListAdapter = new MyExpandableListAdapter(this, groupList, tabConnection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition)
                    expandableListView.collapseGroup(lastExpandedPosition);
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                Toast.makeText(getApplicationContext(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    private void createCollection() {
        String[] deposits = {"Вклад 1", "Вклад 2", "Вклад 3", "Вклад 4", "Вклад 5"};
        String[] investments = {"Tesla", "Роснефть", "Яндекс"};
        tabConnection = new HashMap<String, List<String>>();
        for(String group: groupList)
        {
            if(group.equals("Вклады"))
                loadChild(deposits);
            else if(group.equals("Акции"))
                loadChild(investments);
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
        groupList.add("Вклады");
        groupList.add("Акции");
    }
}