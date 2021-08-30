package com.example.gs20119csmlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // 전체 인플레이션

        EditText idText = (EditText) findViewById(R.id.idRegistText);
        EditText pwText = (EditText) findViewById(R.id.pwRegistText);
        EditText nameText = (EditText) findViewById(R.id.nameRegistText);
        EditText ageText = (EditText) findViewById(R.id.ageRegistText);
        Button registerButton = (Button) findViewById(R.id.confirmRegist); // 레이아웃 요소 불러오기

        registerButton.setOnClickListener((view) -> {
            Intent menuIntent = new Intent(RegisterActivity.this, menuActivity.class);
            RegisterActivity.this.startActivity(menuIntent);
            // 인텐트를 사용하여 액티비티를 전환
        });
    }
}