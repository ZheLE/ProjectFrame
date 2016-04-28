package com.project.frame.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/*
 * 谷歌官方认为，ViewPager应该和Fragment一起使用时，此时ViewPager的适配器是FragmentPagerAdapter，
 * 当你实现一个FragmentPagerAdapter,你必须至少覆盖以下方法:

 * getCount()

 * getItem()
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> list;
	
	public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
