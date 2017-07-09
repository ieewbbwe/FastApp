package com.android_mobile.core.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by mxh on 2017/7/7.
 * Describe：MVP-P
 */
public abstract class BasePresenter<V> {
    protected V mView;
    protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public void setView(V v) {
        this.mView = v;
        this.onAttached();
    }

    public abstract void onAttached();

    public void onDetached() {
        mCompositeSubscription.unsubscribe();
    }
}
