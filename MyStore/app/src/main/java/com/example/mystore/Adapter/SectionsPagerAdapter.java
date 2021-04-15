package com.example.mystore.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mystore.Fragment.AccountFragment;
import com.example.mystore.Fragment.HomeFragment;
import com.example.mystore.Fragment.LatopFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int position) {
        // position + 1 vì position bắt đầu từ số 0.
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new HomeFragment();
                break;
            case 1:
                frag = new LatopFragment();
                break;
            case 2:
                frag = new AccountFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";

        }
        return null;
    }
}
