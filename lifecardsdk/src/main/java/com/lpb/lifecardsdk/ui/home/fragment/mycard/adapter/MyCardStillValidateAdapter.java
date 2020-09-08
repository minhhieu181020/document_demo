package com.lpb.lifecardsdk.ui.home.fragment.mycard.adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.TextViewUtils;
import com.lpb.lifecardsdk.widget.autofilltextview.AutofitTextView;

import java.util.ArrayList;
import java.util.List;

public class MyCardStillValidateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListCardResponseDefault.OwnCardDto> myCardSList;
    private OnClickListener onMenuClickListener;
    private onClickOwnService onClickOwnService;
    private OnClickLogo onClickLogo;
    private Context context;
    private boolean onLoadMore = true;

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    public MyCardStillValidateAdapter(Context context) {
        myCardSList = new ArrayList<>();
        this.context = context;
    }

    public void clearDataList() {
        myCardSList.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<ListCardResponseDefault.OwnCardDto> items) {
        myCardSList.addAll(items);
        notifyDataSetChanged();
    }

    public void setItems(List<ListCardResponseDefault.OwnCardDto> items) {
        myCardSList.clear();
        myCardSList = items;
        notifyDataSetChanged();
    }

    public List<ListCardResponseDefault.OwnCardDto> getMyCardSList() {
        return myCardSList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == Constants.RecyclerViewType.ITEM) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_item_card_paid_still_validated_demo4, parent, false);
            return new ViewHolder(itemView);
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_layout_loadmore, parent, false);
            return new LoadHolder(itemView);
        }

//        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lifecardsdk_item_card_wait_paid_expire, parent, false);
//dfg

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final ListCardResponseDefault.OwnCardDto myCardS = myCardSList.get(position);
            ((ViewHolder) holder).onBindData(myCardS);
        } else {
            //todo
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }

    public void setOnClickLogoListener(OnClickLogo onClickLogo) {
        this.onClickLogo = onClickLogo;
    }

    public MyCardStillValidateAdapter.onClickOwnService getOnClickOwnService() {
        return onClickOwnService;
    }

    public void setOnClickOwnService(MyCardStillValidateAdapter.onClickOwnService onClickOwnService) {
        this.onClickOwnService = onClickOwnService;
    }

    @Override
    public int getItemViewType(int position) {
        if (onLoadMore) {
            if (position == myCardSList.size() - 1) return Constants.RecyclerViewType.LOAD_MORE;
            else return Constants.RecyclerViewType.ITEM;
        } else return Constants.RecyclerViewType.ITEM;
    }

    @Override
    public int getItemCount() {
        if (myCardSList == null) return 0;
        return myCardSList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView  tvNameCard, tvContent, tvLimitRemaining, tvExpirationDate, tvExpandMore, tvLbeExpirationDate, tvLbeLimitRemaining;
        final ImageView imgBackgroud, viewUnSelected, viewExpire, viewWaitForPayMent;
        final AutofitTextView tvNumberCard;
        private RecyclerView rvLogoCard, rvLogoCard1, rvLogoCard2, rvOwnServiceDto;
        private RelativeLayout rlBoder;
        private LinearLayout llstill_validated, llExpire, llWaitForPayMent, llLook, llimit;
        private View ViewLine_Gray;
        private ConstraintLayout clUserNumber;
        private TextView tvUserNumber;
        private ImageView imgUserNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCard = itemView.findViewById(R.id.tvNameCards);
            tvNumberCard = itemView.findViewById(R.id.tvNumberCard);
            tvContent = itemView.findViewById(R.id.tvContent);
            imgBackgroud = itemView.findViewById(R.id.imgBackgroundCard);
            rvLogoCard = itemView.findViewById(R.id.rvLogoCard);
            rvLogoCard1 = itemView.findViewById(R.id.rvLogoCard1);
            rvLogoCard2 = itemView.findViewById(R.id.rvLogoCard2);
            llstill_validated = itemView.findViewById(R.id.llstill_validated);
            llExpire = itemView.findViewById(R.id.llExpire);
            llWaitForPayMent = itemView.findViewById(R.id.llWaitForPayMent);
            llLook = itemView.findViewById(R.id.llLook);
            viewUnSelected = itemView.findViewById(R.id.viewUnSelected);
            viewExpire = itemView.findViewById(R.id.viewExpire);
            viewWaitForPayMent = itemView.findViewById(R.id.viewWaitForPayMent);
            rlBoder = itemView.findViewById(R.id.rlBoder);
            rvOwnServiceDto = itemView.findViewById(R.id.rvOwnServiceDto);
            tvLimitRemaining = itemView.findViewById(R.id.tvLimitRemaining);
            tvExpirationDate = itemView.findViewById(R.id.tvExpirationDate);
            tvExpandMore = itemView.findViewById(R.id.tvExpandMore);
            llimit = itemView.findViewById(R.id.llimit);
            ViewLine_Gray = itemView.findViewById(R.id.ViewLine_Gray);
            tvLbeExpirationDate = itemView.findViewById(R.id.tvLbeExpirationDate);
            tvLbeLimitRemaining = itemView.findViewById(R.id.tvLbeLimitRemaining);

            imgUserNumber = itemView.findViewById(R.id.imgUserNumber);
            clUserNumber = itemView.findViewById(R.id.clUserNumber);
            tvUserNumber = itemView.findViewById(R.id.tvUserNumber);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        void onBindData(final ListCardResponseDefault.OwnCardDto myCardS) {
            Integer mNumberUser = myCardS.getNumberOfShareCardUsers();
            String mStatusShare = myCardS.getCardShare();
            String mStatusCard = myCardS.getStatus();

            if (!StringUtils.isEmpty(mStatusShare)) {
                if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.N)) {
                    tvUserNumber.setVisibility(View.GONE);
                    imgUserNumber.setColorFilter(ContextCompat.getColor(context, R.color.lifecardsdk_gray1), android.graphics.PorterDuff.Mode.SRC_IN);
                    clUserNumber.setVisibility(View.VISIBLE);
                } else if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.K)) {
                    tvUserNumber.setVisibility(View.GONE);
                    imgUserNumber.setColorFilter(ContextCompat.getColor(context, R.color.lifecardsdk_gray1), android.graphics.PorterDuff.Mode.SRC_IN);
                    clUserNumber.setVisibility(View.VISIBLE);
                } else if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.Y)) {
                    clUserNumber.setVisibility(View.VISIBLE);
                    tvUserNumber.setVisibility(View.VISIBLE);
                    imgUserNumber.setColorFilter(ContextCompat.getColor(context, R.color.lifecardsdk_black), android.graphics.PorterDuff.Mode.SRC_IN);
                    if (mNumberUser == null || mNumberUser < 1) {
                        tvUserNumber.setText(context.getString(R.string.lifecardsdk_common_add));
                    } else if (mNumberUser < 100) {
                        tvUserNumber.setText(String.valueOf(mNumberUser));
                    } else {
                        tvUserNumber.setText(context.getString(R.string.lifecardsdk_common_more_than_99));
                    }
                    if (!StringUtils.isEmpty(mStatusCard)) {
                        if (mStatusCard.equalsIgnoreCase("E") || mStatusCard.equalsIgnoreCase("C")) {
                            if (mNumberUser == null || mNumberUser < 1) {
                                tvUserNumber.setVisibility(View.GONE);
                                imgUserNumber.setColorFilter(ContextCompat.getColor(context, R.color.lifecardsdk_gray1), android.graphics.PorterDuff.Mode.SRC_IN);
                            } else {
                                tvUserNumber.setVisibility(View.VISIBLE);
                                imgUserNumber.setColorFilter(ContextCompat.getColor(context, R.color.lifecardsdk_black), android.graphics.PorterDuff.Mode.SRC_IN);
                            }
                        } else if (mStatusCard.equalsIgnoreCase("W")) {
                            tvUserNumber.setVisibility(View.GONE);
                            imgUserNumber.setColorFilter(ContextCompat.getColor(context, R.color.lifecardsdk_gray1), android.graphics.PorterDuff.Mode.SRC_IN);
                        } else {
                            tvUserNumber.setVisibility(View.VISIBLE);
                            imgUserNumber.setColorFilter(ContextCompat.getColor(context, R.color.lifecardsdk_black), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                    } else {
                        tvUserNumber.setVisibility(View.VISIBLE);
                        imgUserNumber.setColorFilter(ContextCompat.getColor(context, R.color.lifecardsdk_black), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                } else {
                    clUserNumber.setVisibility(View.GONE);
                    tvUserNumber.setVisibility(View.GONE);
                    imgUserNumber.setVisibility(View.GONE);
                }
            } else {
                clUserNumber.setVisibility(View.GONE);
                tvUserNumber.setVisibility(View.GONE);
                imgUserNumber.setVisibility(View.GONE);
            }


            clUserNumber.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    onMenuClickListener.onClickUserNumber(myCardS, getAdapterPosition());
                }
            });
            tvNumberCard.setText(myCardS.getCardNoDisplay());
            TextViewUtils.setStyleTextView(tvNumberCard,context,myCardS.getStyleDto().getAppStyle(),myCardS.getStyleDto().getRgb());
            tvContent.setText(myCardS.getName());
            tvNameCard.setText(myCardS.getCustomerName());

//           imgBackgroud.setImageResource(R.mipmap.lifecardsdk_background);

            GlideUtils.loadImageUrl(imgBackgroud, myCardS.getImage(), context, Constants.PlaceHolderType.BACKGROUND_CARD);

            if (myCardS.getProviderDtos().size() == 1) {
                LogoCardAdapter logoCardAdapter = new LogoCardAdapter(context, myCardS.getProviderDtos(), true, onClickLogo, null);
                rvLogoCard.setAdapter(logoCardAdapter);
                rvLogoCard.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                rvLogoCard.setVisibility(View.VISIBLE);
                rvLogoCard1.setVisibility(View.GONE);
                rvLogoCard2.setVisibility(View.GONE);

            } else if (myCardS.getProviderDtos().size() == 2) {
                LogoCardAdapter logoCardAdapter = new LogoCardAdapter(context, myCardS.getProviderDtos(), true, onClickLogo, null);
                rvLogoCard1.setAdapter(logoCardAdapter);
                rvLogoCard1.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                rvLogoCard.setVisibility(View.GONE);
                rvLogoCard1.setVisibility(View.VISIBLE);
                rvLogoCard2.setVisibility(View.GONE);
            } else if (myCardS.getProviderDtos().size() == 3) {
                LogoCardAdapter logoCardAdapter = new LogoCardAdapter(context, myCardS.getProviderDtos(), true, onClickLogo, null);
                rvLogoCard2.setAdapter(logoCardAdapter);
                rvLogoCard2.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                rvLogoCard.setVisibility(View.GONE);
                rvLogoCard1.setVisibility(View.GONE);
                rvLogoCard2.setVisibility(View.VISIBLE);
            } else {
                LogoCardAdapter logoCardAdapter = new LogoCardAdapter(context, myCardS.getProviderDtos(), true, onClickLogo, null);
                rvLogoCard2.setAdapter(logoCardAdapter);
                rvLogoCard2.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                rvLogoCard.setVisibility(View.GONE);
                rvLogoCard1.setVisibility(View.GONE);
                rvLogoCard2.setVisibility(View.VISIBLE);
            }


//        myCardS.getStatus();
            if (myCardS.getStatus().equals("E") || myCardS.getStatus().equals("C")) {
                LogoCardAdapter logoCardAdapter2 = new LogoCardAdapter(context, myCardS.getProviderDtos(), true, onClickLogo, null);
                rvLogoCard.setAdapter(logoCardAdapter2);
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//            holder.imgBackgroud.setColorFilter(filter);
                itemView.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        if (onMenuClickListener != null)
                            onMenuClickListener.onClickEC(myCardS, getAdapterPosition());
                    }
                });
                llstill_validated.setVisibility(View.GONE);
                llExpire.setVisibility(View.VISIBLE);
                llWaitForPayMent.setVisibility(View.GONE);
                llLook.setVisibility(View.GONE);
                viewUnSelected.setVisibility(View.GONE);
                viewExpire.setVisibility(View.VISIBLE);
                viewWaitForPayMent.setVisibility(View.GONE);
                rlBoder.setBackgroundResource(R.drawable.lifecardsdk_bg_round_gray3);
                tvLbeExpirationDate.setText("Hạn sử dụng");
                tvLbeLimitRemaining.setText("Hạn mức thẻ");
                if (myCardS.getRootService() == null) {
                    llimit.setVisibility(View.GONE);
                } else {
                    llimit.setVisibility(View.VISIBLE);
                    if (myCardS.getRootService().getOwnAccountDtos().size() == 0) {
                        tvLimitRemaining.setText("");
                        tvExpirationDate.setText("");
                        llimit.setVisibility(View.GONE);
                    } else {
                        tvLimitRemaining.setText(myCardS.getRootService().getOwnAccountDtos().get(0).getAllocatedUnit());
                        tvExpirationDate.setText(myCardS.getRootService().getExpirationDate());
                    }
                }
            } else if (myCardS.getStatus().equals("A") || myCardS.getStatus().equals("O")) {
                itemView.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        if (onMenuClickListener != null)
                            onMenuClickListener.onClickAO(myCardS, getAdapterPosition());

                    }
                });
                llstill_validated.setVisibility(View.GONE);
                llExpire.setVisibility(View.GONE);
                llWaitForPayMent.setVisibility(View.GONE);
                llLook.setVisibility(View.GONE);
                viewUnSelected.setVisibility(View.VISIBLE);
                viewExpire.setVisibility(View.GONE);
                viewWaitForPayMent.setVisibility(View.GONE);
                rlBoder.setBackground(context.getDrawable(R.drawable.lifecardsdk_bg_round_green3));
                if (myCardS.getRootService() == null) {
                    llimit.setVisibility(View.GONE);
                } else {
                    llimit.setVisibility(View.VISIBLE);
                    if (myCardS.getRootService().getOwnAccountDtos().size() == 0) {
                        tvLimitRemaining.setText("");
                        tvExpirationDate.setText("");
                        llimit.setVisibility(View.GONE);
                    } else {
                        tvLimitRemaining.setText(myCardS.getRootService().getOwnAccountDtos().get(0).getAvailableUnit());
                        if (myCardS.getRootService().getExpirationDate().isEmpty()) {
                            tvExpirationDate.setText("");
                            tvExpirationDate.setVisibility(View.GONE);
                            tvLbeExpirationDate.setVisibility(View.GONE);
                        } else {
                            tvExpirationDate.setVisibility(View.VISIBLE);
                            tvLbeExpirationDate.setVisibility(View.VISIBLE);
                            tvExpirationDate.setText(myCardS.getRootService().getExpirationDate());
                        }
                    }
                }
                if (myCardS.getStatus().equals("A")){
                    tvLbeLimitRemaining.setText("Hạn mức còn lại");
                }else {
                    tvLbeLimitRemaining.setText("Hạn mức thẻ");
                }
                tvLbeExpirationDate.setText("Hạn sử dụng");


            } else if (myCardS.getStatus().equals("L")) {
                itemView.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        if (onMenuClickListener != null)
                            onMenuClickListener.onClickAO(myCardS, getAdapterPosition());
                    }
                });
                llstill_validated.setVisibility(View.GONE);
                llExpire.setVisibility(View.GONE);
                llWaitForPayMent.setVisibility(View.GONE);
                llLook.setVisibility(View.VISIBLE);
                viewUnSelected.setVisibility(View.VISIBLE);
                viewExpire.setVisibility(View.GONE);
                viewWaitForPayMent.setVisibility(View.GONE);
                rlBoder.setBackground(context.getDrawable(R.drawable.lifecardsdk_bg_round_red3));
                if (myCardS.getRootService() == null) {
                    llimit.setVisibility(View.GONE);
                } else {
                    llimit.setVisibility(View.VISIBLE);
                    if (myCardS.getRootService().getOwnAccountDtos().size() == 0) {
                        tvLimitRemaining.setText("");
                        tvExpirationDate.setText("");
                        llimit.setVisibility(View.GONE);
                    } else {
                        tvLimitRemaining.setText(myCardS.getRootService().getOwnAccountDtos().get(0).getAvailableUnit());
                        tvExpirationDate.setText(myCardS.getRootService().getExpirationDate());
                    }
                }
                tvLbeExpirationDate.setText("Hạn sử dụng");
                tvLbeLimitRemaining.setText("Hạn mức thẻ");

            } else {
                itemView.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        if (onMenuClickListener != null)
                            onMenuClickListener.onClickW(myCardS, getAdapterPosition());

                    }
                });
                llstill_validated.setVisibility(View.GONE);
                llExpire.setVisibility(View.GONE);
                llWaitForPayMent.setVisibility(View.VISIBLE);
                llLook.setVisibility(View.GONE);
                viewUnSelected.setVisibility(View.GONE);
                viewExpire.setVisibility(View.GONE);
                viewWaitForPayMent.setVisibility(View.VISIBLE);
                rlBoder.setBackground(context.getDrawable(R.drawable.lifecardsdk_bg_round_yellow3));
                tvLbeExpirationDate.setText("Mã đơn hàng");
                tvLbeLimitRemaining.setText("Chờ thanh toán");

                if (StringUtils.isEmpty(myCardS.getPrice())) {
                    tvLimitRemaining.setText("");
                } else {
                    tvLimitRemaining.setText(myCardS.getPrice());
                }
                if (StringUtils.isEmpty(myCardS.getTransactionCode())) {
                    tvExpirationDate.setText("");
                } else {
                    tvExpirationDate.setText(myCardS.getTransactionCode());
                }
                if (StringUtils.isEmpty(myCardS.getPrice()) && StringUtils.isEmpty(myCardS.getPaymentExpirationDate())) {
                    llimit.setVisibility(View.GONE);
                } else {
                    llimit.setVisibility(View.VISIBLE);
                }
            }

//            Log.e("ID", "onResponse: " + myCardS.getCardNoDisplay() + "     " + myCardS.getId() + "  " + myCardS.getStatus())
            if (myCardS.getOwnServiceDtos() == null) {
                rvOwnServiceDto.setVisibility(View.GONE);
                tvExpandMore.setVisibility(View.GONE);
                tvExpandMore.setText("");
                ViewLine_Gray.setVisibility(View.GONE);
            } else {

                if (myCardS.getOwnServiceDtos().size() == 0) {
                    rvOwnServiceDto.setVisibility(View.GONE);
                    tvExpandMore.setVisibility(View.GONE);
                    tvExpandMore.setText("");
                    ViewLine_Gray.setVisibility(View.GONE);
                } else {
                    rvOwnServiceDto.setVisibility(View.VISIBLE);
                    ViewLine_Gray.setVisibility(View.VISIBLE);

                    if (myCardS.getOwnServiceDtos().size() > Config.MAX_ITEM_PROMOTION) {
                        tvExpandMore.setVisibility(View.VISIBLE);
                        tvExpandMore.setText(context.getString(R.string.lifecardsdk_common_service_more, String.valueOf(myCardS.getOwnServiceDtos().size() - Config.MAX_ITEM_PROMOTION)));
                    } else {
                        tvExpandMore.setVisibility(View.GONE);
                        tvExpandMore.setText("");
                    }
                    if (myCardS.getStatus().equals("W")) {
                        rvOwnServiceDto.setVisibility(View.GONE);
                        tvExpandMore.setVisibility(View.GONE);
                        ViewLine_Gray.setVisibility(View.GONE);

                    } else {
                        ViewLine_Gray.setVisibility(View.VISIBLE);
                        ListOwnServiceAdapter listOwnServiceAdapter = new ListOwnServiceAdapter(context, myCardS.getOwnServiceDtos(), myCardS.getCardNo(), myCardS.getCardNoDisplay(), (MyCardStillValidateAdapter.onClickOwnService) onClickOwnService);
                        rvOwnServiceDto.setAdapter(listOwnServiceAdapter);
                        rvOwnServiceDto.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                        if (myCardS.getRootService() == null && myCardS.getOwnServiceDtos() == null) {
                            llimit.setVisibility(View.GONE);
                            ViewLine_Gray.setVisibility(View.GONE);
                        }
                    }
                }

            }
        }
    }

    class LoadHolder extends RecyclerView.ViewHolder {
        LoadHolder(View view) {
            super(view);
        }
    }

    public interface OnClickListener {
        void onClick(ListCardResponseDefault.OwnCardDto item, int position);

        void onClickEC(ListCardResponseDefault.OwnCardDto myCardSEC, int position);

        void onClickAO(ListCardResponseDefault.OwnCardDto myCardSAO, int position);

        void onClickW(ListCardResponseDefault.OwnCardDto myCardSW, int position);

        void onClickUserNumber(ListCardResponseDefault.OwnCardDto ownCardDto, int position);
    }

    public interface OnClickLogo {
        void onClickItem(ListCardResponseDefault.ProviderDto item);
    }

    public interface onClickOwnService {
        void onClickOwnService(String code, String cardNoDisplay, int position);
    }
}