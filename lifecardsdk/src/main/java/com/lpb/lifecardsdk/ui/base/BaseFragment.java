package com.lpb.lifecardsdk.ui.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lpb.lifecardsdk.callback.AlertDialogListener;
import com.lpb.lifecardsdk.exception.Exception;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class BaseFragment extends Fragment implements BaseScreen {
    private FragmentManager mFrgManager;
    private Deque<BaseFragment> mFragStack;

    @Override
    public void showLoading(boolean isCancel) {
        showLoadingDialog(isCancel);
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showError(String resultDesc, String resultCode) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showError(resultDesc,resultCode);
        }
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
        FragmentManager fragmentManager = getChildFragmentManager();
        mBaseDialog.setArguments(mBundle);
        mBaseDialog.show(fragmentManager, mBaseDialog.getClass().getName());
    }

    public void goToFragment(@IdRes int fragmentContainerId, BaseFragment mBaseFragment, Bundle mBundle) {
        if (mFragStack == null && mFrgManager == null) {
            mFragStack = new ArrayDeque<>();
            mFrgManager = getChildFragmentManager();
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

        if (mFrgManager == null || mFragStack == null)
            return false;

        FragmentTransaction trans = mFrgManager.beginTransaction();
        if (mFragStack.size() > 0) {
            trans.remove(mFragStack.pop());
            trans.commit();

            return true;
        } else {
            return false;
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

//    public boolean assetHasNetwork() {
//        if (getActivity() != null && getActivity() instanceof BaseActivity) {
//            return ((BaseActivity) getActivity()).assetHasNetwork();
//        }
//
//        return true;
//    }
    public  boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }
    public void showDialogConfirm(String title, String desc, String labelClose, String labelAllow, final DialogClickListener dialogClickListener, boolean cancelable,boolean canceledOnTouchOutside) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showDialogConfirm(title, desc, labelClose, labelAllow, dialogClickListener, cancelable,canceledOnTouchOutside);
        }
    }

    public void showDialogClose(String title, String desc, String labelClose, boolean cancelable,boolean canceledOnTouchOutside) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showDialogClose(title, desc, labelClose, cancelable,canceledOnTouchOutside);
        }
    }
}
