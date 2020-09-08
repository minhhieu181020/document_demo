package com.lpb.lifecardsdk.ui.usermanager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.UserManagerResponse;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.base.DialogClickListener;
import com.lpb.lifecardsdk.ui.usermanager.adapter.UserManagerAdapter;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.List;

/**
 * Created by vannh.lvt on 27/07/2020
 */
public class UserManagerFragment extends BaseDataFragment<UserManagerPresenter> implements UserManagerContract.View {
    private ImageView imgBack;
    private TextView tvCardName;
    private TextView tvCardNumber;
    private EditText edtPhoneNumber;
    private EditText edtName;
    private Button btnAddUser;
    private LinearLayout llListUser;
    private ImageView imgContact;
    private UserManagerAdapter userManagerAdapter;
    private String cardName, cardNumber, cardNumberDisplay;
    private boolean canCardShare;
    private Integer userNumber;

    private final int REQ_CODE_ASK_PERMISSION = 69;
    private final int REQ_CODE_CONTACT_PERMISSION = 70;
    private final int REQ_CODE_READ_CONTACT = 71;

    private boolean openAppContact;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_user_manager;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);
        TextView tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_user_manager_title_toolbar));
        tvCardName = view.findViewById(R.id.tvCardName);
        tvCardNumber = view.findViewById(R.id.tvCardNumber);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumber);
        edtName = view.findViewById(R.id.edtName);
        btnAddUser = view.findViewById(R.id.btnAddUser);
        imgContact = view.findViewById(R.id.imgContact);
        llListUser = view.findViewById(R.id.llListUser);
        RecyclerView rvListUser = view.findViewById(R.id.rvListUser);

        userManagerAdapter = new UserManagerAdapter(mActivity);
        rvListUser.setNestedScrollingEnabled(false);
        rvListUser.setLayoutManager(new LinearLayoutManager(mActivity));
        rvListUser.setAdapter(userManagerAdapter);
    }


    @Override
    protected void initData() {
        mPresenter = new UserManagerPresenter(mActivity, this);
        cardName = getArguments().getString(Constants.BundleConstants.CARD_NAME);
        cardNumber = getArguments().getString(Constants.BundleConstants.CARD_NUMBER);
        cardNumberDisplay = getArguments().getString(Constants.BundleConstants.CARD_NUMBER_DISPLAY);
        canCardShare = getArguments().getBoolean(Constants.BundleConstants.CAN_SHARE_CARD, false);
        userNumber = getArguments().getInt(Constants.BundleConstants.USER_NUMBER);

        if (!canCardShare) {
            btnAddUser.setTextColor(getResources().getColor(R.color.lifecardsdk_gray1));
            btnAddUser.setBackgroundResource(R.drawable.lifecardsdk_round_button_gray);
        }

        tvCardName.setText(cardName);
        tvCardNumber.setText(cardNumberDisplay);
        mPresenter.getListUser(cardNumber);
        requestContactPermission();
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });

        userManagerAdapter.setOnClickListener(new UserManagerAdapter.OnClickListener() {
            @Override
            public void onClick(final UserManagerResponse.OwnerDto item) {
                if (!canCardShare) {
                    Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_delete_user_case_card_expire), "");
                    return;
                }
                showDialogConfirm(getString(R.string.lifecardsdk_user_manager_delete_user), getString(R.string.lifecardsdk_user_manager_delete_user_desc, "<b>" + item.getMobilePhone() + "</b>"), getString(R.string.lifecardsdk_common_cancel), getString(R.string.lifecardsdk_common_accept), new DialogClickListener() {
                    @Override
                    public void close() {

                    }

                    @Override
                    public void allow() {
                        mPresenter.deleteListUser(cardNumber, item.getMobilePhone());
                    }
                }, false, false);

            }
        });

        btnAddUser.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (!canCardShare) {
                    Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_card_expire), "");
                    return;
                }
                final String custName = edtName.getText().toString().trim();
                final String custPhone = edtPhoneNumber.getText().toString().trim();
                if (StringUtils.isEmpty(custName) || StringUtils.isEmpty(custPhone)) {
                    showError(getString(R.string.lifecardsdk_user_manager_empty_name_or_phone), "");
                } else {
                    if (StringUtils.checkPhonePattern(custPhone)) {
                        showDialogConfirm(getString(R.string.lifecardsdk_user_manager_add_user), getString(R.string.lifecardsdk_user_manager_add_user_desc, StringUtils.boldString(cardNumberDisplay), StringUtils.boldString(custPhone)), getString(R.string.lifecardsdk_common_cancel), getString(R.string.lifecardsdk_common_accept), new DialogClickListener() {
                            @Override
                            public void close() {

                            }

                            @Override
                            public void allow() {
                                mPresenter.addListUser(cardNumber, custName, custPhone);
                            }
                        }, false, false);

                    } else {
                        showError(getString(R.string.lifecardsdk_buy_card_wrong_format_phone), "");
                    }

                }
            }
        });
        imgContact.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                openAppContact = true;
                requestContactPermission();
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(Constants.Actions.CHANGE_USER_NUMBER);
        intent.putExtra(Constants.BundleConstants.USER_NUMBER, userNumber);
        LocalBroadcastManager.getInstance(mActivity).sendBroadcast(intent);

        Intent intent2 = new Intent(Constants.Actions.REFRESH_LIST_MY_CARD);
        intent2.putExtra(Constants.BundleConstants.USER_NUMBER, userNumber);
        LocalBroadcastManager.getInstance(mActivity).sendBroadcast(intent2);
    }


    @Override
    public void setDataContact(String phone, String name) {
        edtPhoneNumber.setText(phone);
        edtName.setText(name);
    }

    private void showSettingScreen() {
        showDialogConfirm(getString(R.string.lifecardsdk_common_notify), getString(R.string.lifecardsdk_user_manager_notify_grant_permission_contact),
                getString(R.string.lifecardsdk_common_cancel), getString(R.string.lifecardsdk_common_accept), new DialogClickListener() {
                    @Override
                    public void close() {

                    }

                    @Override
                    public void allow() {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getBaseActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQ_CODE_CONTACT_PERMISSION);
                    }
                }, false, false);
    }


    private void requestContactPermission() {
        int contactPermission = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_CONTACTS);
        if (contactPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQ_CODE_ASK_PERMISSION);
        } else {
            if (openAppContact) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, REQ_CODE_READ_CONTACT);
            }
            mPresenter.getAllDataContact();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_CONTACT_PERMISSION) {
            requestContactPermission();
        } else if (requestCode == REQ_CODE_READ_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {
                mPresenter.getDataOpenAppContact(data);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE_ASK_PERMISSION) {
            for (String permission : permissions) {
                if (permission.equals(Manifest.permission.READ_CONTACTS)) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        showSettingScreen();
                    } else {
                        if (openAppContact) {
                            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                            startActivityForResult(contactPickerIntent, REQ_CODE_READ_CONTACT);
                        }
                        mPresenter.getAllDataContact();
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void setListUser(List<UserManagerResponse.OwnerDto> listUser) {
        if (listUser == null || listUser.size() == 0) {
            llListUser.setVisibility(View.GONE);
            userNumber = 0;
            return;
        }
        userNumber = listUser.size();
        llListUser.setVisibility(View.VISIBLE);
        userManagerAdapter.setItems(listUser);
    }

    @Override
    public void refreshEditText() {
        edtName.setText("");
        edtPhoneNumber.setText("");
    }
}
