package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.search.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.search.search_result.SearchMyCardResultFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.widget.CustomEditTextClearable;
import com.lpb.lifecardsdk.widget.CustomToast;

public class SearchMyCardsFragment extends BaseDataFragment<SearchMyCardsPresenter> implements SearchMyCardsContract.View {
    private CustomEditTextClearable edtSearch;
    private TextView tvCancel;


    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_search_mycard;
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        edtSearch = view.findViewById(R.id.edtSearch);
        tvCancel = view.findViewById(R.id.tvCancel);

    }

    @Override
    protected void initData() {
        mPresenter = new SearchMyCardsPresenter(mActivity, this);
    }

    @Override
    protected void initAction() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String sKeyWord = edtSearch.getText().toString().trim();
                    if (sKeyWord.isEmpty()) {
                        showError(getString(R.string.search_please_enter_keyword),"");
                    } else {
                        SearchMyCardResultFragment searchResultFragment = new SearchMyCardResultFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.BundleConstants.KEYWORD, sKeyWord);
                        searchResultFragment.setArguments(bundle);
                        MainMyCardsFragment.getInstance().addFragment(searchResultFragment, SearchMyCardsFragment.this);
                        ScreenUtils.hideSoftKeyboard(mActivity);
                    }
                    return true;
                }
                return false;
            }
        });
    }


}
