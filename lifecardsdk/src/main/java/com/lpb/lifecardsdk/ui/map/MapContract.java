package com.lpb.lifecardsdk.ui.map;

import com.google.android.gms.maps.model.LatLng;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface MapContract {
    interface View extends BaseView {
        void setMapDirection(LatLng startLatLng,LatLng endLatLng, String startAddress, String endAddress,String point,boolean isAnimate);
    }

    interface Presenter extends BasePresenter {
        List<LatLng> decodePoly(String encoded);
        void getMapDirection(String start,String destination,String key);
    }
}
