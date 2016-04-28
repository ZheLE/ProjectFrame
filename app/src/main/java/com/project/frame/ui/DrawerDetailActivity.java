package com.project.frame.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.frame.bean.VenuesInfo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.tsz.afinal.FinalBitmap;

public class DrawerDetailActivity extends AppCompatActivity {

    private static final String EXTRA_IMG = "extraImage";
    private static final String EXTRA_TITLE = "extraTitle";

    private LinearLayout mLinearLayout;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mImageView;
    private Toolbar mToolbar;
    private TextView mTextView;
    private android.support.design.widget.FloatingActionButton mFloatButton;
    private FinalBitmap mFinalBitmap;

    private String imgUrl;
    private String imgTitle;

    /**
     * 启动当前页
     *
     * @param activity
     * @param transitionImage
     * @param venue
     */
    public static void navigate(AppCompatActivity activity, View transitionImage, VenuesInfo venue) {
        Intent intent = new Intent(activity, DrawerDetailActivity.class);
        intent.putExtra(EXTRA_IMG, venue.getTitle_img());
        intent.putExtra(EXTRA_TITLE, venue.getTitle());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMG);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

        super.onCreate(savedInstanceState);
        initActivity();

        setContentView(R.layout.activity_drawer_detail);

//        if (mFinalBitmap == null) {
//            mFinalBitmap = HttpTools.getInstence().getFinalBitmap(DrawerDetailActivity.this);
//        }

        initGui();
        initData();
    }

    private void initGui() {
        mLinearLayout = (LinearLayout) findViewById(R.id.mLinearLayout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.mAppBarLayout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.mCollapsingToolbar);
        mImageView = (ImageView) findViewById(R.id.mImageView);
        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mTextView = (TextView) findViewById(R.id.mTitle);
        mFloatButton = (android.support.design.widget.FloatingActionButton) findViewById(R.id.mFloatButton);

        ViewCompat.setTransitionName(mAppBarLayout, EXTRA_IMG);
        supportPostponeEnterTransition();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        imgUrl = getIntent().getStringExtra(EXTRA_IMG);
        imgTitle = getIntent().getStringExtra(EXTRA_TITLE);

        mCollapsingToolbarLayout.setTitle(imgTitle);
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

//        mFinalBitmap.display(mImageView, imgUrl);
        mTextView.setText(imgTitle);

        Picasso.with(this).load(imgUrl).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap mBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                Palette.from(mBitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide tranisiton = new Slide(); //slide(滑动) - 视图从场景的一个边缘进入或者退出
            tranisiton.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(tranisiton); //当A start B时，使B中的View进入场景的transition
            getWindow().setReturnTransition(tranisiton); //当B 返回 A时，使B中的View退出场景的transition
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
        int primary = getResources().getColor(R.color.colorPrimary_200);

        mLinearLayout.setBackgroundColor(palette.getMutedColor(primary));
        mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(primaryDark));

        updateBackground(mFloatButton, palette);
        supportPostponeEnterTransition();
    }

    private void updateBackground(android.support.design.widget.FloatingActionButton fab, Palette palette){
        int lightColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.colorPrimary_200));

        fab.setRippleColor(lightColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }
}
