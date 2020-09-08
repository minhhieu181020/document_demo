package com.lpb.lifecardsdk.ui.map;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.map.Map;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapPresenter implements MapContract.Presenter {
    private Context mContext;
    private MapContract.View mViewModel;

    MapPresenter(Context mContext, MapContract.View mViewModel) {
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
    public List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(p);
        }
        return poly;
    }

    @Override
    public void getMapDirection(String start, final String destination, String key) {
        final String functionName = Function.FunctionName.GET_MAP_DIRECTION;
        final String functionCode = Function.FunctionCode.GET_MAP_DIRECTION;
        Repository.getMapDirections().getMapDirection(start, destination, key).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                LatLng start = new LatLng(response.body().getRoutes().get(0).getLegs().get(0).getStartLocation().getLat(), response.body().getRoutes().get(0).getLegs().get(0).getStartLocation().getLng());
                LatLng end = new LatLng(response.body().getRoutes().get(0).getLegs().get(0).getEndLocation().getLat(), response.body().getRoutes().get(0).getLegs().get(0).getEndLocation().getLng());
                String startAddress = response.body().getRoutes().get(0).getLegs().get(0).getStartAddress();
                String endAddress = response.body().getRoutes().get(0).getLegs().get(0).getEndAddress();
                String point = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                mViewModel.setMapDirection(start,end,startAddress,endAddress,point,true);
                mViewModel.hideLoading();
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t,mContext,functionName,functionCode);
            }
        });
    }
}
