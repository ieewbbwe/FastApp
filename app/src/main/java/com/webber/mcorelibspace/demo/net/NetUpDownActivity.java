package com.webber.mcorelibspace.demo.net;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android_mobile.core.utiles.BitmapUtils;
import com.android_mobile.net.OnSimpleRequestCallback;
import com.android_mobile.net.response.BaseResponse;
import com.webber.mcorelibspace.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NetUpDownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_up_down);
        findViewById(R.id.m_upload_bt).setOnClickListener(v -> uploadRequest("/storage/emulated/0/DCIM/Camera/IMG_20170601_142846.jpg"));
        findViewById(R.id.m_download_bt).setOnClickListener(v -> downLoadRequest());
    }

    private void downLoadRequest() {

    }

    private void uploadRequest(String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), BitmapUtils.processImgByteArray(path));
            // MultipartBody.Part  和后端约定好Key，这里的partName是用image
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", imgFile.getName(), requestFile);
            MultipartBody.Part id =
                    MultipartBody.Part.createFormData("ID", "001");
            ApiFactory.getFileApi().uploadImage(id, body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseResponseResponse -> {
                        Toast.makeText(NetUpDownActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
