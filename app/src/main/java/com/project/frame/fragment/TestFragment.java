package com.project.frame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.project.frame.ui.R;

public class TestFragment extends Fragment {
	
	private static final String TAG = "TestFragment";
    private String hello;// = "hello android";
    private String defaultHello = "default value";
    private WebView webView;

    public static TestFragment newInstance(String s){
		TestFragment newFragment = new TestFragment();
		Bundle bundle = new Bundle();
		bundle.putString("hello", s);
		newFragment.setArguments(bundle);
		
		return newFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frament_guide_2, container, false);

		webView = (WebView) view.findViewById(R.id.webView);

		//加载本地资源
		webView.loadUrl("file:///android_asset/example.html");
		//加载web资源
		webView.loadUrl("http://baidu.com");



		return view;
	}
}
