package com.webber.mcorelibspace.demo.core.mvvm.wrapper;

import cn.picher.sdk_mvvm.base.BaseView;
import cn.picher.sdk_mvvm.base.ParentViewModel;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by picher on 2018/1/11.
 * Describe：包裹一层SDK 内容  方便自定义
 */

public class WrapperViewModel<V extends BaseView> extends ParentViewModel<V> {

    public void singleBridge(Single single, SingleObserver<?> singleObserver, boolean needLoading) {
        single.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .doOnSubscribe(getOnSubscribe(needLoading))
                //.doAfterTerminate(getOnAfterTerminate())
                .subscribe(singleObserver);
    }

    @android.support.annotation.NonNull
    private Action getOnAfterTerminate() {
        return new Action() {
            @Override
            public void run() throws Exception {
                view.showLoading(false);
            }
        };
    }

    @android.support.annotation.NonNull
    private Consumer<Disposable> getOnSubscribe(final boolean needLoading) {
        return new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                view.showLoading(needLoading);
            }
        };
    }
}
