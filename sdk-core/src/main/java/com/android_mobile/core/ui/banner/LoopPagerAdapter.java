package com.android_mobile.core.ui.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android_mobile.core.R;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.core.utiles.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class LoopPagerAdapter extends BasicPageAdapter<IBanner> {
    private static final String TAG = "LoopPagerAdapter";
    private OnImageClickListener mListener;

    public LoopPagerAdapter(Context context, List<IBanner> list) {
        super(context, list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public List<IBanner> getList() {
        return list;
    }

    public void addImage(ArrayList images) {
        if (list.size() > 0) {
            list.clear();
        }
        if (CollectionUtils.isNotEmpty(images)) {
            list.addAll(images);
        }
        notifyDataSetChanged();
    }

    public interface OnImageClickListener {
        void onImageClick(int pos);
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.view_banner_head, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.header_imageview);
        //imageView.setImageResource(R.mipmap.img_item_default);
        //Lg.print("andy", "广告图地址:" + list.get(position).getImageUrl());
        ImageLoadFactory.getInstance().getImageLoadHandler().displayImage(R.mipmap.ic_img_item_default, imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        ((ViewPager) container).removeView(view);
        view = null;
    }
}
