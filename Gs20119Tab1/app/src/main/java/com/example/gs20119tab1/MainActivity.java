package com.example.gs20119tab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 전체 인플레이션

        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3(); // Fragment 사용법 - 객체를 생성

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        TabLayout tabs = findViewById(R.id.tabs); // 탭 레이아웃 편집
        tabs.addTab(tabs.newTab().setText("CALL"));
        tabs.addTab(tabs.newTab().setText("SPAM"));
        tabs.addTab(tabs.newTab().setText("ADDRESS"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if(pos==0) getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                else if(pos==1) getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                else if(pos==2) getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

    }
}