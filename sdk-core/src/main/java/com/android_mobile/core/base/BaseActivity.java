package com.android_mobile.core.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.android_mobile.core.BasicActivity;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mxh on 2017/6/1.
 * Describe：
 */

public abstract class BaseActivity extends BasicActivity {

    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        compositeDisposable = new CompositeDisposable();
    }

    public void completableBridge(Completable completable, Action action, Consumer<Throwable> throwableConsumer, boolean needLoading) {
        addReference(completable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .doOnSubscribe(getOnSubscribe(needLoading))
                .doAfterTerminate(getOnAfterTerminate())
                .subscribe(action, throwableConsumer));
    }

    /**
     * 管理所有事件，类销毁后移除这些事件，防止回掉错误
     * @param subscribe 事件
     */
    private void addReference(Disposable subscribe) {
        if(compositeDisposable != null){
            compositeDisposable.add(subscribe);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(compositeDisposable != null ){
            compositeDisposable.clear();
        }
    }
}
