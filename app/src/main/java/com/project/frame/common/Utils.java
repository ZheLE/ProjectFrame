package com.project.frame.common;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 *
 */
public class Utils
{

	public static int widthPixels;
	public static int heightPixels;

	public static int dip(Context context, int pixels)
	{
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		float scale = displayMetrics.density;
		System.out.println(displayMetrics.density);
		System.out.println(displayMetrics.densityDpi);
		System.out.println(displayMetrics.xdpi);
		System.out.println(displayMetrics.ydpi);

		widthPixels = displayMetrics.widthPixels;
		heightPixels = displayMetrics.heightPixels;
		System.out.println(displayMetrics.widthPixels);
		System.out.println(displayMetrics.heightPixels);

		return (int) (pixels * scale + 0.5f);

	}

	/**
	 * ���设置ListView高度�߶�
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 启动页
	 * @param context
	 * @param activity
	 */
	public static void startActivity(Context context, Class<?> activity){
		Intent intent = new Intent();
		intent.setClass(context, activity);
		context.startActivity(intent);
	}
}
