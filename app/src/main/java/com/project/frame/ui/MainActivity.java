package com.project.frame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.project.frame.fragment.HomeFragment;
import com.project.frame.fragment.TestFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

	//底部菜单栏
	private TextView tv_home, tv_read, tv_find, tv_me;

	private Fragment mCurrentFragmet;
    private Fragment mHomeFragment;
	private Fragment mTestFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initGui();
		initData();
		initAction();
	}

	protected void initGui(){
		tv_home = (TextView) findViewById(R.id.tv_home);
		tv_read = (TextView) findViewById(R.id.tv_read);
		tv_find = (TextView) findViewById(R.id.tv_find);
		tv_me = (TextView) findViewById(R.id.tv_me);

		tv_home.setOnClickListener(this);
		tv_read.setOnClickListener(this);
		tv_find.setOnClickListener(this);
		tv_me.setOnClickListener(this);
	}

	protected void initData(){
		if(mHomeFragment == null){
			mHomeFragment = new HomeFragment();
		}

		if(!mHomeFragment.isAdded()){
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, mHomeFragment).commit();
		}

		mCurrentFragmet = mHomeFragment;

		mTestFragment = new TestFragment().newInstance("测试页...");
	}

	protected void initAction(){
	}


	private void switchFragmet(Fragment fragment){
		if (mCurrentFragmet == fragment)
			return;

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		if (!fragment.isAdded()) { // 先判断是否被add过
			transaction.hide(mCurrentFragmet).add(R.id.fragment_content, fragment).commit(); //隐藏当前的fragment，add下一个到Activity中
		} else {
			transaction.hide(mCurrentFragmet).show(fragment).commit(); //隐藏当前的fragment，显示下一个
		}

		mCurrentFragmet = fragment;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_home:
				if(mHomeFragment == null){
					mHomeFragment = new HomeFragment();
				}
				switchFragmet(mHomeFragment);
				break;
			case R.id.tv_read:
//				switchFragmet(mTestFragment);
				Intent intent = new Intent(MainActivity.this, DrawerMainActivity.class);
				startActivity(intent);
				break;
			case R.id.tv_find:
//				switchFragmet(mTestFragment);
//				Intent inte = new Intent(MainActivity.this, DrawerNavActivity.class);
//				startActivity(inte);
				break;
			case R.id.tv_me:
				switchFragmet(mTestFragment);
				break;
		}
	}
}
