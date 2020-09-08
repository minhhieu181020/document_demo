package com.lpb.lifecardsdk.ui.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.callback.AlertDialogListener;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.dialog.LoadingDialog;
import com.lpb.lifecardsdk.util.LocaleUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.ArrayDeque;
import java.util.Deque;


public abstract class BaseActivity extends AppCompatActivity implements BaseScreen {
    private FragmentManager mFrgManager;
    private Deque<BaseFragment> mFragStack;
    private LoadingDialog mLoadingDialog;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleUtils.onAttach(base));
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog();
        }
    }

    @Override
    public void showError(String resultDesc, String resultCode) {
        Exception.handleMessageRequestFailure(this, resultDesc, resultCode);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Exception.isShowToast = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Exception.isShowToast = true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Exception.isShowToast = true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading(boolean isCancel) {
        showLoadingDialog(isCancel);
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    /**
     * Show loading dialog without leak window
     */

    public void showLoadingDialog(final boolean cancelable) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mLoadingDialog.isShowingLoadingDialog()) return;
                if (BaseActivity.this.isFinishing() || (mLoadingDialog != null && mLoadingDialog.isAdded())) {
                    return;
                }
                mLoadingDialog.setCancelable(cancelable);
                FragmentManager fragmentManager = BaseActivity.this.getSupportFragmentManager();
                mLoadingDialog.show(fragmentManager, "loading");
                mLoadingDialog.setShowingLoadingDialog(true);
            }
        });
    }

    /**
     * Hide loading dialog, with check activity working or not
     */
    public void hideLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (BaseActivity.this.isFinishing() || mLoadingDialog == null || !mLoadingDialog.isAdded()) {
                    return;
                }
                mLoadingDialog.dismiss();

                mLoadingDialog.setShowingLoadingDialog(false);
            }
        });
    }

    public void goToDialogFragment(BaseDialogFragment mBaseDialog, Bundle mBundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mBaseDialog.setArguments(mBundle);
        mBaseDialog.show(fragmentManager, mBaseDialog.getClass().getName());
    }

    public void goToFragment(@IdRes int fragmentContainerId, BaseFragment mBaseFragment, Bundle mBundle) {
        if (mFragStack == null && mFrgManager == null) {
            mFragStack = new ArrayDeque<>();
            mFrgManager = getSupportFragmentManager();
        }
        FragmentTransaction trans = mFrgManager.beginTransaction();
        if (mBundle != null) {
            mBaseFragment.setArguments(mBundle);
        }
        if (mFragStack != null && mFragStack.size() >= 1) {
            trans.hide(mFragStack.getLast());
        }

        if (mFragStack != null)
            mFragStack.push(mBaseFragment);

        trans.add(fragmentContainerId, mBaseFragment, mBaseFragment.getClass().getSimpleName());
        trans.commit();
        mFrgManager.executePendingTransactions();
    }

    public boolean onBackFragment() {
        FragmentTransaction trans = mFrgManager.beginTransaction();
        if (mFragStack.size() > 1) {
            trans.remove(mFragStack.pop());
            trans.show(mFragStack.getLast());
            mFragStack.getLast().onResume();
            trans.commit();

            return true;
        } else {
            return false;
        }
    }

    public void openActivityForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void openActivity(Class<?> clazz) {
        openActivity(clazz, null);
    }

    public void openActivity(Class<?> clazz, boolean isFinish) {
        openActivity(clazz);
        if (isFinish) {
            finish();
        }
    }

    public void openActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void showAlertDialog(String message) {
        showAlertDialog(getString(R.string.lifecardsdk_common_notify), message, false, null);
    }

    public void showAlertDialog(@StringRes int title, @StringRes int message,
                                boolean cancellable,
                                AlertDialogListener listener) {
        showAlertDialog(getString(title), getString(message), cancellable, listener);
    }

    public void showAlertDialog(@StringRes int title, @StringRes int message,
                                @StringRes int acceptLabel, @StringRes int cancelLabel,
                                boolean cancellable,
                                AlertDialogListener listener) {
        showAlertDialog(getString(title), getString(message),
                getString(acceptLabel), getString(cancelLabel),
                cancellable, listener);
    }

    public void showAlertDialog(String title, String message,
                                boolean cancellable,
                                AlertDialogListener listener) {

        if (cancellable) {
            showAlertDialog(title,
                    message,
                    getString(R.string.lifecardsdk_common_accept),
                    getString(R.string.lifecardsdk_common_cancel),
                    true,
                    listener);
        } else {
            showAlertDialog(title,
                    message,
                    getString(R.string.lifecardsdk_common_cancel),
                    null,
                    false,
                    listener);
        }
    }

    public void showAlertDialog(String title, String message,
                                String acceptLabel, String cancelLabel,
                                boolean cancellable,
                                final AlertDialogListener listener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title)
                .setMessage(message)
                .setCancelable(cancellable);

        if (!StringUtils.isEmpty(acceptLabel)) {

            alertDialogBuilder.setPositiveButton(acceptLabel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (listener != null)
                        listener.onAccept();
                }
            });
        }

        if (!StringUtils.isEmpty(cancelLabel)) {

            alertDialogBuilder.setNegativeButton(cancelLabel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (listener != null)
                        listener.onCancel();
                }
            });
        }

        alertDialogBuilder.create().show();
    }

    public void showShortToast(@StringRes int message) {
        showShortToast(getString(message));
    }

    public void showLongToast(@StringRes int message) {
        showLongToast(getString(message));
    }

    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showDialogClose(String title, String desc, String labelClose, boolean cancelable, boolean canceledOnTouchOutside) {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.lifecardmerchant_layout_dialog_notify, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.windowAnimations = R.style.LifeCardMerchant_Zoom_Center;
        dialog.getWindow().setAttributes(layoutParams);

        int width = getResources().getDisplayMetrics().widthPixels;

        int paddingWidth = (int) (width * 0.12);

        dialog.getWindow().setBackgroundDrawable(new InsetDrawable(new ColorDrawable(Color.TRANSPARENT), paddingWidth, 0, paddingWidth, 0));

        TextView tvClose = dialogView.findViewById(R.id.tvClose);
        tvClose.setText(labelClose);
        TextView tvDesc = dialogView.findViewById(R.id.tvDescription);
        tvDesc.setText(StringUtils.convertHTMLToString(desc, this));
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    public void showDialogConfirm(String title, String desc, String labelClose, String labelAllow, final DialogClickListener dialogClickListener, boolean cancelable, boolean canceledOnTouchOutside) {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.lifecardmerchant_layout_dialog_confirm, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.windowAnimations = R.style.LifeCardMerchant_Zoom_Center;
        dialog.getWindow().setAttributes(layoutParams);

        int width = getResources().getDisplayMetrics().widthPixels;

        int paddingWidth = (int) (width * 0.08);

        dialog.getWindow().setBackgroundDrawable(new InsetDrawable(new ColorDrawable(Color.TRANSPARENT), paddingWidth, 0, paddingWidth, 0));

        TextView tvAllow = dialogView.findViewById(R.id.tvAllow);
        tvAllow.setText(labelAllow);
        TextView tvClose = dialogView.findViewById(R.id.tvClose);
        tvClose.setText(labelClose);
        TextView tvDesc = dialogView.findViewById(R.id.tvDescription);
        tvDesc.setText(StringUtils.convertHTMLToString(desc, this));
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        ImageView imgClose = dialogView.findViewById(R.id.imgClose);

        tvAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogClickListener.allow();
            }
        });

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogClickListener.close();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogClickListener.close();
            }
        });
        dialog.show();
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }
//    public boolean assetHasNetwork() {
//
//        boolean hasNetwork = CommonUtils.isConnectingToInternet();
//
//        if (!hasNetwork) {
//            showAlertDialog(getString(R.string.common_error_no_internet));
//        }
//
//        return hasNetwork;
//    }
}