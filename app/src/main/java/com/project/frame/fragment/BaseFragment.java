package com.project.frame.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 新闻 基类
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public abstract class BaseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    //页面布局视图

//    public PDApplication app;
    public View view = null;
    protected ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        app = (PDApplication)getActivity().getApplication();

        initDialog();

        if(view == null){
            view = getContentLayout(inflater, container, savedInstanceState);
        }

        initGui();
        initData();
        initAction();

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }

        return view;
    }

    /**
     * 加载界面布局
     * @return
     */
    protected abstract View getContentLayout(LayoutInflater inflater, ViewGroup container,
                                             Bundle savedInstanceState);

    /**
     * 初始化UI
     */
    protected abstract void initGui();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化事件
     */
    protected abstract void initAction();


    public void initDialog(){
        dialog = new ProgressDialog(getActivity());
        //设置对话框提示信息
        dialog.setMessage("正在努力加载...");
        //不允许通过返回键关闭对话框
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        //环形
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void showDialog(){
        dialog.show();
    }
    public void closeDialog(){
        if(dialog != null){
            dialog.dismiss();
        }
    }
}
