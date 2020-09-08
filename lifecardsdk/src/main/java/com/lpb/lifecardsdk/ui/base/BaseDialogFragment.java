package com.lpb.lifecardsdk.ui.base;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;
import android.view.Window;

import com.lpb.lifecardsdk.callback.AlertDialogListener;

public abstract class BaseDialogFragment extends DialogFragment implements BaseScreen {

    private boolean isFullScreen;

    protected abstract int getStyleDialog();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getStyleDialog());
        isFullScreen = true;
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isFullScreen) {
            Window window = getDialog().getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showLoading(boolean isCancel) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        return null;
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void showLoadingDialog() {
        showLoadingDialog(true);
    }

    public void hideLoadingDialog() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingDialog();
        }
    }

    public void showLoadingDialog(final boolean cancelable) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoadingDialog(cancelable);
        }
    }

    public void goToDialogFragment(BaseDialogFragment mBaseDialog, Bundle mBundle) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).goToDialogFragment(mBaseDialog, mBundle);
        }
    }

    public void goToFragment(@IdRes int fragmentContainerId, BaseFragment mBaseFragment, Bundle mBundle) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).goToFragment(fragmentContainerId, mBaseFragment, mBundle);
        }
    }

    public void onBackFragment() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).onBackFragment();
        }
    }

    public void openActivityForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).openActivityForResult(clazz, bundle, requestCode);
        }
    }

    public void openActivity(Class<?> clazz) {
        openActivity(clazz, null);
    }

    public void openActivity(Class<?> clazz, boolean isFinish) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).openActivity(clazz, isFinish);
        }
    }

    public void openActivity(Class<?> clazz, Bundle bundle) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).openActivity(clazz, bundle);
        }
    }

    public void showAlertDialog(@StringRes int title, @StringRes int message,
                                boolean cancellable,
                                AlertDialogListener listener) {
        showAlertDialog(getString(title), getString(message), cancellable, listener);
    }

    public void showAlertDialog(String title, String message,
                                boolean cancellable,
                                AlertDialogListener listener) {

        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showAlertDialog(title, message, cancellable, listener);
        }
    }

    public void showAlertDialog(String title, String message,
                                String acceptLabel, String cancelLabel,
                                boolean cancellable,
                                AlertDialogListener listener) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showAlertDialog(title, message, acceptLabel,
                    cancelLabel, cancellable, listener);
        }
    }

    public void showShortToast(@StringRes int message) {
        showShortToast(getString(message));
    }

    public void showLongToast(@StringRes int message) {
        showLongToast(getString(message));
    }

    public void showShortToast(String message) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showShortToast(message);
        }
    }

    public void showLongToast(String message) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLongToast(message);
        }
    }
//
//    public boolean assetHasNetwork() {
//        if (getActivity() != null && getActivity() instanceof BaseActivity) {
//            return ((BaseActivity) getActivity()).assetHasNetwork();
//        }
//
//        return true;
//    }
}
