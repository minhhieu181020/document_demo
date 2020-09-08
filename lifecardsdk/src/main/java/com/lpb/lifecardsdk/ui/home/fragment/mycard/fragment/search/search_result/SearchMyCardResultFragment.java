package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.search.search_result;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.adapter.MyCardStillValidateAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.CardDetailsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.CardDetailsWaitFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.usermanager.UserManagerFragment;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.CustomEditTextClearable;

import java.util.List;

public class SearchMyCardResultFragment extends BaseDataFragment<SearchMyCardResultContract.Presenter> implements SearchMyCardResultContract.View {
    private String sKeyWord = "";
    private ImageView imgBack;
    private CustomEditTextClearable edtSearch;
    private RelativeLayout rlSearchNoResult;
    private RecyclerView rvCards;
    private MyCardStillValidateAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_search_mycard_result;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);
        edtSearch = view.findViewById(R.id.edtSearch);
        rlSearchNoResult = view.findViewById(R.id.rlSearchNoResult);
        rvCards = view.findViewById(R.id.rvCards);
        rvCards.setNestedScrollingEnabled(false);
        adapter = new MyCardStillValidateAdapter(mActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        rvCards.setAdapter(adapter);
        rvCards.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        mPresenter = new SearchMyCardResultPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        sKeyWord = bundle.getString(Constants.BundleConstants.KEYWORD);
        edtSearch.setText(sKeyWord);
        mPresenter.getDataMyCard(LCConfig.getPhoneNumber(), sKeyWord);
    }

    @Override
    protected void initAction() {
        edtSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sKeyWord = edtSearch.getText().toString().trim();
                    if (sKeyWord.isEmpty()) {
                        showError(getString(R.string.search_please_enter_keyword),"");
                    } else {
                        mPresenter.getDataMyCard(LCConfig.getPhoneNumber(), sKeyWord);
                    }
                    return true;
                }
                return false;
            }
        });
        adapter.setOnClickListener(new MyCardStillValidateAdapter.OnClickListener() {
            @Override
            public void onClick(ListCardResponseDefault.OwnCardDto item,int i) {
//                MainMyCardsFragment.getInstance().addFragment(new CardDetailsFragment(), MyCardFragment.this);
            }

            @Override
            public void onClickAO(ListCardResponseDefault.OwnCardDto myCardSAO,int i) {
                CardDetailsFragment cardInforFragment = new CardDetailsFragment();
                ListCardResponseDefault.OwnCardDto OwnServiceDto = myCardSAO;
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Constants.BundleConstants.MY_CARD_INFORMATION, OwnServiceDto);
                cardInforFragment.setArguments(bundle1);
                MainMyCardsFragment.getInstance().addFragment(cardInforFragment, SearchMyCardResultFragment.this);
            }

            @Override
            public void onClickEC(ListCardResponseDefault.OwnCardDto myCardS,int i) {
                CardDetailsFragment cardInforFragment = new CardDetailsFragment();
                ListCardResponseDefault.OwnCardDto OwnServiceDto = myCardS;
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Constants.BundleConstants.MY_CARD_INFORMATION, OwnServiceDto);
                cardInforFragment.setArguments(bundle1);
                MainMyCardsFragment.getInstance().addFragment(cardInforFragment, SearchMyCardResultFragment.this);

            }


            @Override
            public void onClickW(ListCardResponseDefault.OwnCardDto myCardSW,int i) {
                CardDetailsWaitFragment cardDetailsWaitFragment = new CardDetailsWaitFragment();
                ListCardResponseDefault.OwnCardDto OwnServiceDto = myCardSW;
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Constants.BundleConstants.MY_CARD_INFORMATION, OwnServiceDto);
                cardDetailsWaitFragment.setArguments(bundle1);
                MainMyCardsFragment.getInstance().addFragment(cardDetailsWaitFragment, SearchMyCardResultFragment.this);
            }

            @Override
            public void onClickUserNumber(ListCardResponseDefault.OwnCardDto ownCardDto,int i) {
                Integer mNumberUser = ownCardDto.getNumberOfShareCardUsers();
                String mStatusShare = ownCardDto.getCardShare();
                String mStatusCard = ownCardDto.getStatus();

                if (StringUtils.isEmpty(mStatusShare))
                    return;

                if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.N)) {
                    Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_cant_share), "");
                } else if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.K)) {
                    Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_cant_share_2), "");
                } else if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.Y)) {

                    UserManagerFragment userManagerFragment = new UserManagerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BundleConstants.CARD_NUMBER, ownCardDto.getCardNo());
                    bundle.putString(Constants.BundleConstants.CARD_NAME, ownCardDto.getName());
                    bundle.putString(Constants.BundleConstants.CARD_NUMBER_DISPLAY, ownCardDto.getCardNoDisplay());
                    if (mNumberUser != null)
                        bundle.putInt(Constants.BundleConstants.USER_NUMBER, mNumberUser);
                    if (!StringUtils.isEmpty(mStatusCard)) {
                        if (mStatusCard.equalsIgnoreCase("E") || mStatusCard.equalsIgnoreCase("C")) {
                            if (mNumberUser == null || mNumberUser < 1) {
                                Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_card_expire), "");
                                return;
                            } else {
                                bundle.putBoolean(Constants.BundleConstants.CAN_SHARE_CARD, false);
                            }
                        } else {
                            bundle.putBoolean(Constants.BundleConstants.CAN_SHARE_CARD, true);
                        }
                    } else {
                        bundle.putBoolean(Constants.BundleConstants.CAN_SHARE_CARD, false);
                    }

                    userManagerFragment.setArguments(bundle);
                    MainMyCardsFragment.getInstance().addFragment(userManagerFragment, SearchMyCardResultFragment.this);
                }

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void setDataMyCard(List<ListCardResponseDefault.OwnCardDto> dataMyCard) {
        adapter.setItems(dataMyCard);
    }

    @Override
    public void showContent(boolean isEmpty) {
        if (isEmpty == true) {
            rlSearchNoResult.setVisibility(View.GONE);
            rvCards.setVisibility(View.VISIBLE);
        } else {
            rvCards.setVisibility(View.GONE);
            rlSearchNoResult.setVisibility(View.VISIBLE);
        }
    }
}
