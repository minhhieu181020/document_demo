package com.lpb.lifecardsdk.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;


public abstract class BaseDataActivity<K> extends BaseActivity {

    protected K mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        initAction();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter instanceof BasePresenter) {
            ((BasePresenter) mPresenter).subscribe();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter instanceof BasePresenter) {
            ((BasePresenter) mPresenter).unSubscribe();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
    protected abstract void initData();

    protected abstract void initAction();
}
