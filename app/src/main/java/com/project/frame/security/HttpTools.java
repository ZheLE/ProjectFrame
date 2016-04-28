package com.project.frame.security;

import android.content.Context;
import android.util.Log;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

/**
 * Created by lizhe on 2015/7/23.
 */
public class HttpTools {

    public static HttpTools mHttpTools = null;

    public static HttpTools getInstence(){
        if(mHttpTools == null){
            mHttpTools = new HttpTools();
        }
        return mHttpTools;
    }

    public FinalHttp getFinalHttp(){
        FinalHttp mFinalHttp = new FinalHttp();
        mFinalHttp.addHeader("Accept-Charset", "UTF-8");//配置http请求头
        mFinalHttp.configCharset("UTF-8");
//        mFinalHttp.configCookieStore(null);
        mFinalHttp.configRequestExecutionRetryCount(3);//请求错误重试次数
//        mFinalHttp.configSSLSocketFactory(null);
        mFinalHttp.configTimeout(5000);//超时时间
        mFinalHttp.configUserAgent("Mozilla/5.0");//配置客户端信息

        Log.d("TAG", "FinalHttp初始化成功");
        return mFinalHttp;
    }

    /**
     * FinalBitmap模块：通过FinalBitmap，imageview加载bitmap的时候无需考虑bitmap加载过程中出现的oom和android容器快速滑动时候出现的图片错位等现象。
     * FinalBitmap可以配置线程加载线程数量，缓存大小，缓存路径，加载显示动画等。FinalBitmap的内存管理使用lru算法
     * @param context
     * @return
     */
    public FinalBitmap getFinalBitmap(Context context){
        FinalBitmap mFinalBitmap = FinalBitmap.create(context.getApplicationContext()); //初始化
        mFinalBitmap.configBitmapLoadThreadSize(3);
        mFinalBitmap.configDiskCachePath(context.getApplicationContext().getFilesDir().toString()); //设置缓存目录
        mFinalBitmap.configDiskCacheSize(1024 * 1024 * 10); //设置缓存大小
//        mFinalBitmap.configLoadingImage(R.drawable.icon_navig_wo);

        return mFinalBitmap;
    }
}
