package com.project.frame.common;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by lizhe on 2015/5/26.
 */
public class ProgressUtils {

    public static ProgressDialog dialog;

    public static void ShowProgressDialog(Context context){
        dialog = ProgressDialog.show(context, null, "loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(true);  //设置是否可以通过点击back键取消
    }

    public static void CloseProgressDialog(){

        if(dialog.isShowing() && dialog != null)
            dialog.dismiss();
    }
}
