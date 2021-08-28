package com.example.gs20119csmlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class menuActivity extends AppCompatActivity {
    LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // 전체 인플레이션, menu XML 파일 보기

        // XML의 요소를 Activity에 가져오고 싶으면 findView
        container = findViewById(R.id.container); // 전체 레이아웃 (id container)
        Button button = findViewById(R.id.button); // 버튼 (id button)
        button.setOnClickListener((view) -> { // button + onClick
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.sub1, container, true);
            // 부분 인플레이션은 이렇게 inflater을 하나 만들어서 사용함, sub1 XML 파일 붙이기
        });
    }
}