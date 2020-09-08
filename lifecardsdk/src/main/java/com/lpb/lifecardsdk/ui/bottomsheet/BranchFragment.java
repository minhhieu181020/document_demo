package com.lpb.lifecardsdk.ui.bottomsheet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.Branch;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.AreaRequest;
import com.lpb.lifecardsdk.data.model.request.default_.BranchRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.BranchResponse;
import com.lpb.lifecardsdk.data.model.restheader.Location;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.bottomsheet.adapter.BranchAdapter;
import com.lpb.lifecardsdk.ui.payment.case2.notify.LCNotifyActivity;
import com.lpb.lifecardsdk.util.LocationUtil;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vannh.lvt on 02/06/2020
 */
public class BranchFragment extends BottomSheetDialogFragment {
    private RecyclerView rvContent;
    private BranchAdapter branchAdapter;
    private ProgressBar progressBar;
    private String providerID;
    private RelativeLayout rlContent;
    private LinearLayout llTitle;
    private boolean isFirstOpenLocation, isFirstGrantLocation;

    private int pageIndex = 0, rowCount = 1000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.LifeCardSDK_AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lifecardsdk_location_bottom_sheet, container, false);
        rvContent = view.findViewById(R.id.rvContent);
        progressBar = view.findViewById(R.id.progressBar);
        rlContent = view.findViewById(R.id.rlContent);
        llTitle = view.findViewById(R.id.llTitle);
        llTitle = view.findViewById(R.id.llTitle);

        int width = PresenterUtils.getWidthScreen(getActivity());
        int heightContainer = (int) (PresenterUtils.getHeightScreen(getActivity()) * Config.RATIO_HEIGHT_BOTTOM_SHEET);
        int heightContent = PresenterUtils.getHeightScreen(getActivity()) - llTitle.getHeight() - ((int) (getResources().getDimensionPixelSize(R.dimen.lifecardsdk_default_toolbar_height) * getActivity().getResources().getDisplayMetrics().density));

        //rlContent.setLayoutParams(new RelativeLayout.LayoutParams(width, heightContainer));
        //rvContent.setLayoutParams(new RelativeLayout.LayoutParams(width, heightContainer - llTitle.getHeight() - ((int) (getResources().getDimensionPixelSize(R.dimen.lifecardsdk_default_toolbar_height) * getActivity().getResources().getDisplayMetrics().density))));

        branchAdapter = new BranchAdapter(getActivity());
        rvContent.setAdapter(branchAdapter);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();

        providerID = String.valueOf(bundle.getInt(Constants.BundleConstants.PROVIDER_ID, Config.DEAULT_VALUE_INT));
        if (providerID.equals(String.valueOf((Config.DEAULT_VALUE_INT)))) {
            Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_realtime_error), "");
            hideLoading();
            return;
        }

        if (PresenterUtils.isNetworkConnected(getActivity())) {
            if (providerID.equals(String.valueOf((Config.DEAULT_VALUE_INT)))) {
                Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_realtime_error), "");

                return;
            }
            if (PresenterUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getActivity())) {
                if (!PresenterUtils.isLocationEnabled(getActivity())) {
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_branch_open_gps), "");
                    getDataBranch("", "", providerID, pageIndex, rowCount);
                } else {
                    LocationUtil.requestSingleUpdate(getActivity(),
                            new LocationUtil.LocationCallback() {
                                @Override
                                public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                    double lat = loc.latitude;
                                    double lng = loc.longitude;
                                    Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                    getDataBranch("", "", providerID, pageIndex, rowCount);
                                }
                            });
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.PERMISSION_REQUEST_LOCATION);
            }
        } else {
            Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_cant_connect_internet), "");
            return;
        }


        branchAdapter.setOnClickListener(new BranchAdapter.OnClickListener() {
            @Override
            public void onClickItem(BranchResponse.List item) {
                if (item != null) {
                    if (item.getLocation() != null) {
                        if (!StringUtils.isEmpty(item.getLocation().getLatitude()) && !StringUtils.isEmpty(item.getLocation().getLongitude())) {
                            PresenterUtils.openGoogleMap(item.getLocation().getLatitude() + "," + item.getLocation().getLongitude(), getActivity());
                        } else if (!StringUtils.isEmpty(item.getFullAddress())) {
                            PresenterUtils.openGoogleMap(item.getFullAddress(), getActivity());
                        } else {
                            Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                        }
                    } else if (!StringUtils.isEmpty(item.getFullAddress())) {
                        PresenterUtils.openGoogleMap(item.getFullAddress(), getActivity());
                    } else {
                        Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                    }
                } else {
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_common_cant_open_google_map), "");
                }
            }

            @Override
            public void onClickPhone(String phoneNumber) {
                PresenterUtils.callPhoneNumber(getActivity(), phoneNumber);
            }
        });

    }


    public void getDataBranch(String areaCode, String areaType, String providerId, final int pageIndex, int rowCount) {
        final String functionName = Function.FunctionName.GET_DATA_BRANCH_BOTTOM_SHEET;
        final String functionCode = Function.FunctionCode.GET_DATA_BRANCH_BOTTOM_SHEET;


        String body = StringUtils.convertObjectToBase64(new BranchRequest(areaCode, areaType, providerId, pageIndex, rowCount, true));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, getActivity());

        Repository.getInstance().getBranch(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        BranchResponse branchResponse = new Gson().fromJson(text, BranchResponse.class);
                        if (branchResponse.getList().size() == 0) {
                            Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_cant_fint_branch), functionCode);
                            hideLoading();
                            return;
                        }
                        branchAdapter.setItems(branchResponse.getList());
                        progressBar.setVisibility(View.GONE);
                    } catch (java.lang.Exception e) {
                        Exception.handleException(e, getActivity(), functionName, functionCode);
                        hideLoading();
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    Exception.handleMessageRequestFailure(getActivity(), responseBase64.getResultDesc(), functionCode);
                    hideLoading();
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_unknown_error), functionCode);
                    hideLoading();
                } else if (status.equals(Exception.Type.SERVER)) {
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_sever_error), functionCode);
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                Exception.handleMessageRequestFailure(t, getActivity(), functionName, functionCode);
                hideLoading();
            }
        });
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Config.PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                getDataBranch("", "", providerID, pageIndex, rowCount);
            } else {
                if (!PresenterUtils.isLocationEnabled(getActivity())) {
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_branch_open_gps), "");
                    getDataBranch("", "", providerID, pageIndex, rowCount);
                } else {
                    LocationUtil.requestSingleUpdate(getActivity(),
                            new LocationUtil.LocationCallback() {
                                @Override
                                public void onLocationChanged(LocationUtil.GPSCoordinates loc) {
                                    double lat = loc.latitude;
                                    double lng = loc.longitude;
                                    Config.Header.setLocation(new Location(String.valueOf(lat), String.valueOf(lng)));
                                    getDataBranch("", "", providerID, pageIndex, rowCount);
                                }
                            });
                }
            }
        }
    }
}
