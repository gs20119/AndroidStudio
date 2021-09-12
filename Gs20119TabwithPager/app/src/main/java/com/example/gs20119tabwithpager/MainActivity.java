package com.example.gs20119tabwithpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.gs20119tabwithpager.Fragment1;
import com.example.gs20119tabwithpager.Fragment2;
import com.example.gs20119tabwithpager.Fragment3;
import com.example.gs20119tabwithpager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    class MyPagerAdapter extends FragmentStateAdapter {
        int itemCount = 3;
        public MyPagerAdapter(FragmentManager fm, Lifecycle cycle){ super(fm,cycle); }

        @Override
        public int getItemCount(){ return itemCount; }
        @NonNull
        @Override
        public Fragment createFragment(int pos){
            if(pos==0) return new Fragment1();
            else if(pos==1) return new Fragment2();
            else if(pos==2) return new Fragment3();
            return new Fragment1();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 전체 인플레이션

        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3(); // Fragment 사용법 - 객체를 생성

        ViewPager2 pager = findViewById(R.id.pager);
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setOffscreenPageLimit(3);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tabs); // 탭 레이아웃 편집
        tabs.addTab(tabs.newTab().setText("CALL"));
        tabs.addTab(tabs.newTab().setText("SPAM"));
        tabs.addTab(tabs.newTab().setText("ADDRESS"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if(pos==0) pager.setCurrentItem(0);
                else if(pos==1) pager.setCurrentItem(1);
                else if(pos==2) pager.setCurrentItem(2);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.tab1:
                        pager.setCurrentItem(0);
                        return true;
                    case R.id.tab2:
                        pager.setCurrentItem(1);
                        return true;
                    case R.id.tab3:
                        pager.setCurrentItem(2);
                        return true;
                }return false;
            }
        });
    }
}