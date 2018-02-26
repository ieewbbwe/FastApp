package cn.picher.sdk_mvvm.base;

import android.databinding.BaseObservable;

import cn.picher.sdk_mvvm.BasicViewModel;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by picher on 2018/1/11.
 * Describe：
 */

public abstract class ParentViewModel<V extends BaseView> extends BaseObservable implements BasicViewModel{


    private CompositeDisposable compositeDisposable;
    protected V view;

    public ParentViewModel(){
        this.compositeDisposable = new CompositeDisposable();
    }

    public void onAttach(V view){
        this.view = view;
    }

    public void onDetach(){
        compositeDisposable.clear();
        this.view = null;
    }
}
