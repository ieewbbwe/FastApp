package com.android_mobile.core.event;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mxh on 2017/2/27.
 * Describe：基于RxJava的消息传递总线
 */

public class RxEventBus {
    private static volatile RxEventBus mInstance;

    private final PublishSubject<RxEvent> bus;


    private RxEventBus() {
        bus = PublishSubject.create();
    }

    /**
     * 单例模式RxBus
     */
    public static RxEventBus getInstance() {

        if (mInstance == null) {
            if (mInstance == null) {
                mInstance = new RxEventBus();
            }
        }

        return mInstance;
    }


    /**
     * 发送消息
     */
    public void send(RxEvent rxEvent) {
        bus.onNext(rxEvent);
    }


    /**
     * 接收消息
     */
    public Disposable register(Consumer<RxEvent> consumer) {
        return bus.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
    }

}
