package com.example.gs20119service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 전체 인플레이션

        EditText editText = findViewById(R.id.editText); // 필요한 요소 불러오기
        Button button = findViewById(R.id.button);

        button.setOnClickListener((view) -> {
            String name = editText.getText().toString(); // 문자열 받아오기 (꿀팁)
            Intent intent = new Intent(this, MyService.class); // 발신/수신 지정
            intent.putExtra("command", "show");
            intent.putExtra("name", name); // 신호에 들어가는 내용 추가하기
            startService(intent); // 신호 보내기 (서비스시작)
        });

        Intent passedIntent = getIntent();
        processIntent(passedIntent);
    }

    @Override
    protected void onNewIntent(Intent intent){ // Service 에서 신호 돌아오면
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent){
        if(intent != null){
            String command = intent.getStringExtra("command"); // 신호에 들어있는 내용을 getExtra로 받음
            String name = intent.getStringExtra("name");
            Toast.makeText(this, "command : " + command + ", name : " + name, Toast.LENGTH_LONG).show();
        }
    }
}