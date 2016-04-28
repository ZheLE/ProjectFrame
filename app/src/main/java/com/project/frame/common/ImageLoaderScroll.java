package com.project.frame.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lizhe on 2015/8/4.
 * 异步加载图片
 * 通过LruCache缓存优化异步加载
 */
public class ImageLoaderScroll {

    private ImageView mImageView;
    private String mUrl;
    private LruCache<String, Bitmap> mCaches; //实现缓存  设置key指定缓存图片名字

    public ImageLoaderScroll( ){
        int maxMemory = (int) Runtime.getRuntime().maxMemory(); //获取最大可用内存
        int cacheSizes = maxMemory/4; //可用缓存大小

        //初始化lruCache
        mCaches = new LruCache<String, Bitmap>(cacheSizes) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount(); //每次存入缓存时的调用 告知系统当前缓存大小
            }
        };
    }

    /**
     * 增加Bitmap到缓存LruCache
     * @param url
     * @param bitmap
     */
    public void addBitmapToCache(String url, Bitmap bitmap){
        if(getBitmapFromCache(url) == null){
            mCaches.put(url, bitmap);
        }
    }

    //从LruCache中获取Bitmap
    public Bitmap getBitmapFromCache(String url){
        return mCaches.get(url);
    }

    /**
     * 更新UI
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //当Tag相同时更新主界面图片
            Log.d("TAG", "mImageView.getTag()--" + mImageView.getTag());

            if(mImageView.getTag().equals(mUrl)){
                mImageView.setImageBitmap((Bitmap)msg.obj);
            }
        }
    };

    /**
     * 异步加载图片 通过Handler更新界面
     * @param imageView
     * @param url
     */
    public void showImageByThread(ImageView imageView, final String url){
        mImageView = imageView;
        mUrl = url;

        new Thread() {
            @Override
            public void run() {
                super.run();

                Bitmap bitmapCa = getBitmapFromCache(url); //从缓存中取出图片

                if(bitmapCa == null){
                    Bitmap bitmap = getBitmapByUrl(url); //网络下载
                    if(bitmap != null){
                        addBitmapToCache(url, bitmap); //加入缓存

                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);
                    }
                } else if(bitmapCa != null){
                    Message msg = Message.obtain();
                    msg.obj = bitmapCa;
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * AsyncTask 对外公开的方法
     * @param imageView
     * @param url
     */
    public void showImageByAsyncTask(ImageView imageView, String url){
        Bitmap bitmap = getBitmapFromCache(url); //缓存中取图片

        if(bitmap == null){ //如果缓存中无此图片则进行下载
            new ImageAsyncTask(imageView, url).execute(url);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 通过AsyncTask从网络加载图片并更新到界面
     */
    private class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView mImageView;
        private String mUrl;

        public ImageAsyncTask(ImageView imageView, String url){
            mImageView = imageView;
            mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = getBitmapByUrl(params[0]); //网络获取图片
            if(bitmap != null){
                addBitmapToCache(params[0], bitmap); //图片添加到内存
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(mImageView.getTag().equals(mUrl)){ //解决缓存所带来的异步加载产生的显示错误Bug
                mImageView.setImageBitmap(bitmap); //设置图片
            }
        }
    }

    /**
     * 从网络加载图片
     * @param imgUrl
     * @return
     */
    public Bitmap getBitmapByUrl(String imgUrl){

        Bitmap bitmap;
        InputStream is = null;

        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();

//            Thread.sleep(1000);

            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
