package com.project.frame.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.project.frame.adapter.DrawerViewPager;
import com.project.frame.common.CommonTools;


public class DrawerMainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private LinearLayout mLinearLayout;
    private NavigationView mNavigation;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;

//    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
//    private ListView mDrawerList;

    private String[] datas;
    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_drawer_main);

        initGui();
        initData();
        initAction();
    }

    protected void initGui(){
        mLinearLayout = (LinearLayout) findViewById(R.id.mLinearLayout);
        //将ToolBar下移Status个高度
        CommonTools.setLayoutPaddingTop(this, mLinearLayout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawer_layout);

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.mTabs);

        if(mToolbar != null){
            // 设置标题
            mToolbar.setTitle("Title");
//            mToolbar.setSubtitle("Sub Title");
            // 将ToolBar设置为ActionBar
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.drawable.icon_drawer);
//            mToolbar.setOnMenuItemClickListener(onMenuItem);

            getSupportActionBar().setHomeButtonEnabled(true);// 设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.mSliding_tabs);
        mViewPager = (ViewPager) findViewById(R.id.mViewpager);
//        mDrawerList = (ListView) findViewById(R.id.mNav_drawer);
        mNavigation = (NavigationView) findViewById(R.id.navigation_view);
    }

    protected void initData(){
        datas = new String[] {"DEFAULT", "RED", "BLUE", "GRAY"};
//        titles = new String[] {"Tab 1", "Tab 2", "Tab 3", "Tab 4", "Tab 5", "Tab 6", "Tab 7", "Tab 8"};
    }

    protected void initAction(){
        mTabLayout.addTab(mTabLayout.newTab().setText("热点"));
        mTabLayout.addTab(mTabLayout.newTab().setText("要闻"));
        mTabLayout.addTab(mTabLayout.newTab().setText("大事"));
        mTabLayout.addTab(mTabLayout.newTab().setText("文化"));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab1));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab2));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab3));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab4));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab5));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab6));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab7));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab8));


        // 创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.tool_bar, R.string.tool_bar) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mNavigation.setNavigationItemSelectedListener(new MyOnItemClick());

        mViewPager.setAdapter(new DrawerViewPager(getSupportFragmentManager(), mTabLayout.getTabCount()));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        mSlidingTabLayout.setViewPager(mViewPager);
//        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////                colorChange(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

//        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//
//            @Override
//            public int getIndicatorColor(int position) {
//                return Color.WHITE;
//            }
//        });

//        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, datas);
//        mDrawerList.setAdapter(mAdapter);
//        mDrawerList.setOnItemClickListener(new MyOnItemClick());
    }

//    private Toolbar.OnMenuItemClickListener onMenuItem = new Toolbar.OnMenuItemClickListener() {
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_edit:
//                    Toast.makeText(DrawerMainActivity.this, "Click edit", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.action_share:
//                    Toast.makeText(DrawerMainActivity.this, "Click share", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.action_settings:
//                    Toast.makeText(DrawerMainActivity.this, "Click settings", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//            return true;
//        }
//    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

//        switch (item.getItemId()) {
////            case R.id.action_edit:
//            case R.id.drawer_home:
//                Toast.makeText(DrawerMainActivity.this, "Click edit", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.drawer_fav:
//                Toast.makeText(DrawerMainActivity.this, "Click share", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.drawer_seeting:
//                Toast.makeText(DrawerMainActivity.this, "Click settings", Toast.LENGTH_SHORT).show();
//                break;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 抽提菜点击事件
     */
    private class MyOnItemClick implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.drawer_home:
//                    Toast.makeText(DrawerMainActivity.this, "Click edit", Toast.LENGTH_SHORT).show();
                    Snackbar.make(mDrawerLayout, "Click_home", Snackbar.LENGTH_SHORT).show();
                    setColor(R.color.colorPrimary_200);
                    break;
                case R.id.drawer_fav:
//                    Toast.makeText(DrawerMainActivity.this, "Click share", Toast.LENGTH_SHORT).show();
                    setColor(R.color.red);
                    break;
                case R.id.drawer_seeting:
//                    Toast.makeText(DrawerMainActivity.this, "Click settings", Toast.LENGTH_SHORT).show();
                    setColor(R.color.blue);
                    break;
            }
            return true;
        }
    }

    /**
     * 为每个Tab添加监听器
     */
    private class MyOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    private void setColor(int color){
        mNavigation.setBackgroundColor(getResources().getColor(color));
        mToolbar.setBackgroundColor(getResources().getColor(color));
        mTabLayout.setBackgroundColor(getResources().getColor(color));
//        mSlidingTabLayout.setBackgroundColor(getResources().getColor(color));
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        if(Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(color));
            window.setNavigationBarColor(getResources().getColor(color));
        }
    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        drawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }


}
