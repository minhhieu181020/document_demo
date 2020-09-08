package com.lpb.lifecardsdk.ui.base;

public interface BaseScreen {

    void showLoading(boolean isCancel);
    //true cho ẩn khi click back, false không cho ẩn

    void hideLoading();

    void showError(String resultDesc,String resultCode);
}
