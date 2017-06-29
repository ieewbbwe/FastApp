package com.webber.mcorelibspace.demo.net;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.net.ApiConstants;
import com.android_mobile.net.NetUtils;
import com.android_mobile.net.OnSimpleRequestCallback;
import com.webber.mcorelibspace.R;
import com.webber.mcorelibspace.demo.net.request.TopNewsRequest;
import com.webber.mcorelibspace.demo.net.response.TopNewsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static rx.Completable.complete;

public class NetRequestActivity extends AppCompatActivity {

    private static final int PAGE_OFFSET = 10;
    private static int mCurrentPage = 0;
    private RecyclerView mNewsRv;
    private List<TopNewsResponse.TopNewsItem> mDatas = new ArrayList<>();
    private TopNewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_request);
        mNewsRv = (RecyclerView) findViewById(R.id.m_news_rv);
        mNewsRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TopNewsAdapter();
        mNewsRv.setAdapter(mAdapter);

        findViewById(R.id.m_get_bt).setOnClickListener(v -> getRequest());
        findViewById(R.id.m_post_bt).setOnClickListener(v -> postRequest());

    }

    private void postRequest() {

    }

    private void getRequest() {
        TopNewsRequest mTopNewsRequest = new TopNewsRequest(ApiConstants.KEY_WX, PAGE_OFFSET, mCurrentPage + 1);
        ApiFactory.getNewsApi().getTopNews(NetUtils.getParams(mTopNewsRequest))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnSimpleRequestCallback<Response<TopNewsResponse>>(NetRequestActivity.this) {
                    @Override
                    public void onResponse(Response<TopNewsResponse> response) {
                        mDatas.clear();
                        mDatas.addAll(response.body().newslist);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        complete();
                    }
                });
    }

    class TopNewsAdapter extends RecyclerView.Adapter<TopNewsAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_news_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TopNewsResponse.TopNewsItem item = mDatas.get(position);
            holder.mTitleTv.setText(item.getTitle());
            holder.mContentTv.setText(item.getDescription());
            ImageLoadFactory.getInstance().getImageLoadHandler()
                    .displayImage(item.getPicUrl(), holder.mNewIv);
        }

        @Override
        public int getItemCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mTitleTv;
            private TextView mContentTv;
            private ImageView mNewIv;

            public ViewHolder(View itemView) {
                super(itemView);
                mTitleTv = (TextView) itemView.findViewById(R.id.top_news_center_title_tv);
                mContentTv = (TextView) itemView.findViewById(R.id.top_news_center_content_tv);
                mNewIv = (ImageView) itemView.findViewById(R.id.top_news_iv);
            }
        }
    }
}
