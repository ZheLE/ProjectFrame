package com.project.frame.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.frame.ui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

	private SwipeRefreshLayout mSwieRefresh;
//	private boolean isRefresh = false;
	private ListView mListView;
	private List<Map<String, String>> mList;
	private MyAdapter mAdapter;

	@Override
	protected View getContentLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home, container, false);
		return view;
	}

	@Override
	protected void initGui() {
		mSwieRefresh = (SwipeRefreshLayout) view.findViewById(R.id.mSwipe_refresh);
		mListView = (ListView) view.findViewById(R.id.mListview);
	}

	@Override
	protected void initData() {
		mAdapter = new MyAdapter();
		mList = new ArrayList<Map<String, String>>();
		mListView.setAdapter(mAdapter);
	}

	@Override
	protected void initAction() {
		mSwieRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwieRefresh.setOnRefreshListener(this);
	}

	@Override
	public void onRefresh() {
//		if(!isRefresh){
//			isRefresh = true;
//		mText.setText("正在刷新");

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(mList.isEmpty()){
					addData();
				}else {
					clearData();
				}

				mSwieRefresh.setRefreshing(false);
				mAdapter.notifyDataSetChanged();
//				mText.setText("刷新完成");
			}}, 2000);
		}

//	}

	private List<Map<String, String>> addData(){
		Map<String, String> map;
		for(int i = 0; i<10; i++){
			map = new HashMap<String, String>();
			map.put("name", "Google..." + i);
			mList.add(map);
		}
		return mList;
	}

	private void clearData(){
		mList.clear();
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mList.size() <=0 ? 0:mList.size();
		}

		@Override
		public Object getItem(int i) {
			return mList.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {

			ViewHolder holder = null;

			if(view == null){
				holder = new ViewHolder();
				view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item, null);

				holder.name = (TextView) view.findViewById(R.id.mText);
				view.setTag(holder);
			}else {
				holder = (ViewHolder) view.getTag();
			}

			Map<String, String> map = mList.get(i);
			holder.name.setText(map.get("name"));

			return view;
		}
	}

	class ViewHolder{
		TextView name;
	}
}
