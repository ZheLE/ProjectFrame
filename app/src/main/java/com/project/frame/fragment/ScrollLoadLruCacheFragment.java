package com.project.frame.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.frame.adapter.ScrollLoadLruCacheAdapter;
import com.project.frame.bean.VenuesInfo;
import com.project.frame.common.PConstant;
import com.project.frame.security.HttpTools;
import com.project.frame.ui.LruCacheDetailActivity;
import com.project.frame.ui.R;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.List;

public class ScrollLoadLruCacheFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ScrollLoadLruCacheAdapter.OnItemClickListener{
    private static final String ARG_POSITION = "position";
    private FinalHttp mFinalHttp = null;
    private View view;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ScrollLoadLruCacheAdapter mAdapter;
    private List<VenuesInfo> datas;
    private int pageNo = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case PConstant.UPDATE_FIRST:
                    mAdapter.notifyDataSetChanged();
                    break;
                case PConstant.UPDATE_REFRESH:
                    mSwipeRefresh.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();
                    break;
            }

        }
    };

    public static ScrollLoadLruCacheFragment newInstance(int position) {
        ScrollLoadLruCacheFragment fragment = new ScrollLoadLruCacheFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_swipe_refresh, container, false);

        initGui();
        initData();
        initAction();

        return view;
    }

    private void initGui(){
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.mSwipeRefresh);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.mRecyclerView);
        //创建默认线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //如果可以确定每个item的固定高度，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
    }

    private void initData(){
        datas = new ArrayList<VenuesInfo>();
        onFirstRefresh();
    }

    private void initAction(){
        //创建并设置Adapter
        mAdapter = new ScrollLoadLruCacheAdapter(getActivity(), datas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(this);

        //第一次进入页面的时候显示加载进度条
        mSwipeRefresh.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void onFirstRefresh(){
        sendRequest(PConstant.UPDATE_FIRST);
    }
    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        sendRequest(PConstant.UPDATE_REFRESH);
    }

    private void sendRequest(final int tag){

        if(mFinalHttp == null){
            mFinalHttp = HttpTools.getInstence().getFinalHttp();
        }

        mFinalHttp.get(PConstant.VENUES_URL, new AjaxCallBack() {

            @Override
            public void onSuccess(Object o) {

                Log.d("TAG", o.toString());

                List<VenuesInfo> venues = VenuesInfo.getDatas(getActivity(), o.toString());

                if (venues != null) {
                    datas.addAll(venues);
                }

                switch (tag){
                    case PConstant.UPDATE_FIRST:
                        mHandler.sendEmptyMessage(PConstant.UPDATE_FIRST);
                        break;
                    case PConstant.UPDATE_REFRESH:
                        mHandler.sendEmptyMessage(PConstant.UPDATE_REFRESH);
                        break;
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    @Override
    public void onItemClick(View view, VenuesInfo venue) {
        LruCacheDetailActivity.navigate((AppCompatActivity) getActivity(), view.findViewById(R.id.item_img), venue);
    }
}
