package com.example.gs20119pager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    class MyPagerAdapter extends FragmentStateAdapter{
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
        setContentView(R.layout.activity_main);

        ViewPager2 pager = findViewById(R.id.pager);
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setOffscreenPageLimit(3);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setAdapter(adapter);

        Button button = findViewById(R.id.button);
        button.setOnClickListener((view) -> {
            pager.setCurrentItem(1);
        });
    }

}