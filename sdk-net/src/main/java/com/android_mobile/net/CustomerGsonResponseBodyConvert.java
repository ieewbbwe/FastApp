package com.android_mobile.net;

import com.android_mobile.net.response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by picher on 2017/12/14.
 * Describe：自定义Gson 对象解析器 适配GsonFormatException
 */

final class CustomerGsonResponseBodyConvert<T> implements Converter<ResponseBody, T> {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> tTypeAdapter;

    CustomerGsonResponseBodyConvert(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.tTypeAdapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
        if (!baseResponse.isSuccess()) {
            throw new ApiException(baseResponse.getMessage());
        }
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return tTypeAdapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
