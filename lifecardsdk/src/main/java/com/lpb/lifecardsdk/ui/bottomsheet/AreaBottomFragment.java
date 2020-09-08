package com.lpb.lifecardsdk.ui.bottomsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.AreaRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.AreaResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.provider.fragment.branch.adapter.AreaAdapter;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaBottomFragment extends BottomSheetDialogFragment {
    private BottomSheetListener mListenner;
    private RecyclerView rvContent;
    private AreaAdapter areaAdapter;
    private ProgressBar progressBar;
    private TextView tvTitle;
    private String areaCode, parentCode, areaType, parentName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.LifeCardSDK_AppBottomSheetDialogTheme);
    }

    public void setListenner(BottomSheetListener mListenner) {
        this.mListenner = mListenner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lifecardsdk_area_bottom_sheet, container, false);
        rvContent = view.findViewById(R.id.rvContent);
        progressBar = view.findViewById(R.id.progressBar);
        tvTitle = view.findViewById(R.id.tvTitle);
        areaAdapter = new AreaAdapter(getActivity());
        rvContent.setAdapter(areaAdapter);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        areaCode = bundle.getString("areaCode");
        parentCode = bundle.getString("parentCode");
        areaType = bundle.getString("areaType");
        parentName = bundle.getString("parentName");

        switch (areaType) {
            case Constants.AreaType.CITY:
                tvTitle.setText(getString(R.string.lifecardsdk_area_select_city));
                break;
            case Constants.AreaType.DISTRICT:
                tvTitle.setText(getString(R.string.lifecardsdk_area_select_district));
                break;
            case Constants.AreaType.VILLAGE:
                tvTitle.setText(getString(R.string.lifecardsdk_area_select_village));
                break;
            default:
                tvTitle.setText(getString(R.string.lifecardsdk_area_select_location));
                break;
        }

        if (PresenterUtils.isNetworkConnected(getActivity())) {
            getDataArea(areaType, parentCode);
        } else {
            progressBar.setVisibility(View.GONE);
            dismiss();
            Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
        if (!StringUtils.isEmpty(areaCode)) {
            areaAdapter.setAreaChecked(areaCode);
        }
        areaAdapter.setOnClickListener(new AreaAdapter.OnClickListener() {
            @Override
            public void onClickItem(AreaResponse.ListArea item) {
                if (item.getAreaCode().equals(areaCode)) {
                    switch (item.getAreaType()) {
                        case Constants.AreaType.CITY:
                            mListenner.callBack("", "", "");
                            dismiss();
                            break;
                        case Constants.AreaType.DISTRICT:
                            mListenner.callBack(parentCode, parentName, Constants.AreaType.CITY);
                            dismiss();
                            break;
                        case Constants.AreaType.VILLAGE:
                            mListenner.callBack(parentCode, parentName, Constants.AreaType.DISTRICT);
                            dismiss();
                            break;
                    }
                } else {
                    mListenner.callBack(item.getAreaCode(), item.getAreaName(), item.getAreaType());
                    dismiss();
                }
            }
        });
    }

    public interface BottomSheetListener {
        void callBack(String areaCode, String areaName, String areaType);
    }

    public void getDataArea(final String areaType, String parentCode) {
        final String functionName = Function.FunctionName.GET_DATA_AREA;
        final String functionCode = Function.FunctionCode.GET_DATA_AREA;

        String body = StringUtils.convertObjectToBase64(new AreaRequest(areaType, parentCode));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, getActivity());

        Repository.getInstance().getArea(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        AreaResponse areaResponse = new Gson().fromJson(text, AreaResponse.class);
                        areaAdapter.setItems(areaResponse.getListArea());
                        progressBar.setVisibility(View.GONE);
                        if (!StringUtils.isEmpty(areaCode)) {
                            for (int i = 0; i < areaResponse.getListArea().size(); i++) {
                                if (areaResponse.getListArea().get(i).getAreaCode().equals(areaCode)) {
                                    rvContent.scrollToPosition(i);
                                }
                            }
                        }
                    } catch (java.lang.Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Exception.handleException(e, getActivity(), functionName, functionCode);
                        dismiss();
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    progressBar.setVisibility(View.GONE);
                    ResponseBase64 responseBase64 = response.body();
                    Exception.handleMessageRequestFailure(getActivity(), responseBase64.getResultDesc(), functionCode);

                    dismiss();
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    progressBar.setVisibility(View.GONE);
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_unknown_error), functionCode);
                    dismiss();
                } else if (status.equals(Exception.Type.SERVER)) {
                    progressBar.setVisibility(View.GONE);
                    Exception.handleMessageRequestFailure(getActivity(), getActivity().getString(R.string.lifecardsdk_sever_error), functionCode);
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                Exception.handleMessageRequestFailure(t, getActivity(), functionName, functionCode);
                progressBar.setVisibility(View.GONE);
                dismiss();
            }
        });

    }
}
