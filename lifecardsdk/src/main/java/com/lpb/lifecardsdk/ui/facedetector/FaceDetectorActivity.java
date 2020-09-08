package com.lpb.lifecardsdk.ui.facedetector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.EKYCConfig;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.request.default_.face_verify.FaceVerifyRequest;
import com.lpb.lifecardsdk.data.model.request.default_.face_verify.ImageLive;
import com.lpb.lifecardsdk.data.model.request.default_.face_verify.ImageUniqueID;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.home.LCHomeActivity;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.facedetector.ActionFace;
import com.lpb.lifecardsdk.widget.facedetector.CameraView;
import com.lpb.lifecardsdk.widget.facedetector.CameraViewEventListener;
import com.lpb.lifecardsdk.widget.facedetector.CircleProgressBar;
import com.lpb.lifecardsdk.widget.facedetector.FaceDetectionHelper;
import com.lpb.lifecardsdk.widget.facedetector.FaceDetectionType;
import com.lpb.lifecardsdk.widget.facedetector.LivenessDetector;
import com.lpb.lifecardsdk.widget.facedetector.LivenessDetectorListener;
import com.lpb.lifecardsdk.widget.facedetector.SDKConfiguration;
import com.lpb.lifecardsdk.widget.facedetector.SelfieConfiguration;
import com.lpb.lifecardsdk.widget.facedetector.SoundPlayer;
import com.lpb.lifecardsdk.widget.facedetector.TVSelfieImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vannh.lvt on 13/07/2020
 */
public class FaceDetectorActivity extends BaseDataActivity<FaceDetectorPresenter> implements FaceDetectorContract.View {

    private final int REQ_CODE_CAMERA_PERMISSION = 12311;

    private TextView mPromptText;

    private TextView mTVCooldown;

    private ImageView mCameraMaskImageView;

    private ImageView mCameraBGImageView;

    private CameraView mCameraView;

    private ImageView mCaptureButton;

    private RelativeLayout mLivenessLayout;
    private ImageView imgBack;
    private RelativeLayout rlOpenCamera;
    private ImageView imgBack2;
    private TextView tvTitleToolbar2;
    private Button btnSettings;
    private LinearLayout llContent;
    private TextView tvTitleToolbar;

    private CircleProgressBar mCircleProgressBar;

    private CircleProgressBar.CircleProgressBarAnimation mProgressAnimation;

    private boolean isHandleStart;

    private boolean isFinished;

    private boolean isCapturing;

    private SoundPlayer mSoundPlayer;

    private CountDownTimer mTimer;

    private CountDownTimer mLivenessTimer;

    private ArrayList<TVSelfieImage> mListBitmap = new ArrayList<>();

    private ArrayList<TVSelfieImage> selfieImages;

    private LivenessDetector mLivenessDetector;

    private FaceDetectionHelper mLivenessUIHelper;

    private int mCameraMode;

    private int numOfFrame;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    private SDKConfiguration mConfiguration;


    private final LivenessDetectorListener mFaceDetectorListener = new LivenessDetectorListener() {
        public void onDetectionFailed(final String message) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (!isHandleStart) {
                        mCameraBGImageView.setImageDrawable(getResources().getDrawable(R.mipmap.lifecardsdk_bg_camera_mask_red));
                        mCameraMaskImageView.setImageDrawable(getResources().getDrawable(R.mipmap.lifecardsdk_bg_face_border_red));
                        mPromptText.setText(message);
                        mCaptureButton.setEnabled(false);
                    } else if (mConfiguration.getLivenessMode() == SDKConfiguration.LivenessMode.ACTIVE) {
                        mLivenessUIHelper.setLivenessError(message);
                    }

                }
            });
        }

        @Override
        public void onMovedToNextStep(final FaceDetectionType currentStep, final FaceDetectionType nexStep, final Bitmap bitmap) {
            if (!isFinished && mConfiguration.getLivenessMode() == SDKConfiguration.LivenessMode.ACTIVE) {
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSoundPlayer.doPlay(R.raw.lifecardsdk_weldone);
                        stopTimeoutTimer();
                        mLivenessUIHelper.finishCurrentStep(bitmap);
                        mListBitmap.add(new TVSelfieImage(currentStep.getFaceAction(), bitmap));
                    }
                });
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSoundPlayer.doPlay(mLivenessUIHelper.getSoundID(nexStep));
                        mLivenessDetector.changeDetectionType(nexStep);
                        startTimeoutTimer((long) nexStep.getTimeInterval());
                    }
                }, 1000L);
            }
        }

        public void onDetectionSuccess(final Bitmap bitmap) {
            isFinished = true;
            stopTimeoutTimer();
            mTVCooldown.setText("");
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    mPromptText.setText("");
                    mListBitmap.add(new TVSelfieImage(ActionFace.portrait.name(), bitmap));
                    mLivenessUIHelper.finishCurrentStep(bitmap);
                    startCapture();


                }
            }, 1000L);
        }

        public void onFaceDetected(Bitmap faceBitmap) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {

                    if (mConfiguration.getLivenessMode() != SDKConfiguration.LivenessMode.ACTIVE) {
                        if (!isFinished) {
                            mPromptText.setText(getString(R.string.lifecardsdk_txt_capture_now));
                            mCameraBGImageView.setImageDrawable(getResources().getDrawable(R.mipmap.lifecardsdk_bg_camera_mask));
                            mCameraMaskImageView.setImageDrawable(getResources().getDrawable(R.mipmap.lifecardsdk_bg_face_border_blue));
                            mCaptureButton.setEnabled(true);
                        }
                    } else if (!isHandleStart) {
                        mPromptText.setText("");
                        mSoundPlayer.doPlay(R.raw.lifecardsdk_weldone);
                        isHandleStart = true;
                        mCameraBGImageView.setImageDrawable(getResources().getDrawable(R.mipmap.lifecardsdk_bg_camera_mask));
                        mCameraMaskImageView.setImageDrawable(getResources().getDrawable(R.mipmap.lifecardsdk_bg_face_border_blue));
                        startActiveLiveness();
                    } else {
                        mLivenessUIHelper.setLivenessError("");
                    }


                }
            });
        }

        @Override
        public void onResetStep() {
            reset();
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_face_detector;
    }

    @Override
    protected void initView() {
        mPromptText = findViewById(R.id.liveness_layout_promptText);
        mTVCooldown = findViewById(R.id.tv_cooldown);
        mCameraMaskImageView = findViewById(R.id.camera_mask);
        mCameraBGImageView = findViewById(R.id.camera_mask_bg);
        mCameraView = findViewById(R.id.camera_view);
        mCaptureButton = findViewById(R.id.camera_button);
        mLivenessLayout = findViewById(R.id.liveness_layout_gesture);
        mCircleProgressBar = findViewById(R.id.liveness_progress);
        imgBack = findViewById(R.id.imgBack);
        llContent = findViewById(R.id.llContent);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        rlOpenCamera = findViewById(R.id.rlOpenCamera);
        imgBack2 = findViewById(R.id.imgBack2);
        tvTitleToolbar2 = findViewById(R.id.tvTitleToolbar2);
        btnSettings = findViewById(R.id.btnSettings);

        tvTitleToolbar.setText(getString(R.string.lifecardsdk_face_detector));
        tvTitleToolbar2.setText(getString(R.string.lifecardsdk_face_detector));


        mConfiguration = new SDKConfiguration(new SelfieConfiguration.Builder().setEnableSound(true)
                .setLivenessMode(SDKConfiguration.LivenessMode.ACTIVE)
                .setCameraOption(SDKConfiguration.CameraOption.FRONT).build());
        setupView();
        requestCameraPermission();
    }

    @Override
    protected void initData() {
        mPresenter = new FaceDetectorPresenter(this, this);
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
        imgBack2.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
        btnSettings.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQ_CODE_CAMERA_PERMISSION);
            }
        });
    }

    private void startActiveLiveness() {
        mSoundPlayer.doPlay(this.mLivenessUIHelper.getSoundID(this.mLivenessDetector.getFirstDetectionType()));
        mLivenessUIHelper.start();
    }

    private void startTimeoutTimer(final long time) {
        this.stopTimeoutTimer();
        if (time > 0L) {
            long second = 0L;
            mTVCooldown.setText(String.valueOf(second));
            mLivenessTimer = new CountDownTimer(time * 1000L, 1000L) {
                public void onTick(long millisUntilFinished) {
                    long second = millisUntilFinished / 1000L;
                    mTVCooldown.setText(String.valueOf(second));
                    mCircleProgressBar.setProgress((int) ((float) (time - second) / (float) time * 100.0F));
                }

                public void onFinish() {
                    reset();
                    Exception.handleMessageRequestFailure(FaceDetectorActivity.this, getString(R.string.lifecardsdk_face_timeout), "");
                }
            };
            this.mLivenessTimer.start();
        }

    }

    private void stopTimeoutTimer() {
        if (this.mLivenessTimer != null) {
            this.mLivenessTimer.cancel();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mCameraView.startCamera();
            llContent.setVisibility(View.VISIBLE);
            rlOpenCamera.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mCameraView.stopCamera();
    }

    @Override
    public void onDestroy() {
        stopTimeoutTimer();
        mSoundPlayer.close();
        super.onDestroy();
    }


    private void requestCameraPermission() {
        int contactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (contactPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_CAMERA_PERMISSION);
        } else {
            llContent.setVisibility(View.VISIBLE);
            rlOpenCamera.setVisibility(View.GONE);
            mCameraView.startCamera();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_CAMERA_PERMISSION) {
            requestCameraPermission();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (String permission : permissions) {
            if (permission.equals(Manifest.permission.CAMERA)) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    llContent.setVisibility(View.GONE);
                    rlOpenCamera.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }


    private void initFaceDetector() {
        mLivenessDetector = new LivenessDetector(this);
        mLivenessDetector.setFaceActions(EKYCConfig.getActions(), EKYCConfig.getNumberOfActions());
        mLivenessDetector.setConfiguration(mConfiguration);
        mLivenessDetector.setDetectorListener(mFaceDetectorListener);
        mLivenessDetector.start();
    }

    private void reset() {
        stopTimeoutTimer();
        mTVCooldown.setText("");
        mListBitmap.clear();
        isHandleStart = false;
        isFinished = false;
        isCapturing = false;
        mCircleProgressBar.setProgress(0);
        if (mLivenessDetector != null) {
            mLivenessDetector.setFaceActions(EKYCConfig.getActions(), EKYCConfig.getNumberOfActions());
            mLivenessDetector.reset();
        }
        if (mLivenessUIHelper != null) {
            mLivenessUIHelper.reset();
            mLivenessUIHelper.setSteps(mLivenessDetector.getAllSteps());
        }
        mLivenessLayout.setVisibility(View.INVISIBLE);
        mCameraBGImageView.setImageDrawable(this.getResources().getDrawable(R.mipmap.lifecardsdk_bg_camera_mask));
        mCameraMaskImageView.setImageDrawable(this.getResources().getDrawable(R.mipmap.lifecardsdk_bg_face_border_blue));
        mCaptureButton.setVisibility(View.INVISIBLE);
    }

    private void setupView() {
        mSoundPlayer = new SoundPlayer(this, this.mConfiguration.getEnableSound());
        initFaceDetector();
        mCircleProgressBar.setProgressFormatter(null);
        switch (this.mConfiguration.getCameraOption()) {
            case BACK:
                this.mCameraMode = 1;
                break;
            case FRONT:
                this.mCameraMode = 0;
                break;
            case BOTH:
                this.mCameraMode = 0;
        }
        mCameraView.isStretchToFill = true;
        mCameraView.cameraType = this.mCameraMode;
        mCameraView.imageFormat = 35;
        mCameraView.cameraMaxBufferWidth = 640;
        mCameraView.cameraMaxBufferHeight = 480;
        mCameraView.setBufferListener(new CameraViewEventListener() {

            public void onCameraError(String error) {
                Exception.handleMessageRequestFailure(FaceDetectorActivity.this, error, "");
            }

            public void onPreviewReady() {
                if (!isHandleStart) {
                    mSoundPlayer.doPlay(R.raw.lifecardsdk_guide);
                }
            }

            public void onBufferReady(Image image) {
                if (!isFinished && !isCapturing) {
                    try {
                        mLivenessDetector.doDetection(image, mCameraMode == 0 ? 3 : 1);
                    } catch (java.lang.Exception var3) {
                        var3.printStackTrace();
                    }

                }
            }

            public void onCapturedImage(Bitmap bm) {
                mListBitmap.add(new TVSelfieImage(ActionFace.portrait.name(), bm));
            }
        });
        mCircleProgressBar.setProgress(0);
        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCaptureButton.setVisibility(View.INVISIBLE);
                startCapture();
            }
        });
        numOfFrame = this.mConfiguration.hasLivenessStep() && this.mConfiguration.getLivenessMode() == SDKConfiguration.LivenessMode.PASSIVE ? 1 : 2;
        mProgressAnimation = new CircleProgressBar.CircleProgressBarAnimation(this.mCircleProgressBar, (long) (this.numOfFrame * 500));
        mLivenessUIHelper = new FaceDetectionHelper(this, mLivenessLayout);
        mLivenessUIHelper.setSteps(mLivenessDetector.getAllSteps());
        switch (mConfiguration.getLivenessMode()) {
            case NONE:
            case PASSIVE:
                this.mCaptureButton.setVisibility(View.VISIBLE);
                break;
            case ACTIVE:
                this.mCaptureButton.setVisibility(View.INVISIBLE);
        }
    }

    private void startCapture() {
        isCapturing = true;
        mCaptureButton.setEnabled(false);
        if (mTimer != null) {
            mTimer.cancel();
        }

        int countDownTime = numOfFrame * 500 + 200;
        mProgressAnimation.setProgress(100);
        mTimer = new CountDownTimer((long) countDownTime, 500L) {
            public void onTick(long millisUntilFinished) {
                if (mListBitmap.size() < numOfFrame + 4) {
                    mCameraView.takePicture();
                }
            }

            public void onFinish() {
                faceVerify();
            }
        };
        mTimer.start();
    }

    private void faceVerify() {
        if (PresenterUtils.isNetworkConnected(this)) {
            selfieImages = new ArrayList<>();
            selfieImages.addAll(mListBitmap.subList(0, EKYCConfig.getActions().size() > EKYCConfig.getNumberOfActions() ? EKYCConfig.getNumberOfActions() + 2 : EKYCConfig.getActions().size() + 2));
            List<ImageLive> imageLives = new ArrayList<>();
            FaceVerifyRequest faceVerifyRequest = new FaceVerifyRequest();
            faceVerifyRequest.setMobilePhone(LCConfig.getPhoneNumber());
            ImageUniqueID imageUniqueID = new ImageUniqueID();
            if (selfieImages.size() > 0) {
                imageUniqueID.setBase64(mPresenter.convertBase64ImageLive(selfieImages.get(0)));
                imageUniqueID.setImageType(selfieImages.get(0).getLabel());
                selfieImages.remove(0);
            }
            for (TVSelfieImage selfieImage : selfieImages) {
                ImageLive imageLive = new ImageLive();
                imageLive.setBase64(mPresenter.convertBase64ImageLive(selfieImage));
                imageLive.setImageType(selfieImage.getLabel());
                imageLives.add(imageLive);
            }

            faceVerifyRequest.setImageUniqueId(imageUniqueID);
            faceVerifyRequest.setImageLives(imageLives);

            String body = StringUtils.convertObjectToBase64(faceVerifyRequest);
            showLoading(true);
            mPresenter.faceVerify(body);
        } else {
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }


    }

    public void backToHome(int tabPosition) {
        Intent intent = new Intent(getApplicationContext(), LCHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.BundleConstants.TAB_POSITION, tabPosition);
        intent.putExtra(Constants.BundleConstants.SHOW_FACE_VERIFY_INFOR, true);
        startActivity(intent);
    }
    @Override
    public void faceVerifySuccess() {
        backToHome(4);
        Exception.handleMessageSuccess(this, getString(R.string.lifecardsdk_face_verify_success));
    }

    @Override
    public void faceVerifyFail() {
        finish();
    }
}
