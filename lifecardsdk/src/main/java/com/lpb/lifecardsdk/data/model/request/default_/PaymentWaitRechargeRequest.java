package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 14/08/2020
 */
public class PaymentWaitRechargeRequest {
    @SerializedName("amountDeposit")
    @Expose
    private Long amountDeposit;

    @SerializedName("amountPayment")
    @Expose
    private Long amountPayment;

    @SerializedName("cardNo")
    @Expose
    private String cardNo;


    @SerializedName("accountNo")
    @Expose
    private String accountNo;

    @SerializedName("defRechargeId")
    @Expose
    private Long defRechargeId;

    public PaymentWaitRechargeRequest(Long amountDeposit, Long amountPayment, String cardNo, String accountNo, Long defRechargeId) {
        this.amountDeposit = amountDeposit;
        this.amountPayment = amountPayment;
        this.cardNo = cardNo;
        this.accountNo = accountNo;
        this.defRechargeId = defRechargeId;
    }
}
