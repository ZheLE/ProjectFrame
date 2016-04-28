package com.project.frame.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.project.frame.adapter.PhotoWallAdapterDemo;
import com.project.frame.common.Images;

/**
 * Created by lizhe on 2015/5/27.
 */
public class PhotoWallActivity extends Activity {

    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWall;

    /**
     * GridView的适配器
     */
    private PhotoWallAdapterDemo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);

        mPhotoWall = (GridView) findViewById(R.id.photo_wall);
        adapter = new PhotoWallAdapterDemo(this, 0, Images.imageThumbUrls, mPhotoWall);
        mPhotoWall.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
        adapter.cancelAllTasks();
    }

}