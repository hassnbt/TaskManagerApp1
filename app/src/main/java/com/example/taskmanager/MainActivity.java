package com.example.taskmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout; // ✅ CORRECT

import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

     TabLayout tabLayout;
    ViewPager2 viewPager2;
    viewpageradapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();


        new TabLayoutMediator(
                tabLayout,
                viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {

                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position)
                        {
                            case 0:
                                tab.setText("Calender");
                                BadgeDrawable badge = tab.getOrCreateBadge();
                                badge.setNumber(10);
                                badge.setMaxCharacterCount(2);
                                tab.setIcon(R.drawable.calendar);
                                break;
                            case 1:
                                tab.setText("Clock");
                                BadgeDrawable badge1 = tab.getOrCreateBadge();
                                badge1.setNumber(100);
                                badge1.setMaxCharacterCount(3);
                                tab.setIcon(R.drawable.clock);
                                break;
                            case 2:
                                tab.setText("Notification");
                                BadgeDrawable badge2 = tab.getOrCreateBadge();
                                badge2.setNumber(55);
                                tab.setIcon(R.drawable.bell);
                                break;

                            case 3:
                                tab.setText("Notification");
                                BadgeDrawable badge3 = tab.getOrCreateBadge();
                                badge3.setNumber(155);
                                tab.setIcon(R.drawable.user);
                                break;

                        }
                    }
                }
        ).attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badge = tabLayout.getTabAt(position).getOrCreateBadge();
                badge.setVisible(false);
                badge.setNumber(0);
            }
        });

    }


    private void init()
    {

//tabLayout=findViewById(R.id.tabLayout);
        tabLayout=findViewById(R.id.tabLayout);
viewPager2=findViewById(R.id.viewPager2);
adapter= new viewpageradapter(this);

viewPager2.setAdapter(adapter);

    }
}