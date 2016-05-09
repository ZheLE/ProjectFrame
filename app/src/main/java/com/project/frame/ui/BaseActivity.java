package com.project.frame.ui;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.project.frame.common.ProgressUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by lizhe on 2015/5/26.
 *
 * 创建一个RequestQueue对象。
 * 创建一个StringRequest对象。
 * 将StringRequest对象添加到RequestQueue里面
 *
 */
public class BaseActivity extends FragmentActivity {

    private RequestQueue mQueue;

    private NetworkImageView mNetworkImageView;
    private ImageView mImageView;

    private String imageUrl = "http://avatar.csdn.net/6/6/D/1_lfdfhl.jpg";
    private String getUrl = "http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_base);

        mQueue = Volley.newRequestQueue(getApplicationContext());

//        mQueue = MyApplication.getInstance().getRequestQueue();

        initView();

        jsonObjectRequestGet(getUrl, null, new RequestCallBack() {
            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        loadImageByVolley(imageUrl, mImageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        showImageByVolley(imageUrl);
    }

    private void initView(){
        mImageView = (ImageView)findViewById(R.id.imageView);
        mNetworkImageView = (NetworkImageView) findViewById(R.id.networkImageView);
    }

    /**
     * 使用Volley发送GET请求
     * @param url 请求路径
     * @param callBack
     */
    public void jsonObjectRequestGet(String url, HashMap<String, String> parmas, final RequestCallBack callBack){

//        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());

        ProgressUtils.ShowProgressDialog(BaseActivity.this);

        JsonObjectRequest mJsonObjectRequestGet = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("TAG", jsonObject.toString());
                        callBack.onResponse(jsonObject);

                        //加载成功进度条消失
                        ProgressUtils.CloseProgressDialog();
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        callBack.onErrorResponse(volleyError);
                    }
                });

        //设置超时时间
        mJsonObjectRequestGet.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0f));
        //请求加入列队
        mQueue.add(mJsonObjectRequestGet);
        //开始发起请求
        mQueue.start();
    }

    /**
     * 使用Volley发送POST请求
     *
     * @param url
     * @param callBack
     */
    public void jsonObjectRequestPost(String url, HashMap<String, String> parmas, final RequestCallBack callBack){

//        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());

        ProgressUtils.ShowProgressDialog(BaseActivity.this);

        JsonObjectRequest mJsonObjectRequestPost = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(parmas),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("TAG", jsonObject.toString());
                        callBack.onResponse(jsonObject);

                        //加载成功进度条消失
                        ProgressUtils.CloseProgressDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("TAG", volleyError.getMessage().toString());
                        callBack.onErrorResponse(volleyError);
                    }
                });

        //设置超时时间
        mJsonObjectRequestPost.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0f));
        //请求加入列队
        mQueue.add(mJsonObjectRequestPost);
        //开始发起请求
        mQueue.start();
    }

    /**
     * 重写 服务器响应成功的回调
     * 服务器响应的失败回调
     */
    public interface RequestCallBack{
        public void onResponse(JSONObject response);
        public void onErrorResponse(VolleyError volleyError);
    }

    /**
     * 使用Volley异步加载网络图片
     *
     * 创建一个RequestQueue对象。
     * 创建一个ImageLoader对象。
     * 获取一个ImageListener对象。
     * 调用ImageLoader的get()方法加载网络上的图片
     *
     * 方法参数:
     * getImageListener(ImageView view, int defaultImageResId, int errorImageResId)
     * 第一个参数:显示图片的ImageView
     * 第二个参数:默认显示的图片资源
     * 第三个参数:加载错误时显示的图片资源
     */
    public void loadImageByVolley(String imageUrl, ImageView view, int defaultImageResId, int errorImageResId){

//        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);

        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String key) {
                return lruCache.get(key);
            }

            @Override
            public void putBitmap(String key, Bitmap value) {
                lruCache.put(key, value);
            }
        };

        ImageLoader imageLoader = new ImageLoader(mQueue, imageCache);
        ImageLoader.ImageListener listener = ImageLoader.
                getImageListener(mImageView, defaultImageResId, errorImageResId);

        imageLoader.get(imageUrl, listener);
    }

    /**
     * 利用NetworkImageView显示网络图片
     */
    public void showImageByVolley(String imageUrl){

//        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
        //使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;

        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) { //getRowBytes 用于计算位图每一行所占用的内存字节数
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){    //API 19
                    return bitmap.getAllocationByteCount();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
                    return bitmap.getByteCount(); //用于计算位图所占用的内存字节数
                }
                //位图所占用的内存空间数等于位图的每一行所占用的空间数乘以位图的行数
                return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
            }
        };

        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String key) {
                return lruCache.get(key);
            }

            @Override
            public void putBitmap(String key, Bitmap value) {
                lruCache.put(key, value);
            }
        };

        ImageLoader imageLoader = new ImageLoader(mQueue, imageCache);

        mNetworkImageView.setTag("url");
        mNetworkImageView.setImageUrl(imageUrl, imageLoader);
    }
}
