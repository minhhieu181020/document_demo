package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.scanqr;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.PackageDetailRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.ListServicePaymentQrBillResponse;
import com.lpb.lifecardsdk.data.model.response.default_.PackageDetailResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.BinaryBitmap;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.ChecksumException;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.FormatException;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.LuminanceSource;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.NotFoundException;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.RGBLuminanceSource;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.Reader;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.Result;
import com.lpb.lifecardsdk.widget.zxing.zxing.common.HybridBinarizer;
import com.lpb.lifecardsdk.widget.zxing.zxing.qrcode.QRCodeReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScanQRPresenter implements ScanQRContract.Presenter {

    private Context mContext;
    private ScanQRContract.View mViewModel;

    ScanQRPresenter(Context context, ScanQRContract.View viewModel) {
        mContext = context;
        mViewModel = viewModel;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public String decodeQRImage(String path) {
        Bitmap bMap = BitmapFactory.decodeFile(path);
        String decoded;
        int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(),
                bMap.getHeight());
        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(),
                bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new QRCodeReader();
        try {
            Result result;
            result = (Result) reader.decode(bitmap);
            decoded = result.getText();
        } catch (NotFoundException e) {
            decoded =  null;
        } catch (ChecksumException e) {
            decoded =  null;
        } catch (FormatException e) {
            decoded =  null;
        }
        return decoded;
    }

    @Override
    public String getPathFromUri(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = mContext.getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        assert cursor != null;
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    @Override
    public void getDataCardInformation(final String cardCode) {
        final String functionName = Function.FunctionName.CARD_INFORMATION_QR;
        final String functionCode = Function.FunctionCode.CARD_INFORMATION_QR;

        String body = StringUtils.convertObjectToBase64(new PackageDetailRequest(cardCode));
        RequestBase64  requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getPackageDetail(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName,functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        PackageDetailResponse packageDetailResponse = new Gson().fromJson(text, PackageDetailResponse.class);
                        if (null == packageDetailResponse.getCost()) {
                            mViewModel.setview(false, cardCode);
                            Log.e("ScanQR", "onResponse: False");
                        }
                        if (null != packageDetailResponse.getCost()) {
                            mViewModel.setview(true, cardCode);
                            Log.e("ScanQR", "onResponse: TRUE" + packageDetailResponse.getCost());
                        }
                        Log.e("ScanQR", "" + packageDetailResponse.getCost());


                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e,mContext,functionName,functionCode);
                    }
                } else if (s.equals(Exception.Type.KNOWN)) {
                    mViewModel.setview(false, cardCode);
                    Log.e("ScanQR", "onResponse: False");
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (s.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                     mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (s.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                     mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }

            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t,mContext,functionName,functionCode);
            }
        });
    }

    @Override
    public void getServicesByQRBIll(final String qrcode) {

        final String functionName = Function.FunctionName.GET_SERVICE_BY_QR_BILL;
        final String functionCode = Function.FunctionCode.GET_SERVICE_BY_QR_BILL;

        String body = StringUtils.convertStringToBase64(qrcode);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getService_By_QrBill(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName,functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        ListServicePaymentQrBillResponse listservicePaymentQrBillResponse = new Gson().fromJson(text, ListServicePaymentQrBillResponse.class);
                        mViewModel.setviewServicesByQRBIll(true, listservicePaymentQrBillResponse);
                        Log.e("ScanQR", "" + text.toString());
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e,mContext,functionName,functionCode);
                    }
                } else if (s.equals(Exception.Type.KNOWN)) {
                    Log.e("ScanQR", "onResponse: False");
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.setviewServicesByQRBIll(false, null);
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (s.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.setviewServicesByQRBIll(false, null);
                     mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (s.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.setviewServicesByQRBIll(false, null);
                     mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }

            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t,mContext,functionName,functionCode);
            }
        });
    }
}




