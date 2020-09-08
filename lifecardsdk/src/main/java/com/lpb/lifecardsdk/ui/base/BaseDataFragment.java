package com.lpb.lifecardsdk.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public abstract class BaseDataFragment<K> extends BaseFragment {

    protected K mPresenter;
    protected AppCompatActivity mActivity;
    protected View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(getLayoutId(), container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initAction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter instanceof BasePresenter) {
            ((BasePresenter) mPresenter).subscribe();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter instanceof BasePresenter) {
            ((BasePresenter) mPresenter).unSubscribe();
        }
    }

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        return null;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
    protected abstract void initAction();
}
