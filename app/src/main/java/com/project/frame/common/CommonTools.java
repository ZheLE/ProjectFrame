package com.project.frame.common;

import android.content.Context;
import android.os.Build;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonTools {

	/**
	 * 短暂显示Toast消息
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShortToast(Context context, int message) {
//		LayoutInflater inflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View view = inflater.inflate(R.layout.custom_toast, null);
//		TextView text = (TextView) view.findViewById(R.id.toast_message);
//		text.setText(context.getResources().getString(message));
//		Toast toast = new Toast(context);
//		toast.setDuration(Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.BOTTOM, 0, 300);
//		toast.setView(view);
//		toast.show();
	}

	/**
	 * 根据手机分辨率从dp转成px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	/**
	 * 设置界面布局
	 * @param context
	 * @param view
	 */
	public static void setLayoutPaddingTop(Context context, View view){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
			view.setPadding(0, CommonTools.getStatusBarHeight(context), 0, 0);
		}
	}

	/**
	 * 获取手机状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	
	/**
	 * 判断手机号码*/
	public static boolean isMobileNO(String mobiles){
		
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher matcher = pattern.matcher(mobiles);  
		
		return matcher.matches();
		
	}
	
	
}
