package com.example.gs20119csmlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // 전체 인플레이션

        EditText idText = (EditText) findViewById(R.id.idText);
        EditText pwText = (EditText) findViewById(R.id.pwText);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        TextView registerButton = (TextView) findViewById(R.id.registerButton); // 레이아웃 요소 불러오기

        registerButton.setOnClickListener((view) -> { // registerButton + onClick(누르면)
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            LoginActivity.this.startActivity(registerIntent);
            // RegisterActivity(회원가입과정) 시작 / 인텐트는 나중에 더 언급
        });

    }
}