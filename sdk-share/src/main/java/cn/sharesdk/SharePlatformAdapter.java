package cn.sharesdk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import cn.sharesdk.share.demo.R;


/**
 * @author mxh
 */
public class SharePlatformAdapter extends BaseAdapter {

    private ArrayList<SharePlatform> mSharePlatform;
    private Context mContext;
    /*當前選中位置*/
    private int currentPos = -1;

    public SharePlatformAdapter(Context context) {
        this.mContext = context;
        this.mSharePlatform = new ArrayList<SharePlatform>();
    }

    public void add(SharePlatform platform) {
        mSharePlatform.add(platform);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends SharePlatform> platforms) {
        mSharePlatform.addAll(platforms);
        notifyDataSetChanged();
    }

    public void remove(SharePlatform platform) {
        mSharePlatform.remove(platform);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mSharePlatform.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mSharePlatform.size();
    }

    @Override
    public SharePlatform getItem(int position) {
        return mSharePlatform.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_socialize_item, parent, false);
            holder.mPlatformIcon = (ImageView) convertView.findViewById(R.id.umeng_socialize_shareboard_image);
            holder.mPlatformText = (TextView) convertView.findViewById(R.id.umeng_socialize_shareboard_pltform_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SharePlatform item = getItem(position);
        holder.mPlatformIcon.setImageResource(item.icon);
        holder.mPlatformText.setText(item.name);
        return convertView;
    }

    public void setCurrentPos(int position) {
        this.currentPos = position;
        notifyDataSetChanged();
    }

    public int getCurrentPos() {
        return currentPos;
    }

    static class ViewHolder {
        public ImageView mPlatformIcon;
        public TextView mPlatformText;
    }
}
