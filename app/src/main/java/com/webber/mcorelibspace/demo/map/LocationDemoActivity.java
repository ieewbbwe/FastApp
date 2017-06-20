package com.webber.mcorelibspace.demo.map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android_mobile.location.LocationHelper;
import com.android_mobile.location.listener.LocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.webber.mcorelibspace.R;

import java.util.ArrayList;
import java.util.List;

public class LocationDemoActivity extends AppCompatActivity {

    private TextView mInfoTv;
    private LatLng mCenterPos;
    private RecyclerView mSearchRv;
    private List<PoiInfo> mDatas = new ArrayList<>();
    private SearchAdapter mAdapter;
    private EditText mKeyWordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_demo);
        mInfoTv = (TextView) findViewById(R.id.m_info_tv);
        mKeyWordEt = (EditText) findViewById(R.id.m_keyword_et);
        mSearchRv = (RecyclerView) findViewById(R.id.m_search_rv);

        mSearchRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchAdapter();
        mSearchRv.setAdapter(mAdapter);

        findViewById(R.id.m_location_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location();
            }
        });
        findViewById(R.id.m_distance_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance();
            }
        });
        findViewById(R.id.m_nearby_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearbySearch();
            }
        });
    }

    private void nearbySearch() {
        if (mCenterPos == null) {
            Toast.makeText(this, "请先定位", Toast.LENGTH_SHORT).show();
            return;
        }
        LocationHelper.getInstance().startPoiNearBySearch(mKeyWordEt.getText().toString().trim(), PoiSortType.distance_from_near_to_far,
                mCenterPos, 1000, new OnGetPoiSearchResultListener() {
                    @Override
                    public void onGetPoiResult(PoiResult poiResult) {
                        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                            mDatas.clear();
                            mDatas.addAll(poiResult.getAllPoi());
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(LocationDemoActivity.this, "查找到" + poiResult.getAllPoi().size() + "个点",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LocationDemoActivity.this, "查找失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

                    }

                    @Override
                    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                    }
                });
    }

    private void distance() {

    }

    private void location() {
        LocationHelper.getInstance().startLocation(new LocationListener() {
            @Override
            public void onStartLocation() {
                mInfoTv.setText("定位中...");
            }

            @Override
            public void onReceiveLocation(BDLocation location, boolean isCache) {
                StringBuilder sb = new StringBuilder();
                sb.append("省份：").append(location.getProvince()).append("\n")
                        .append("城市：").append(location.getCity()).append("\n")
                        .append("经纬度：").append(location.getLatitude()).append(",").append(location.getLongitude()).append("\n");
                mCenterPos = new LatLng(location.getLatitude(), location.getLongitude());
                mInfoTv.setText(sb.toString());
            }

            @Override
            public void onLocationFailure(Throwable error, String msg) {
                mInfoTv.setText("定位失败...");
            }
        });
    }

    class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_location_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            PoiInfo item = mDatas.get(position);
            StringBuilder sb = new StringBuilder();
            sb.append("店名：").append(item.name).append("\n")
                    .append("电话：").append(item.phoneNum).append("\n")
                    .append("地址：").append(item.address).append("\n");
            holder.mLocateTv.setText(sb.toString());
        }

        @Override
        public int getItemCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mLocateTv;

            public ViewHolder(View itemView) {
                super(itemView);
                mLocateTv = (TextView) itemView.findViewById(R.id.m_location_tv);
            }
        }
    }
}
