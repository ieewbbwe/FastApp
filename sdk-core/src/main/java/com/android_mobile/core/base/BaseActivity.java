package com.android_mobile.core.base;

import com.android_mobile.core.BasicActivity;

import org.reactivestreams.Subscriber;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mxh on 2017/6/1.
 * Describe：
 */

public abstract class BaseActivity extends BasicActivity {

    public void completableBridge(Completable completable, Action action, Consumer<Throwable> throwableConsumer, boolean needLoading) {
        completable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .doOnSubscribe(getOnSubscribe(needLoading))
                .doAfterTerminate(getOnAfterTerminate())
                .subscribe(action, throwableConsumer);
    }

    public void singleBridge(Single single, SingleObserver<?> singleObserver, boolean needLoading) {
        single.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .doOnSubscribe(getOnSubscribe(needLoading))
                .doAfterTerminate(getOnAfterTerminate())
                .subscribe(singleObserver);
    }

    public void observableBridge(Observable observable, boolean needLoading, Observer<?> observer){
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(getOnSubscribe(needLoading))
                .doAfterTerminate(getOnAfterTerminate())
                .subscribe(observer);
    }

    @android.support.annotation.NonNull
    private io.reactivex.functions.Action getOnAfterTerminate() {
        return new io.reactivex.functions.Action() {
            @Override
            public void run() throws Exception {
                hideProgressBar();
            }
        };
    }

    @android.support.annotation.NonNull
    private io.reactivex.functions.Consumer<Disposable> getOnSubscribe(final boolean needLoading) {
        return new io.reactivex.functions.Consumer<Disposable>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
                displayProgressBar(needLoading);
            }
        };
    }
}
