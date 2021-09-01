package com.example.basicintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 101; // 지령코드 101
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 전체 인플레이션

        Button button = (Button) findViewById(R.id.menuButton); // 레이아웃 요소 불러오기

        button.setOnClickListener((view) -> { // 'button' 클릭했을 때
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class); // MenuActivity 목적지로 하는 전령 생성
            startActivityForResult(intent, REQUEST_CODE_MENU); // 전령이 목적지(Activity)를 실행시키고, 반환값을 기다림
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}