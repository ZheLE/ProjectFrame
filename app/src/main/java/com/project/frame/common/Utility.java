package com.project.frame.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: Utility
 * @Description: 工具类
 * @Author XuChao
 * @ModifyDate:2014/09/25
 * @Date 2014年8月1日 下午2:34:03
 * 
 */
public class Utility {

	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	/**
	 * 
	 * 获取手机的ip地址
	 * @return
	 */
	public static String getLocalIpAddress() {
//		try {
//			for (Enumeration<NetworkInterface> en = NetworkInterface
//					.getNetworkInterfaces(); en.hasMoreElements();) {
//				NetworkInterface intf = en.nextElement();
//				for (Enumeration<InetAddress> enumIpAddr = intf
//						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//					InetAddress inetAddress = enumIpAddr.nextElement();
////					但是在4.0 下 会出现类似fe80::b607:f9ff:fee5:487e的IP地址，  这个是IPV6的地址，我们需要获得是的IPV4的地址，所以要在上诉代码中加一个判断
//					if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
//						return inetAddress.getHostAddress().toString();
//					}
//				}
//			}
//		} catch (SocketException ex) {
//			Log.e("WifiPreference IpAddress", ex.toString());
//		}
		return null;
	}

	/**
	 * 隐藏键盘
	 * 
	 * @param view
	 */
	public static void HideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	/**
	 * 获取App安装包信息
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context) {
		PackageInfo info = null;
		try { 
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		} 
		if(info == null) info = new PackageInfo();
		return info;
	}
	
	
	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	/**
	 * 
	 * @Title: getCurrentTime
	 * @Description: 获取当前时间
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}

	/**
	 * 
	 * @Title: dip2px
	 * @Description: dp转px
	 * @param @param context
	 * @param @param dipValue
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		Log.d("UTIL", scale + "");
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 
	 * @Title: px2dp
	 * @Description: px转dp
	 * @param @param context
	 * @param @param pxValue
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		Log.d("UTIL", scale + "");
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 
	 * @Title: transferLongToDate
	 * @Description: long时间转String
	 * @param @param millSec
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String transferLongToDate(Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date(millSec);
		return sdf.format(date);
	}

	/**
	 * 
	 * @Title: getDeviceIMEI
	 * @Description: 获取设备唯一码
	 * @param @param activity
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getDeviceIMEI(Activity activity) {
		return ((TelephonyManager) activity
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	/**
	 * @MethodName: checkSDCard
	 * @Description:检查设备SD卡是否存在
	 * @return:true(存在) false(不存在)
	 * @Author: yangxd
	 * @CreateDate: 2013/01/09
	 */
	public static boolean checkSDCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	/**
	 * @MethodName: checkSDCard
	 * @Description:检查设备SD卡是否存在
	 * @return:true(存在) false(不存在)
	 * @Author: yangxd
	 * @CreateDate: 2013/01/09
	 */
	public static long getSDFreeSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		// return freeBlocks * blockSize; //单位Byte
		// return (freeBlocks * blockSize)/1024; //单位KB
		return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
	}

	public static long getSDAllSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 获取所有数据块数
		long allBlocks = sf.getBlockCount();
		// 返回SD卡大小
		// return allBlocks * blockSize; //单位Byte
		// return (allBlocks * blockSize)/1024; //单位KB
		return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
	}
	
	/**
	 * 比较时间
	 * @param maxTime
	 * @param mintime
	 * @return
	 */
	
	public static int compareTime(String maxTime, String mintime){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date maxdt = df.parse(maxTime);
            Date mindt = df.parse(mintime);
            if (maxdt.getTime() > mindt.getTime()) {
                return 1;
            } else if (maxdt.getTime() < mindt.getTime()) {
                return -1;
            }else {
                return 0;//等于
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
		return 0;
	}
	
	/**
	 * 加缓存
	 * @return 
	 */
	public static void ListCache(Context context, String list, String fileName){
		FileUtils.write(context, fileName, list);
	}
	
	/**
	 * 读取缓存
	 * @return 
	 */
	public static String readListCache(Context context, String fileName){
		return FileUtils.read(context, fileName);
	}

//	/**
//	 * @MethodName: getCurrentNetType
//	 * @Description:获取网络状态类型
//	 * @return:0(网络异常) 1(wifi) 2(gprs)
//	 * @Author: yangxd
//	 * @CreateDate: 2013/01/09
//	 */
//	public static int getCurrentNetType(Context mContext) {
//		ConnectivityManager connManager = (ConnectivityManager) mContext
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo wifi = connManager
//				.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // wifi
//		NetworkInfo gprs = connManager
//				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // gprs
//		// 判断当前网络状态
//		if (wifi != null && wifi.getState() == State.CONNECTED) { // wifi
//			Log.e("DDD", "Current net type:  WIFI.");
//			return Constant.NETWORK_TYPE_WIFI;
//		} else if (gprs != null && gprs.getState() == State.CONNECTED) { // gprs
//			Log.e("DDD", "Current net type:  MOBILE.");
//			return Constant.NETWOKR_TYPE_MOBILE;
//		} else { // 断网
//			Log.e("DDD", "Current net type:  NONE.");
//			return Constant.NETWORK_TYPE_NONE;
//		}
//	}

	/**
	 * 
	 * @Title: getSDPath
	 * @Description: 获取SD卡绝对路径
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();

	}

	/**
	 * 判断是否是合法email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email))
			return false;
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 判断是否是合法手机号码
	 * 
	 * @param cellphone
	 * @return
	 */
	public static boolean isCellphone(String cellphone) {
		if (null == cellphone || "".equals(cellphone)) {
			return false;
		}
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(cellphone);
		return m.matches();
	}

	/**
	 * 判断身份证号码是否合法
	 * 
	 * @param idNo
	 * @return
	 */
	public static boolean isIdNo(String idNo) {
		if (null == idNo || "".equals(idNo)) {
			return false;
		}
		String regex18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
		String regex15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
		Pattern p18 = Pattern.compile(regex18);
		Pattern p15 = Pattern.compile(regex15);
		Matcher m18 = p18.matcher(idNo);
		Matcher m15 = p15.matcher(idNo);
		return (m18.matches() || m15.matches());
	}

	/**
	 * 获取下周的第一天
	 * 
	 * @return
	 */
	public static Calendar getNextWeekFirstDay() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day != Calendar.SUNDAY) {
			cal.add(Calendar.WEEK_OF_MONTH, 1);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal;
	}
	
}
