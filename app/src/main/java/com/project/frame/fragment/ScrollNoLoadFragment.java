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

import com.project.frame.adapter.ScrollNoLoadAdapter;
import com.project.frame.bean.VenuesInfo;
import com.project.frame.common.ImageLoader;
import com.project.frame.common.PConstant;
import com.project.frame.security.HttpTools;
import com.project.frame.ui.DrawerDetailActivity;
import com.project.frame.ui.R;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.List;

public class ScrollNoLoadFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ScrollNoLoadAdapter.OnItemClickListener{
    private static final String ARG_POSITION = "position";
//    private int position;

    private FinalHttp mFinalHttp = null;
    private View view;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ScrollNoLoadAdapter mAdapter;
    private List<VenuesInfo> datas;
    private int pageNo = 1;

    private ImageLoader mImageLoader;
    public static String[] URLS; //用来保存所有获取到的图片地址
    private int mStart, mEnd;
    private boolean mFirst = true;

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

    public static ScrollNoLoadFragment newInstance(int position) {
        ScrollNoLoadFragment fragment = new ScrollNoLoadFragment();
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

        mImageLoader = new ImageLoader(mRecyclerView);
    }

    private void initData(){
        datas = new ArrayList<VenuesInfo>();
        onFirstRefresh();
    }

    private void initAction(){
        //创建并设置Adapter
        mAdapter = new ScrollNoLoadAdapter(getActivity(), datas, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(this);

        //第一次进入页面的时候显示加载进度条
        mSwipeRefresh.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            //正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调
            //回调顺序如下
            //第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
            //第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
            //第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
            //当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1；
            //由于用户的操作，屏幕产生惯性滑动时为2
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == recyclerView.SCROLL_STATE_IDLE) {
                    //加载可见项
                    mImageLoader.loadImages(mStart, mEnd);
                } else {
                    //停止任务
                    mImageLoader.cancleAllTasks();
                }
            }

            //滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
            //firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
            //visibleItemCount：当前能看见的列表项个数（小半个也算）
            //totalItemCount：列表项共数
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mStart = mLayoutManager.findFirstVisibleItemPosition(); //第一个可见元素
                mEnd = mLayoutManager.findLastVisibleItemPosition() + 1 ; //最后一个可见元素

                Log.d("TAG", "mStart" + mStart);
                Log.d("TAG", "mEnd" + mEnd);
                //第一次显示调用
                if(mFirst){
                    mImageLoader.loadImages(mStart, mEnd);
                    mFirst = false;
                }
            }
        });


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

                URLS = new String[datas.size()];
                for (int i = 0; i < datas.size(); i++) {
                    URLS[i] = datas.get(i).getTitle_img();
//                    Log.d("TAG", "URLS[i]" + URLS[i]);
                }
                Log.d("TAG", "URLS.length" + URLS.length);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    @Override
    public void onItemClick(View view, VenuesInfo venue) {
        DrawerDetailActivity.navigate((AppCompatActivity) getActivity(), view.findViewById(R.id.item_img), venue);
    }
}
