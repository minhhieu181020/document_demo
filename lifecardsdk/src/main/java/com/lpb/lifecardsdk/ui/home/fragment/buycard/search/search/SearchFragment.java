package com.lpb.lifecardsdk.ui.home.fragment.buycard.search.search;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.search.search_result.SearchResultFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.MainMyCodeFragment;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.CustomEditTextClearable;

public class SearchFragment extends BaseDataFragment<SearchPresenter> implements SearchContract.View {
    private CustomEditTextClearable edtSearch;
    private TextView tvCancel;
    private Integer providerID;
    private String mainParentClass;
    private LinearLayout llContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_search_buycard;
    }

    @Override
    protected void initView() {
        edtSearch = view.findViewById(R.id.edtSearch);
        tvCancel = view.findViewById(R.id.tvCancel);
        llContainer = view.findViewById(R.id.llContainer);
        edtSearch.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        imgr.showSoftInput(edtSearch, 0);
    }

    @Override
    protected void initData() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        providerID = bundle.getInt(Constants.BundleConstants.PROVIDER_ID, Config.DEAULT_VALUE_INT);
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);

        if (StringUtils.isEmpty(mainParentClass)) {
            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
            llContainer.setVisibility(View.GONE);
        } else {
            llContainer.setVisibility(View.VISIBLE);

        }
        mPresenter = new SearchPresenter(mActivity, this);
    }

    @Override
    protected void initAction() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenUtils.hideSoftKeyboard(mActivity);
                mActivity.onBackPressed();
            }
        });
        edtSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String sKeyWord = edtSearch.getText().toString().trim();
                    if (sKeyWord.isEmpty()) {
                        showError(mActivity.getString(R.string.lifecardsdk_search_notify_empty_key_word), "");
                    } else {
                        SearchResultFragment searchResultFragment = new SearchResultFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.BundleConstants.KEYWORD, sKeyWord);
                        bundle.putInt(Constants.BundleConstants.PROVIDER_ID, providerID);
                        bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                        searchResultFragment.setArguments(bundle);
                        switch (mainParentClass) {
                            case Constants.ParentClass.MainBuyCardFragment:
                                MainBuyCardFragment.getInstance().addFragment(searchResultFragment, SearchFragment.this);
                                break;
                            case Constants.ParentClass.MainMyCardFragment:
                                MainMyCardsFragment.getInstance().addFragment(searchResultFragment, SearchFragment.this);
                                break;
                            case Constants.ParentClass.MainMyCodeFragment:
                                MainMyCodeFragment.getInstance().addFragment(searchResultFragment, SearchFragment.this);
                                break;
                            case Constants.ParentClass.MainScanQrCodeFragment:
                                MainScanQrCodeFragment.getInstance().addFragment(searchResultFragment, SearchFragment.this);
                                break;
                            default:
                                showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                                break;
                        }

                        ScreenUtils.hideSoftKeyboard(mActivity);
                    }
                    return true;
                }
                return false;
            }
        });
    }


}
