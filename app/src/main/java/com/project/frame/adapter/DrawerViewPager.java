package com.project.frame.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.frame.fragment.SampleFragment;
import com.project.frame.fragment.ScrollLoadAfinalFragment;
import com.project.frame.fragment.ScrollLoadLruCacheFragment;
import com.project.frame.fragment.ScrollNoLoadFragment;


/**
 * Created by lizhe on 2015/7/15.
 */
public class DrawerViewPager extends FragmentPagerAdapter {

//    private String[] titles;
    private int mCount;

    public DrawerViewPager(FragmentManager fm, int count) {
        super(fm);
        this.mCount = count;
//        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ScrollLoadAfinalFragment.newInstance(position);
            case 1:
                return ScrollLoadLruCacheFragment.newInstance(position);
            case 2:
                return ScrollNoLoadFragment.newInstance(position);
            case 3:
                return SampleFragment.newInstance(position);
            case 4:
                return SampleFragment.newInstance(position);
            case 5:
                return SampleFragment.newInstance(position);
            case 6:
                return SampleFragment.newInstance(position);
            case 7:
                return SampleFragment.newInstance(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mCount;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles[position];
//    }

//    @Override
//    public int getCount() {
////        return titles.length;
//    }
}
