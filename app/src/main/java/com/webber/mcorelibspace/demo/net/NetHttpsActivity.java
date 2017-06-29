package com.webber.mcorelibspace.demo.net;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android_mobile.net.OnProgressRequestCallback;
import com.webber.mcorelibspace.R;
import com.webber.mcorelibspace.demo.net.api.YahooApi;
import com.webber.mcorelibspace.demo.net.response.QueryProductResponse;

import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetHttpsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_https);
        findViewById(R.id.m_https_bt).setOnClickListener(v -> {
            //yahooHttpsRequest();
            httpsRequest();
        });
    }

    private void httpsRequest() {

    }

    private void yahooHttpsRequest() {
        String q = getSQL("", "0000217,0000099,C0000066,C0000328,C18561606,0000554,0000413,0000148,0000710,0000742,0000661,0000493,0000001", null, null, null, "-prop_2", null, null,
                "weight", "desc", "1", "10");
        ApiFactory.createApi("https://auction.yql.yahoo.com/", YahooApi.class)
                .getSearchProduct(q, true, "json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnProgressRequestCallback<Response<QueryProductResponse>>(NetHttpsActivity.this) {
                    @Override
                    public void onResponse(Response<QueryProductResponse> response) {
                        Toast.makeText(NetHttpsActivity.this, "请求到" + response.body()
                                .getQuery().getResults().getProduct().size() + "条数据", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 組拼sql
     */
    public static String getSQL(String key, String cartId, String cartLevel, String merchantId,
                                String custCid, String filters, String minPrice, String maxPrice, String sortBy, String sortOrder, String pageStart, String pageSize) {
        StringBuffer sql = new StringBuffer("SELECT * FROM ecsearch.sdk.search (" + pageStart + "," + pageSize + ")");
        if (key == null) {
            key = "";
        }
        sql.append(" WHERE  keyword = \"");
        sql.append(key);
        sql.append("\" AND property = \"hkbrandstore\"");
        sql.append(" AND backfill = \"1\"");

        if (cartId != null) {
            sql.append(" AND catId = \"");
            sql.append(cartId);
            sql.append("\"");
        }
        if (cartLevel != null) {
            sql.append(" AND catLevel = \"");
            sql.append(cartLevel);
            sql.append("\"");
        }
        if (merchantId != null) {
            sql.append(" AND merchantId = \"");
            sql.append(merchantId);
            sql.append("\"");
        }
        if (custCid != null) {
            sql.append(" AND custCid = \"");
            sql.append(custCid);
            sql.append("\"");
        }
        if (filters != null) {
            sql.append(" AND filters = \"");
            sql.append(filters);
            sql.append("\"");
        }
        if (minPrice != null) {
            sql.append(" AND minPrice = \"");
            sql.append(minPrice);
            sql.append("\"");
        }
        if (maxPrice != null) {
            sql.append(" AND maxPrice = \"");
            sql.append(maxPrice);
            sql.append("\"");
        }
        if (sortBy != null) {
            sql.append(" AND sortBy = \"");
            sql.append(sortBy);
            sql.append("\"");
        }
        if (sortOrder != null) {
            sql.append(" AND sortOrder = \"");
            sql.append(sortOrder);
            sql.append("\"");
        }
        return sql.toString();
    }

}
