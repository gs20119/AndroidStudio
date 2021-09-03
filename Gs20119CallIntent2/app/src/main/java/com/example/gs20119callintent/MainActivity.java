package com.example.gs20119callintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 전체 인플레이션

        TextView textView = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener((view) -> {
            String data = textView.getText().toString(); // 'editText' 에서 문자열 읽어 'data' 에 저장
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
            startActivity(intent);
        });

    }
}