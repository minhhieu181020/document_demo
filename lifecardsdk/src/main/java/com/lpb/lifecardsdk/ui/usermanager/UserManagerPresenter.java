package com.lpb.lifecardsdk.ui.usermanager;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.Contact;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.AddUserRequest;
import com.lpb.lifecardsdk.data.model.request.default_.DeleteUserRequest;
import com.lpb.lifecardsdk.data.model.request.default_.UserManagerRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.UserManagerResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vannh.lvt on 27/07/2020
 */
public class UserManagerPresenter implements UserManagerContract.Presenter {
    private Context mContext;
    private UserManagerContract.View mViewModel;

    UserManagerPresenter(Context mContext, UserManagerContract.View mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getListUser(String cardNo) {
        if (PresenterUtils.isNetworkConnected(mContext)) {
            mViewModel.showLoading(true);
        } else {
            mViewModel.showError(mContext.getString(R.string.lifecardsdk_cant_connect_internet), "");
            return;
        }

        final String functionName = Function.FunctionName.USER_CARD_SHARE_LIST;
        final String functionCode = Function.FunctionCode.USER_CARD_SHARE_LIST;


        UserManagerRequest userManagerRequest = new UserManagerRequest(cardNo);
        String body = StringUtils.convertObjectToBase64(userManagerRequest);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getUserShareCard(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        UserManagerResponse userManagerResponse = new Gson().fromJson(text, UserManagerResponse.class);
                        mViewModel.setListUser(userManagerResponse.getOwnerDtos());
                        mViewModel.hideLoading();
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (status.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }

    @Override
    public List<Contact> getAllDataContact() {
        List<Contact> contacts = new ArrayList<>();
        ContentResolver cr = mContext.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Contact contact = new Contact(name, phoneNo);
                        contacts.add(contact);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        Log.e("getContactsList: ",contacts.toString() );
        return contacts;
    }

    @Override
    public void getDataOpenAppContact(Intent intent) {
        Uri contactUri = intent.getData();

        String[] pN = {ContactsContract.CommonDataKinds.Phone.NUMBER};

        String[] pNa = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};//idk

        assert contactUri != null;
        @SuppressLint("Recycle") Cursor cP = mContext.getContentResolver().query(contactUri, pN, null, null, null);
        assert cP != null;
        cP.moveToFirst();

        @SuppressLint("Recycle") Cursor cPa = mContext.getContentResolver().query(contactUri, pNa, null, null, null);
        assert cPa != null;
        cPa.moveToFirst();

        int p = cP.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String phoneNumber = cP.getString(p);

        int n = cPa.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        String name = cPa.getString(n);

        mViewModel.setDataContact(phoneNumber, name);
    }

    @Override
    public void addListUser(final String cardNo, String custName, String custPhone) {
        if (PresenterUtils.isNetworkConnected(mContext)) {
            mViewModel.showLoading(true);
        } else {
            mViewModel.showError(mContext.getString(R.string.lifecardsdk_cant_connect_internet), "");
            return;
        }
        final String functionName = Function.FunctionName.ADD_USER_CARD_SHARE;
        final String functionCode = Function.FunctionCode.ADD_USER_CARD_SHARE;
        AddUserRequest addUserRequest = new AddUserRequest(cardNo, custName, custPhone);
        String body = StringUtils.convertObjectToBase64(addUserRequest);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().addUserCardShare(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    getListUser(cardNo);
                    mViewModel.refreshEditText();
                    Exception.handleMessageSuccess(mContext, mContext.getString(R.string.lifecardsdk_user_manager_add_user_success));
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (status.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }

    @Override
    public void deleteListUser(final String cardNo, String custPhone) {
        if (PresenterUtils.isNetworkConnected(mContext)) {
            mViewModel.showLoading(true);
        } else {
            mViewModel.showError(mContext.getString(R.string.lifecardsdk_cant_connect_internet), "");
            return;
        }
        final String functionName = Function.FunctionName.DELETE_USER_CARD_SHARE;
        final String functionCode = Function.FunctionCode.DELETE_USER_CARD_SHARE;

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest(cardNo, custPhone);
        String body = StringUtils.convertObjectToBase64(deleteUserRequest);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().deleteUserCardShare(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    getListUser(cardNo);
                    Exception.handleMessageSuccess(mContext, mContext.getString(R.string.lifecardsdk_user_manager_delete_user_success));
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (status.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }
}
