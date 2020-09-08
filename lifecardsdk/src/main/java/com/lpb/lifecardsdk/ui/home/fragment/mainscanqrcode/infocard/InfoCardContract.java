package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.infocard;
import com.lpb.lifecardsdk.data.model.response.default_.ListServiceInfoResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface InfoCardContract {
    interface View extends BaseView {
        void setDataCard(List<ListServiceInfoResponse.OwnServiceDto> data);
        void setDataCard(ListServiceInfoResponse data);

    }

    interface Presenter extends BasePresenter {
        void getdata(String cardNo);
    }
}
