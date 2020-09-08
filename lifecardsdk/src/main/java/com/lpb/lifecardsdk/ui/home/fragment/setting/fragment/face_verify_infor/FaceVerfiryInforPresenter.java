package com.lpb.lifecardsdk.ui.home.fragment.setting.fragment.face_verify_infor;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.FaceDeleteRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vannh.lvt on 17/07/2020
 */
public class FaceVerfiryInforPresenter implements FaceVerifyInforContract.Presenter {
    private Context mContext;
    private FaceVerifyInforContract.View mViewModel;

    FaceVerfiryInforPresenter(Context mContext, FaceVerifyInforContract.View mViewModel) {
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
    public void deleteFace() {
        if (!PresenterUtils.isNetworkConnected(mContext)) {
            mViewModel.showError(mContext.getString(R.string.lifecardsdk_cant_connect_internet), "");
            return;
        }
        mViewModel.showLoading(true);
        final String functionName = Function.FunctionName.DELETE_FACE;
        final String functionCode = Function.FunctionCode.DELETE_FACE;

        RequestBase64 requestBase64 = ReqApiUtils.createRequest(StringUtils.convertObjectToBase64(new FaceDeleteRequest(LCConfig.getPhoneNumber())), functionName, mContext);

        Repository.getInstance().deleteFace(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBase64> call, @NonNull Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    Exception.handleMessageSuccess(mContext, mContext.getString(R.string.lifecardsdk_face_detete_success));
                    mViewModel.backToHome(4);
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    assert responseBase64 != null;
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
            public void onFailure(@NonNull Call<ResponseBase64> call, @NonNull Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }
}
