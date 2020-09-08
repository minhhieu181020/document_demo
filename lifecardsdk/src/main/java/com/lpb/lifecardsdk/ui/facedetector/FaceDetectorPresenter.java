package com.lpb.lifecardsdk.ui.facedetector;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.EKYCConfig;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.widget.facedetector.TVSDKUtil;
import com.lpb.lifecardsdk.widget.facedetector.TVSelfieImage;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vannh.lvt on 13/07/2020
 */
public class FaceDetectorPresenter implements FaceDetectorContract.Presenter {
    private Context mContext;
    private FaceDetectorContract.View mViewModel;

    FaceDetectorPresenter(Context mContext, FaceDetectorContract.View mViewModel) {
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
    public String convertBase64ImageLive(TVSelfieImage selfieImage) {
        Bitmap scaleBitmap = TVSDKUtil.resize(selfieImage.getImage(), EKYCConfig.getWidth(), EKYCConfig.getHeight());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaleBitmap.compress(Bitmap.CompressFormat.JPEG, EKYCConfig.getImageQuality(), byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    @Override
    public void faceVerify(String body) {

        final String functionName = Function.FunctionName.FACE_VERIFY;
        final String functionCode = Function.FunctionCode.FACE_VERIFY;

        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().faceVerify(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    mViewModel.faceVerifySuccess();
                    mViewModel.hideLoading();
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                    mViewModel.faceVerifyFail();
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                    mViewModel.faceVerifyFail();
                } else if (status.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                    mViewModel.faceVerifyFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
                mViewModel.faceVerifyFail();
            }
        });
    }
}
