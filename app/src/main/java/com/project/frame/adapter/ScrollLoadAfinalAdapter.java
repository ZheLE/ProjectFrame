package com.project.frame.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.frame.bean.VenuesInfo;
import com.project.frame.ui.R;

import net.tsz.afinal.FinalBitmap;

import java.util.List;

/**
 * Created by lizhe on 2015/7/23.
 * 滑动停止后才加载可见项
 * 滑动时 取消所有加载项
 */
public class ScrollLoadAfinalAdapter extends RecyclerView.Adapter<ScrollLoadAfinalAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<VenuesInfo> mDatas;
    private FinalBitmap mFinalBitmap;
    private LayoutInflater mInflater = null;

    private OnItemClickListener onItemClickListener;

    /**
     * 设置Item点击事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public ScrollLoadAfinalAdapter(Context context, List<VenuesInfo> datas){
        this.mContext = context;
        this.mDatas = datas;

        mInflater = LayoutInflater.from(mContext);

//        if(mFinalBitmap == null){
//            mFinalBitmap = HttpTools.getInstence().getFinalBitmap(this.context);
//        }
    }

    //创建新View 被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = mInflater.inflate(R.layout.fragment_swipe_refresh_item, viewGroup, false);

        view.setOnClickListener(this);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    //将数据与界面进行绑定操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        VenuesInfo venue = mDatas.get(i);

        viewHolder.img.setImageResource(R.drawable.ic_launcher);
        String imgUrl = mDatas.get(i).getTitle_img();
        viewHolder.img.setTag(imgUrl); //为imageview设置Tag

        viewHolder.title.setText(venue.getTitle());
        viewHolder.address.setText(venue.getAddress());
        mFinalBitmap.display(viewHolder.img, venue.getTitle_img());

        viewHolder.itemView.setTag(venue);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if(mDatas != null){
            return mDatas.size();
        }
        return 0;
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(final View v) {

        if(onItemClickListener != null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemClickListener.onItemClick(v, (VenuesInfo) v.getTag());
                }
            }, 200);
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView title, address;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.item_img);
            title = (TextView)itemView.findViewById(R.id.item_txt_tile);
            address = (TextView)itemView.findViewById(R.id.item_txt_add);
        }
    }


    public interface OnItemClickListener{
        void onItemClick(View view, VenuesInfo venue);
    }
}
