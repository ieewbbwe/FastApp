package com.webber.mcorelibspace.demo.pay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.webber.mcorelibspace.R;

public class PayDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_demo);

        findViewById(R.id.m_alipay_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliPay();
            }
        });
        findViewById(R.id.m_wx_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wxPay();
            }
        });
    }

    private void aliPay() {

    }

    private void wxPay() {

    }

}
