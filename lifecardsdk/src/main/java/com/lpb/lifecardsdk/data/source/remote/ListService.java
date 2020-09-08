package com.lpb.lifecardsdk.data.source.remote;

import com.lpb.lifecardsdk.constant.RetRestApi;
import com.lpb.lifecardsdk.data.model.map.Map;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ListService {
    // API chỉ đường -- vannh
    @GET(RetRestApi.API_MAP)
    Call<Map> getMapDirection(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);

    // API tỉnh thành phố quận huyện phường xã -- vannh
    @POST(RetRestApi.API_AREA)
    Call<ResponseBase64> getArea(@Body RequestBase64 requestBase64);

    //API danh mục -- vannh
    @POST(RetRestApi.API_CATEGORY)
    Call<ResponseBase64> getCategory(@Body RequestBase64 requestBase64);

    // API nhà cung cấp -- vannh
    @POST(RetRestApi.API_PROVIDER)
    Call<ResponseBase64> getProvider(@Body RequestBase64 requestBase64);

    // API Danh sách thẻ chưa mua -- vannh
    @POST(RetRestApi.API_PACKAGE)
    Call<ResponseBase64> getPackage(@Body RequestBase64 requestBase64);

    // API Danh sách thẻ chưa mua -- vannh
    @POST(RetRestApi.API_PACKAGE_2)
    Call<ResponseBase64> getDefCard(@Body RequestBase64 requestBase64);


    // API Chi nhánh -- vannh
    @POST(RetRestApi.API_BRANCH)
    Call<ResponseBase64> getBranch(@Body RequestBase64 requestBase64);

    // API Chi tiết thẻ -- vannh
    @POST(RetRestApi.API_PACKAGE_DETAIL)
    Call<ResponseBase64> getPackageDetail(@Body RequestBase64 requestBase64);

    // API sinh mã đơn hàng -- vannh
    @POST(RetRestApi.API_INIT_PAYMENT)
    Call<ResponseBase64> getBill(@Body RequestBase64 requestBase64);

    // API cài đặt -- vannh
    @POST(RetRestApi.API_MOBILE_CONFIG)
    Call<ResponseBase64> getMobileConfig(@Body RequestBase64 requestBase64);

    // API xóa đăng ký khuôn mặt -- vannh
    @POST(RetRestApi.API_DELETE_FACE)
    Call<ResponseBase64> deleteFace(@Body RequestBase64 requestBase64);

    // API lấy danh sách người dùng chung -- vannh
    @POST(RetRestApi.API_USER_CARD_SHARE_LIST)
    Call<ResponseBase64> getUserShareCard(@Body RequestBase64 requestBase64);

    // API thêm người dùng chung -- vannh
    @POST(RetRestApi.API_ADD_USER_CARD_SHARE)
    Call<ResponseBase64> addUserCardShare(@Body RequestBase64 requestBase64);

    // API xóa gười dùng chung -- vannh
    @POST(RetRestApi.API_DELETE_USER_CARD_SHARE)
    Call<ResponseBase64> deleteUserCardShare(@Body RequestBase64 requestBase64);

    // API nạp hạn mức thẻ -- vannh
    @POST(RetRestApi.API_OWN_CARD_RECHARGE)
    Call<ResponseBase64> ownCardRecharge(@Body RequestBase64 requestBase64);

    // API lấy discount khi nạp mức thẻ -- vannh
    @POST(RetRestApi.API_DISCOUNT_RECHARGE)
    Call<ResponseBase64> getDiscountRecharge(@Body RequestBase64 requestBase64);

    // API chờ thanh toán nạp hạn mức thẻ -- vannh
    @POST(RetRestApi.API_PAYMENT_WAIT_RECHARGE)
    Call<ResponseBase64> paymentWaitRecharge(@Body RequestBase64 requestBase64);


    // API thanh toán -- vannh
    @POST(RetRestApi.API_PAYMENT_WAIT)
    Call<ResponseBase64> getPaymentWait(@Body RequestBase64 requestBase64);

    // API mô tả kết quả giao dịch -- vannh
    @POST(RetRestApi.API_PAYMENT_RESULT)
    Call<ResponseBase64> getPaymentResult(@Body RequestBase64 requestBase64);

    // API hướng dẫn thanh toán -- vannh
    @POST(RetRestApi.API_PAYMENT_GUIDE)
    Call<ResponseBase64> getPaymentGuide(@Body RequestBase64 requestBase64);

    // API sinh mã đơn hàng -- vannh
    @POST(RetRestApi.API_ORDER_INFOR)
    Call<ResponseBase64> getOrderInfor(@Body RequestBase64 requestBase64);

    // API hướng dẫn-- vannh
    @POST(RetRestApi.API_GUIDE)
    Call<ResponseBase64> getDataGuide(@Body RequestBase64 requestBase64);

    // API lấy thông tin cấu hình face verify-- vannh
    @POST(RetRestApi.API_EKYC_CONFIGURE)
    Call<ResponseBase64> getEKYCConfigure(@Body RequestBase64 requestBase64);

    // API face verify-- vannh
    @POST(RetRestApi.API_FACE_VERIFY)
    Call<ResponseBase64> faceVerify(@Body RequestBase64 requestBase64);

    /**fake*/
    // API query đơn hàng-- vannh
    @POST("lifecard-app/query-order/try-on/req")
    Call<ResponseBase64> queryOrder(@Body RequestBase64 requestBase64);

    // API  thanh toán -- vannh
    @POST("lifecard-app/payment-order/try-on/req")
    Call<ResponseBase64> paymentOrder(@Body RequestBase64 requestBase64);
    /**fake*/



    // API danh sách thẻ của tôi.
    @POST(RetRestApi.API_MY_CARD)
    Call<ResponseBase64> getListMyCard(@Body RequestBase64 requestBase64);

    // API Chi tiết thẻ của tôi -- hieudm
    @POST(RetRestApi.API_MY_CARD_DETAIL)
    Call<ResponseBase64> getMyCardDetail(@Body RequestBase64 requestBase64);

    // API Chi tiết gói thẻ của tôi -- hieudm
    @POST(RetRestApi.API_CARD_PACKGE_SERVICE_DETAIL)
    Call<ResponseBase64> getCardPackgeServiceDetail(@Body RequestBase64 requestBase64);

    // API Chi tiết lich sử dịch vụ thẻ của tôi -- hieudm
    @POST(RetRestApi.API_TRANSACTION_HISTORY)
    Call<ResponseBase64> getTransactionHistory(@Body RequestBase64 requestBase64);

    // API Mã qr của tôi -- hieudm
    @POST(RetRestApi.API_QR_PRESONAL2)
    Call<ResponseBase64> getDataQrPersonal(@Body RequestBase64 requestBase64);

    // API update thẻ vật lý -- hieudm
    @POST(RetRestApi.API_CARD_PHYSICAL)
    Call<ResponseBase64> getDataCardPhysical(@Body RequestBase64 requestBase64);

    // API thông tin the -- hieudm
    @POST(RetRestApi.API_CARD_PAID_DETAIL)
    Call<ResponseBase64> getinfocard(@Body RequestBase64 requestBase64);

    // API Quét Mã QR Thanh Toán -- hieudm
    @POST(RetRestApi.API_SERVICES_ByQRBIll)
    Call<ResponseBase64> getService_By_QrBill(@Body RequestBase64 requestBase64);

    // API Xác Nhận Quét QR Thanh Toán -- hieudm
    @POST(RetRestApi.API_PAYMENT_ByQRBIll)
    Call<ResponseBase64> getService_Payment_QrBill(@Body RequestBase64 requestBase64);

    // API Xác Nhận thanh toán VIPAY -- hieudm
    @POST(RetRestApi.API_URL_VIPAY)
    Call<ResponseBase64> get_url_ViPay(@Body RequestBase64 requestBase64);

    // API  Nhận kết quả Thanh Toán -- hieudm
    @POST(RetRestApi.API_RESULT_URL_VIPAY)
    Call<ResponseBase64> get_Result_url_ViPay(@Body RequestBase64 requestBase64);

    // API  đổi phương thức thanh toán -- hieudm
    @POST(RetRestApi.API_PAY_MENT_METHOD)
    Call<ResponseBase64> get_Payment_Method(@Body RequestBase64 requestBase64);

    // API danh sách thẻ của tôi tab mã của tôi.
    @POST(RetRestApi.API_MY_CODE_CARD)
    Call<ResponseBase64> getListMyCodeCard(@Body RequestBase64 requestBase64);
}
