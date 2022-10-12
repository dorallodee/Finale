package com.example.finale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MiniHistories extends AppCompatActivity implements View.OnTouchListener {

    ImageView iv;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv = new ImageView(this);
        iv.setOnTouchListener(this); // для обработки касаний
        setContentView(R.layout.activity_mini_histories);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { // нажатие
                try {
                    Thread.sleep(10000); // тут нужно будет подобрать функцию для задержки экрана
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }


        /*try {
            Thread.sleep(5000); // тут нужно будет подобрать функцию для задержки экрана
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return false;
    }
}