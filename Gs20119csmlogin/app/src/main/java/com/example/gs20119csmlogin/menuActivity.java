package com.example.gs20119csmlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class menuActivity extends AppCompatActivity {
    LinearLayout container; // container = Layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // 여기까지 기본

        container = findViewById(R.id.container);
        Button button = findViewById(R.id.button);
        button.setOnClickListener((view) -> {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.sub1, container, attachTo);
        }); // Layout Inflater 기본 사용법
    }
}