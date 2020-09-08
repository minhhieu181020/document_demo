package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.addcard;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.CardPhysicalResponseDefault;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.mycardsfragment.MyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.CustomToast;

public class AddCardFragment extends BaseDataFragment<AddCardPresenter> implements AddCardContract.View {

    private String sKeyWord = "";
    private EditText edtSearch;
    private EditText edtSearch1;
    private EditText edtSearch2;
    private EditText edtSearch3;
    private EditText edtSearch4;
    private Button btnSent;


    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_add_card_fragment;
    }

    @Override
    protected void initView() {
        edtSearch = view.findViewById(R.id.edtSearch);
        edtSearch1 = view.findViewById(R.id.edtSearch1);
        edtSearch2 = view.findViewById(R.id.edtSearch2);
        edtSearch3 = view.findViewById(R.id.edtSearch3);
        edtSearch4 = view.findViewById(R.id.edtSearch4);
        btnSent = view.findViewById(R.id.btnSent);

        edtSearch.addTextChangedListener(new GenericTextWatcher(edtSearch));
        edtSearch1.addTextChangedListener(new GenericTextWatcher(edtSearch1));
        edtSearch2.addTextChangedListener(new GenericTextWatcher(edtSearch2));
        edtSearch3.addTextChangedListener(new GenericTextWatcher(edtSearch3));
        edtSearch4.addTextChangedListener(new GenericTextWatcher(edtSearch4));
    }

    @Override
    protected void initData() {
        mPresenter = new AddCardPresenter(mActivity, this);
    }

    @Override
    protected void initAction() {
//        edtSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    sKeyWord = edtSearch.getText().toString().trim();
//                    if (sKeyWord.isEmpty()) {
//                        CustomToast.makeText(mActivity, getString(R.string.search_please_enter_keyword), CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
//                    } else {
//                        mPresenter.getDataCard(edtSearch.getText().toString(), LCConfig.getCustomerName(), LCConfig.getPhoneNumber(), "");
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
        sKeyWord = edtSearch.getText().toString().trim();


        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sKeyWord.isEmpty()) {
                    showError(getString(R.string.add_card_please_enter_card_number), "");
                } else {
                    mPresenter.getDataCard(sKeyWord, LCConfig.getCustomerName(), LCConfig.getPhoneNumber(), "");
                }
            }

        });
    }


    @Override
    public void setData(CardPhysicalResponseDefault.OwnCardDto cardPhysicalResponseDefault) {
        MainMyCardsFragment.getInstance().addFragment(new MyCardFragment());
    }


    public class GenericTextWatcher implements TextWatcher {
        private View view;

        public GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            int id = view.getId();
            if (id == R.id.edtSearch) {
                if (text.length() == 4)
                    edtSearch1.requestFocus();
            } else if (id == R.id.edtSearch1) {
                if (text.length() == 4)
                    edtSearch2.requestFocus();
                else if (text.length() == 0)
                    edtSearch.requestFocus();
            } else if (id == R.id.edtSearch2) {
                if (text.length() == 4)
                    edtSearch3.requestFocus();
                else if (text.length() == 0)
                    edtSearch1.requestFocus();
            } else if (id == R.id.edtSearch3) {
                if (text.length() == 4)
                    edtSearch4.requestFocus();
                else if (text.length() == 0)
                    edtSearch2.requestFocus();
            } else {
                if (text.length() == 0)
                    edtSearch3.requestFocus();
            }
//            sKeyWord = edtSearch.getText().toString() + edtSearch1.getText().toString() + edtSearch2.getText().toString() + edtSearch3.getText().toString() + edtSearch4.getText().toString();
            sKeyWord = edtSearch.getText().toString() + " " + edtSearch1.getText().toString() + " " + edtSearch2.getText().toString() + " " + edtSearch3.getText().toString() + " " + edtSearch4.getText().toString();
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }
}
