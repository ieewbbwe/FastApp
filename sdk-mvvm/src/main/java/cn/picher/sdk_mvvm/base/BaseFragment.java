package cn.picher.sdk_mvvm.base;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.picher.sdk_mvvm.BR;

/**
 * Created by picher on 2018/1/11.
 * Describe：Fragment 基类
 */

public abstract class BaseFragment<VM extends ParentViewModel, B extends ViewDataBinding>
        extends Fragment implements BaseView {

    protected VM viewModel;
    protected B binding;

    protected abstract VM onCreateViewModel();

    protected abstract int getLayoutId();

    private boolean isCanShowDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        isCanShowDialog = true;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), getLayoutId(), null, false);
        viewModel = onCreateViewModel();
        binding.setVariable(BR.viewModel, viewModel);
        viewModel.onAttach(this);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        isCanShowDialog = true;
    }

    @Override
    public void onPause() {
        isCanShowDialog = false;
        showLoading(false);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        viewModel.onDetach();
        super.onDestroyView();
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
        return (AppCompatActivity) getActivity();
    }
}
