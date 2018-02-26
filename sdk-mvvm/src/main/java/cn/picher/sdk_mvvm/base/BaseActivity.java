package cn.picher.sdk_mvvm.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.picher.sdk_mvvm.BR;

/**
 * Created by picher on 2018/1/11.
 * Describe：Activity基类
 */

public abstract class BaseActivity<VM extends ParentViewModel,B extends ViewDataBinding>
        extends AppCompatActivity implements BaseView{

    private B binding;
    private VM viewModel;

    protected abstract VM onCreateViewModel();

    protected abstract int getLayoutId();

    private boolean isCanShowDialog = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        isCanShowDialog = true;
        binding = DataBindingUtil.setContentView(this,getLayoutId());
        viewModel = onCreateViewModel();
        binding.setVariable(BR.viewModel,viewModel);
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public boolean isCanShowDialog() {
        return isCanShowDialog;
    }

    @Override
    public AppCompatActivity getSupportActivity() {
        return this;
    }

    @Override
    protected void onPause() {
        isCanShowDialog = false;
        showLoading(false);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCanShowDialog = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDetach();
    }
}
