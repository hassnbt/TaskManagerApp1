package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class viewpageradapter extends FragmentStateAdapter {


    public viewpageradapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch (position)
       {

           case 0:
               return new calender();
           case 1:
               return new clock();

           case 2:
               return  new notification();
           case 3:
               return  new profile();
           default:
               return  new calender();

       }
    }
}
