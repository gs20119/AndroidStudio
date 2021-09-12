package com.example.gs20119tab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gs20119tab2.Fragment1;
import com.example.gs20119tab2.Fragment2;
import com.example.gs20119tab2.Fragment3;
import com.example.gs20119tab2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.tab1:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                                return true;
                            case R.id.tab2:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                                return true;
                            case R.id.tab3:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();
                                return true;
                        }return false;
                    }
                }
        );

    }
}