package com.android_mobile.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by picher on 2017/12/14.
 * Describe：
 */

public class CustomerGsonFactory extends Converter.Factory {

    private final Gson gson;

    public static CustomerGsonFactory create() {
        return create(new Gson());
    }

    public static CustomerGsonFactory create(Gson gson) {
        return new CustomerGsonFactory(gson);
    }

    private CustomerGsonFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomerGsonResponseBodyConvert<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomerGsonRequestBodyConvert<>(gson, adapter);
    }

}
