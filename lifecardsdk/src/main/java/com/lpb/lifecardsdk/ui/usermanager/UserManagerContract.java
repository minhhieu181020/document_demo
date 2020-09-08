package com.lpb.lifecardsdk.ui.usermanager;

import android.content.Intent;

import com.lpb.lifecardsdk.data.model.Contact;
import com.lpb.lifecardsdk.data.model.response.default_.UserManagerResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

/**
 * Created by vannh.lvt on 27/07/2020
 */
public interface UserManagerContract {
    interface View extends BaseView {
        void setListUser(List<UserManagerResponse.OwnerDto> listUser);
        void refreshEditText();
        void setDataContact(String phone,String name);
    }

    interface Presenter extends BasePresenter {
        void getListUser(String cardNo);
        List<Contact> getAllDataContact();
        void addListUser(String cardNo, String custName, String custPhone);
        void getDataOpenAppContact(Intent intent);
        void deleteListUser(String cardNo, String custPhone);
    }
}
