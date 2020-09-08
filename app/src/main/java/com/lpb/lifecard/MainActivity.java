package com.lpb.lifecard;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.constant.RetRestApi;
import com.lpb.lifecardsdk.ui.home.LCHomeActivity;

public class MainActivity extends AppCompatActivity {

    //url
    private static final String URL_PROD="https://service.lifecard.vn/";
    private static final String URL_TEST="https://lifecardtest.viviet.vn/";

    //token test
    private static final String TOKEN_TEST_CARGO="eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.GsHvc-8OefGRoN5FZPgHTz9P29NyWTb44s_LzdGRQo7ow3FNIIk9Vv8NFYxDSHeOevknSG891jKa83vMeNOA1p2rgAlVpwlY3VzsWld9QVnu3timrhicmynney9RjDEHSreeBLK919Ei9J0OUwyS-TMuCaDSlPqTKWnQPepRiAJv5eoVBQ2aR72ffAQFWyHQQ01Jq8JsfbHbePt7fSNMByz44SfhsVuKAgf9UZKmcPWIovsUWsygKhJ5XBJQeVfibGXDXNVSl7JV6ews0oNyMHq85PlFuTHUUsgBZRNyslrMKxQHs2bR8VPmDmSIfndWcAGcYeQL44J-wymN_5z1ZA.RfVbT4K2jbbeU3j8.Hc38QUh-1gT-te5cMcfJiTI9PBmLuN8NQKs0P6lf8X2rHtTUpPjx7t3Th1WCENlW7LGLgiRgoAr-KtkEgNFh-ZCv0weDTKrhvER0WGnaORxFjo_M2QVOMmKstTNaTzJTCqA7AsVGZ0s5_L-jFQ-ZPPTZAiVQWKzaMHxCDox2QrhGc_FCiSoGrPA0h2cSLCASmBPClk3og8Kaom0BVB0y1IFDZs96WfzQRLlBDQshqt8jZxLIQQTJ7XAs0ZjCfN11FZCAbAFWg7VZ2fexdaLve-2N21UL_QO6x3TZ70MCHAt6z2illMlHD1mkN9d-yyLuWIX6ZQ1ZGNxsHe3XIbiBdtwjisdIxHPUHtQcSHjLsO6bIazor5E-nfssRcKS5YLEF_UUbzTuI5UaDYaYOEl3RAsJTzdACIR59MENjrjC52VGIgwYLVGKeUOs05oej2B7q2yo7KYKKz5orTSeE0MxOU-zbkBtV0plrgUWEFFt8ud5KYXbAorh9R9beZmgoLF7TfCyvEn-r65CrV7rrto0MHovS4Qc8y7r9KAuNMOcoJxXAjmC_59Qsis6yTrfGRjzGhu7zHHvk4d5BtQrqY_qquju42F7rlE2YLgViykn2WFj41QWRvTP_gOpBqLAkgTnm6a6liwjlUn89CoEywJcECZOZPPoi9ZF_c1r721rz75Mqy63j-aIjGkPa5bGeWY_g4exPLJisjG2txQFF9737IWelJLosxnCAXi6vkpqb1jhqsC_DXHAaWrPJas7w4hWNXCfacdJVtJbUysJcH8JehrJzXDgzv5f5fo4iUo3qBSLOiFt1PAw5ztrWzunGL07d4KxhH4pcrPrlVnPqCw9rZCycmrkZn_nWm3BcahC07uMhg_AwbJJYZ_rFoM.psevOPdyiO-S_dNsct81Lw";
    private static final String TOKEN_TEST_HIGHLANDS="eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.VSkCZFFk22VRqJrOsQ69LHFntEeClrpphUeQ-KWLQ5GkDJeQ02xyPq_uBDrwdBS5z2yig980Hn2CcXiFgDE-VH_tDI35UhFMoftD2LkBI4-QIeiW_vihu_1b3cWPXL_pdN86WRA8kiQZfB9yBeAAO7OpJk0Cnnm7YD8ntYJX8ySQFDwN99iUrt5dlcBR15tKOKOIFeLr8nIr1pH5FyaUct7cGx8KlYt_l8luJ9TGrpH7d8lZsSm-6BXxiz4iixRsuYB37rYiAvTwmR5J-THlLTQXEJPnlYCa8EC2Tp-_ozgvKUls7aWAi4o7ReviVhkroxW5MlapDoD_5K0sUslgWw.E8GYI5gjwA3PD5in.-INy8Fz5bn4e6LbH1bbX3qAfOoeVxsEaAzna6UgV-7TnQohwTCtzPMEr7nrzFQXFQH7SAr-pnDPyZ0N9QhK5wCE0OIcabutRGFVS_mdX9tGSlsk08accos7vPOO6r70gAvaaYvJpJoWCTH4QfiL2K-3Lsayr2RFtvVx_WEbo_8BLY1q7_U59REqNdSrxMgAm97aIajsy1lDyzOCPJ3BT-xNmMmgqw905HcFwpdwCweKwWtsCPN2vcMdL6prqL3vO9XWaWRoYnXV80-TQql0D1_brvEj6UOEkT4mvMrT9zh4JxXUPXjm9fXfGlilwNqjucUUPinr21ZI0Zsg4Eg_FnjTd15GRB7JzzyCmj1smUct-WOqjGyNEkzigOhcFRcg88svmz3mJdqttytqofOnqnc5WlLe-yJ8fB24P5rafO13Lvel_xZH3uFTC-Tab3Wdp4lyO8qQxTUQJZ36QBHDpV2J3Tz8Z2eNNoD3XmQMr2cZe91SjJSubD0tN_sw21E6lvlX0J2T8xfRmBTxH_5VGF3yuHxIA-HfTY8pkBZc21K0hCrO6Y25NZxuxn2tOwbsHoWpR3WPVDenwSkzm4nUx-QzXBtrbXYQ-iXsTYHhOZuT-c7IcWtGROZUAw9yAOMmWGvRYLu-QrYfYvmbYCiQHF_9YlJJskKTbwodLxhU0G4XyMG1Nr2adTS31woequjq0fPw8iAld3_mHFbeYP6AsflgvDjnlnKoETRIAhVt8THmCGs2p7AheGHIU2eWpWWLivbcUh-sWOzVdmMlGWu5bFVE_pHWk8kBpDkHRuu_mi9CGt-3PTbF0vQPvL_F6S3tWec7v6NH5xzhLewHOPHTFiGx8Pljqf5Z-Aeagsa08ibOXFiGo34_hZw.ojgQ6upWV3_i8XMXjdChew";
    private static final String TOKEN_TEST_MAI_LINH="Bearer eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.gszEFP1gfOz7XTGOzjHG7aUtDseyydZuXtbjJeORyuGA1-MGnq0TsVH9ETxFr6eGSgu6xHIWe7ZmkUEwQfUXyDyH99btQu9Up0p0OUHnf5JoXygGHvOJVioEvth5TQj4UdohV8qtRlXQWjKOLDujx7YE3lGqiaqWq5JQcbGD6DeU2TbkDcfe9bUUAgz8O5q91vpFIVRFwAWYTjhTTbSfxebc2U6Szt_LQL2PgOXRtxONlQjDzqUfIaZ3NgFfhVC9VK88VbYQyVNBruLRD_j7lGwkpzzMSziH2vepNk5m4hfghtg9mt2hBEeE-l1PrrizOuZCEErTyk6FfhNsB-pi-Q.UcymxO0TOm_wSJ22.xKvtJ3u_nEKuoNVX8KyyQlH1f5dlbbqiYcYmnjnZXQ-gC8v2e6docEIVuMxIdY1Hz1Wa4A0YVQcvzNk9zPq0KjvDCn5xlRdGmp-I3VAt1IM1KiCcVxXxTFcHEDHfwk__-zXn4TOPEpE_M_7md7PdyzO8t3TJMOf_4yDkO5K70FKnvJVY1DY70sOmHS569168k_rmxZvxYs6uPsejNL0kcz_A05u-Woi8S8mDESKzb2ek4Xx_OGlrL4w_JRz1WTMdvq_t5_wjJ41RD8ousb5dJr2G-Z8PJ8oobUSdXbm1w3Chao6stjOFT39tLgBDr_8LlcixzW-ffkMh-hPS5rtXSsL4U-eaXWs74A_ZmRoH0S2Waf64bmAu9v7EKT1kc5schWyg554UMt4WG93f-HOWaPf_VP7EPLOnzbz4qAy63yjVKUK3LS2lHbeSBgHbXJMcb4p2xKI1g0GQOYCS9_pzhqDdiyVaCEKOm2X58o7RB7VfLMfFj-5E8HKQdTqowb9AbUu4eJiiafCVsCdk3L0DvTA2ogPiXp_psFjsAw5AMjNMvb4Gzcg2mdkSqe8JmYeCSdJpRJYRbzfpD_92PTk46BVmbW2xpuhnMRC_iF8wBUzT0e7xdberCNoBOhkKO_SM9O68IXBGGYpsdabyDNC_r-RzVvzJdKVJmuON9Npx-CV_JkzU5tx0QrXCiL23ygVcHOubQipxguGaV-Ukny5WpT-ePRk4z0THdk-2iUk2hQMdsxNDyd2hStcRgnZLAVERw7Xgojt5QAjyJ346bjF6f6y5Z9QO_tJYlnwcoInJK_3fq7penT33j8m6Yhr5HzmSTOUxa4J_qs6jdoHuwH67qgj3QBrQlpU6kMto65VfcquUpN83qy5753si.0BZfkQ3xD67x6sMpulvm6w";
    private static final String TOKEN_TEST_VIVIET="eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.J10F0L9-nMLzJTDgnzNibKGLYD_fX92pah1--zn7YD7NzYOxp349XB1pO0uZRo8QDt4YgKM1Joas1iCDdUcmz29GPJPLTTzlDk4s6XGPUhk-ExCIy6HGn1ByFIbF3mgo3WysMTOFX1gw2sl8jPByMqTEmEt6aQydy5K2GJ8_t4CfI_kiTjmxc4_qZAp8u92uyPri7kIeScPPAPS3sx0ORl7latcm272AIW547pH4o-bFlUl4dM5MWWBTNQbc4h7U2BSDGNgIXo3D7XcMH_nddWPvMb6XpAKgAFfVP8ukhxu9T-UKQra0wVyIrb_TXZ0YzDstOSQJi4lm46KrtDUabQ.Zs7yzqm4VWUF6jsk.tpq4WZKm8DhDjdNOJ5D1tA67XJQ-12GaxWfi9HIxhZp30BFmiZMsCDZ9PD10BiPMn2ewloU-t3ONIo24QMF8hToAANdhDhrTD80R2ClCta5lhu7lVCzZ7tBc3O6_RHDn5LgRP3n3VBd4llJy5R9g-O4G0kWBJk9k-cpYFwwGVFoeu0VSmKQgH4DLEDwkswz2psGxJElSgw2TWRjo7A8x967jt0R3FcZsB3FHDOlLBpYkbTGYkP49Y05HUDKTVqO76c1SlCEy95Sl8C7191yJiPbPzbVCPGQ2TAWA3SlUg9f5LiPdOl-kNS8YWtJ5d-PcXYmL0J3Iw4T5TwFAvR3QyXUqg2i_J6semKEcgC0v4tSBpeNzW4u-0n02on5He9C_uPQchyQSgGv7YqQgR7se5A7yEhNseABE3N6P_k2c98ayyHm7iP8Zz0kXagKIptpz1mBS2cxeqNPue1Twn0DtRyBpOVrHvIg4xwb9B3mdKglekolG4vzjNxmoYIumP9M200kbnjZy18V1m_nXe1k3-sTC1NQcvrodU4unked-kZsr0dg-uZn6qq25YSVXltHGIs2flUNXKFsw_wyBb4j9TQFqfp9M_bJGkIBmTJukeUHjk3ZQ222vW4vz3KiCX1y9G_M9lpbtoSl1oWvedm0en5PGSfKI5gzMIZnB9iYqEbfWoyz3LHbWdU8XEAtdZwj5fJAvoW_49UzxI-7uCK04Hu0ZueLuG0wMzR-TpsnseZuMSxDmqJGA41hSUS_i5Gunu3Pr8b4nMqJECWnlxVOKhy2DAjYXVVK9x0tTTlRmxmhAWS390ckNB9qFeYXvXGK2yw74s41lzUYxUhiR5n76HCtdj13DsYMo7w99iLRRYtzTduJUHT2tgGGrFrNQqDza.uhXatWJ4eUMdK0EONMJw4Q";
    //token prod
    private static final String TOKEN_PROD_CARGO="";

    private EditText edtCustomerName;
    private EditText edtPhoneNumber;
    //    private CheckBox chkPayment;
    private Button btnOpenSDK;
    private ProgressBar progressbar;
    private EditText edtRefCustID;
    private static SharedPreferences spLogin;
    private static SharedPreferences.Editor editLogin;
    private RadioButton RdMailinh;
    private RadioButton RdHighLand;
    private RadioButton RdPayment;
    private RadioButton RdNotPayment;
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spLogin = getSharedPreferences("lifecard_customer_login",
                Context.MODE_PRIVATE);
        editLogin = spLogin.edit();

        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnOpenSDK = findViewById(R.id.btnOpenSDK);
        progressbar = findViewById(R.id.progressbar);
        edtRefCustID = findViewById(R.id.edtRefCustID);
        RdMailinh = (RadioButton) findViewById(R.id.RdMailinh);
        RdHighLand = (RadioButton) findViewById(R.id.RdHighLand);
        RdPayment = (RadioButton) findViewById(R.id.RdPayment);
        RdNotPayment = (RadioButton) findViewById(R.id.RdNotPayment);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        edtPhoneNumber.setText(spLogin.getString("login_phone_number", ""));
        edtCustomerName.setText(spLogin.getString("login_customer_name", ""));
        edtRefCustID.setText(spLogin.getString("login_customer_ref_cust_id", ""));
        if (RetRestApi.API_END_POINT_RET.equals(URL_PROD)) {
            RdHighLand.setVisibility(View.GONE);
            RdMailinh.setVisibility(View.GONE);
            RdPayment.setVisibility(View.GONE);
            RdNotPayment.setVisibility(View.GONE);
            editLogin.putString("login_payment_gateways", "RdNotPayment");
            editLogin.commit();
        }
        String status = spLogin.getString("login_payment_gateways", "RdPayment");

        if (status.equalsIgnoreCase("RdMailinh")) {
            RdMailinh.setChecked(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                RdMailinh.setTextColor(getColor(R.color.app_colorPrimary));
            }
        } else if (status.equalsIgnoreCase("RdHighLand")) {
            RdHighLand.setChecked(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                RdHighLand.setTextColor(getColor(R.color.app_colorPrimary));
            }
        } else if (status.equalsIgnoreCase("RdPayment")) {
            RdPayment.setChecked(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                RdPayment.setTextColor(getColor(R.color.app_colorPrimary));
            }
        } else if (status.equalsIgnoreCase("RdNotPayment")) {
            RdNotPayment.setChecked(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                RdNotPayment.setTextColor(getColor(R.color.app_colorPrimary));
            }
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RdMailinh:
                        RdMailinh.setTextColor(getColor(R.color.app_colorPrimary));
                        RdHighLand.setTextColor(getColor(R.color.lifecardsdk_black));
                        RdPayment.setTextColor(getColor(R.color.lifecardsdk_black));
                        RdNotPayment.setTextColor(getColor(R.color.lifecardsdk_black));
                        break;
                    case R.id.RdHighLand:
                        RdMailinh.setTextColor(getColor(R.color.lifecardsdk_black));
                        RdHighLand.setTextColor(getColor(R.color.app_colorPrimary));
                        RdPayment.setTextColor(getColor(R.color.lifecardsdk_black));
                        RdNotPayment.setTextColor(getColor(R.color.lifecardsdk_black));
                        break;
                    case R.id.RdPayment:
                        RdMailinh.setTextColor(getColor(R.color.lifecardsdk_black));
                        RdHighLand.setTextColor(getColor(R.color.lifecardsdk_black));
                        RdPayment.setTextColor(getColor(R.color.app_colorPrimary));
                        RdNotPayment.setTextColor(getColor(R.color.lifecardsdk_black));
                        break;
                    case R.id.RdNotPayment:
                        RdMailinh.setTextColor(getColor(R.color.lifecardsdk_black));
                        RdHighLand.setTextColor(getColor(R.color.lifecardsdk_black));
                        RdPayment.setTextColor(getColor(R.color.lifecardsdk_black));
                        RdNotPayment.setTextColor(getColor(R.color.app_colorPrimary));
                        break;
                }
            }
        });
        btnOpenSDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSDK();
            }
        });

        Bungee.fade(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressbar.setVisibility(View.GONE);
        btnOpenSDK.setEnabled(true);
    }

    public void openSDK() {
        btnOpenSDK.setEnabled(false);
        progressbar.setVisibility(View.VISIBLE);
        String phoneNumber = edtPhoneNumber.getText().toString().trim();// số điện thoại người dùng (bắt buộc)
        String customerName = edtCustomerName.getText().toString().trim();// tên người dùng (bắt buộc)
        String refCustID = edtRefCustID.getText().toString().trim();
        editLogin.putString("login_phone_number", phoneNumber);
        editLogin.putString("login_customer_name", customerName);
        editLogin.putString("login_customer_ref_cust_id", refCustID);

        LCConfig.setPhoneNumber(phoneNumber);
        LCConfig.setCustomerName(customerName);
        LCConfig.setRefCustID(refCustID);
        if (RetRestApi.API_END_POINT_RET.equals(URL_PROD)) {
            LCConfig.setChannelCode("CARGO_APP");
            LCConfig.setSystemCode("BTEK");
            LCConfig.setAuthorization(TOKEN_PROD_CARGO);
            editLogin.putString("login_payment_gateways", "RdNotPayment");
        } else if (RetRestApi.API_END_POINT_RET.equals(URL_TEST)) {
            if (RdMailinh.isChecked()) {
                LCConfig.setChannelCode("MLTX_APP");
                LCConfig.setSystemCode("MLG");
                LCConfig.setAuthorization(TOKEN_TEST_MAI_LINH);
                editLogin.putString("login_payment_gateways", "RdMailinh");
            }
            if (RdHighLand.isChecked()) {
                LCConfig.setChannelCode("HL_APP");
                LCConfig.setSystemCode("HLCF");
                LCConfig.setAuthorization(TOKEN_TEST_HIGHLANDS);
                editLogin.putString("login_payment_gateways", "RdHighLand");
            }
            if (RdPayment.isChecked()) {
                LCConfig.setChannelCode("VIVIET_APP");
                LCConfig.setSystemCode("VIVIET");
                LCConfig.setAuthorization(TOKEN_TEST_VIVIET);
                editLogin.putString("login_payment_gateways", "RdPayment");
            }
            if (RdNotPayment.isChecked()) {
                LCConfig.setChannelCode("CARGO_APP");
                LCConfig.setSystemCode("BTEK");
                LCConfig.setAuthorization(TOKEN_TEST_CARGO);
                editLogin.putString("login_payment_gateways", "RdNotPayment");
            }
        }

        editLogin.commit();


        Intent intent = new Intent(this, LCHomeActivity.class);
        startActivity(intent);
    }
}
