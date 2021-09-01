package com.example.basicintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button = findViewById(R.id.backButton);
        button.setOnClickListener((view) -> {
            Intent intent = new Intent(); // 새로운 전령 생성
            intent.putExtra("name", "mike"); // 전령이 (name = "mike") 데이터를 전달
            setResult(RESULT_OK, intent); // 반환값을 RESULT_OK로 설정
            finish(); // 액티비티 종료 (이전 활동으로 돌아감)
        });
    }
}