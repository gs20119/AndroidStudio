package com.example.gs20119csmlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 전체 인플레이션

        TextView idText = (TextView) findViewById(R.id.idShowText);
        TextView pwText = (TextView) findViewById(R.id.pwShowText);
        TextView Msg = (TextView) findViewById(R.id.welcomeMsg);
    }
}