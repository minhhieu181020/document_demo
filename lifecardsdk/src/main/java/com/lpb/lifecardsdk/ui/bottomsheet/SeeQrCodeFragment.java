package com.lpb.lifecardsdk.ui.bottomsheet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.PackageDetailResponse;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.widget.zxing.BarcodeEncoder;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.BarcodeFormat;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.MultiFormatWriter;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.WriterException;
import com.lpb.lifecardsdk.widget.zxing.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class SeeQrCodeFragment extends BottomSheetDialogFragment {

    private Button imgSaveQR;
    private Button imgShareQR;
    private boolean isClicked;
    private ImageView imgGenerateQR;
    private TextView tvNamePackage;
    private int i = 60;
    private int status = -1;
    private DownloadManager downloadManager;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.LifeCardSDK_AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lifecardsdk_seeqrcode_bottom_sheet, container, false);
        imgSaveQR = view.findViewById(R.id.imgSaveQR);
        imgShareQR = view.findViewById(R.id.imgShareQR);
        imgGenerateQR = view.findViewById(R.id.imgQRcode);
        tvNamePackage = view.findViewById(R.id.tvNamePackage);
        PackageDetailResponse.DefCardDto data = (PackageDetailResponse.DefCardDto) getArguments().getSerializable(Constants.BundleConstants.SEE_QR_CODE);
        String s = "channel_code;CARD_SALE;maSP;UUID";
        Random rd = new Random();
        int number = rd.nextInt();
        String tokent = LCConfig.getChannelCode()+";CARD_SALE;"+data.getCode()+";"+number;
        GenerateQR(tokent);
        tvNamePackage.setText(data.getName());
        Log.e("SeeQrCodeFragment", "Code: " + tokent);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }
        } else {

            // Code for Below 23 API Oriented Device
            // Do next code
        }
    }

    private boolean showDialogPermissionStorage() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.lifecardsdk_dialog_permission_storage, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.windowAnimations = R.style.LifeCardSDK_Zoom_Center;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.getWindow().setBackgroundDrawable(new InsetDrawable(new ColorDrawable(Color.TRANSPARENT), 100, 0, 100, 0));

        TextView tvAllow = dialogView.findViewById(R.id.tvAllow);
        TextView tvClose = dialogView.findViewById(R.id.tvClose);

        tvAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exception.handleMessageSuccess(getActivity(),getString(R.string.lifecardsdk_buy_card_notify_save_qr_success));
                dialog.dismiss();
                status = 1;
            }
        });

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                status = 0;
            }
        });
        dialog.show();
        dialog.setCancelable(false);
        if (status == 1) {
            return true;
        } else {
            return false;
        }

    }

    //permission
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Exception.handleMessageRequestFailure(getActivity(),getString(R.string.see_qr_please_grant_storage),"");
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    private void startTimer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                isClicked = false;
            }
        }, Config.DELAY_CLICK);
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("storeImage", "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("storeImage", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("storeImage", "Error accessing file: " + e.getMessage());
        }
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        Log.e("getOutputMediaFile: ", mediaStorageDir.getPath());
        return mediaFile;
    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();

        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
            //     Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
            out.flush();
            out.close();

        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
// Tell the media scanner about the new file so that it is
// immediately available to the user.
        MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });


//        try {
//            File myFile = new File(file.getPath());
//            MimeTypeMap mime = MimeTypeMap.getSingleton();
//            String ext = myFile.getName().substring(myFile.getName().lastIndexOf(".") + 1);
//            String type = mime.getMimeTypeFromExtension(ext);
//            Intent sharingIntent = new Intent("android.intent.action.SEND");
//            sharingIntent.setType(type);
//            sharingIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(myFile));
//            startActivity(Intent.createChooser(sharingIntent, "Share using"));
//        } catch (Exception e) {

//        }

    }

    void shareImg(Bitmap bitmap) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("storeImage", "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("storeImage", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("storeImage", "Error accessing file: " + e.getMessage());
        }

        try {
            File myFile = new File(pictureFile.getPath());
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = myFile.getName().substring(myFile.getName().lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);
            Intent sharingIntent = new Intent("android.intent.action.SEND");
            sharingIntent.setType(type);
            sharingIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(myFile));
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        } catch (java.lang.Exception e) {
           Exception.handleException(e,getActivity(),"","");

        }

    }

    private void GenerateQR(String string) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(string, BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            final Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

//            BitMatrix bitMatrix = multiFormatWriter.encode(string, BarcodeFormat.QR_CODE, 400, 400);
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            final Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgGenerateQR.setImageBitmap(bitmap);
            imgSaveQR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveImage(bitmap);
                    dismiss();
                    Exception.handleMessageSuccess(getActivity(),getString(R.string.lifecardsdk_buy_card_notify_save_qr_success));

//                    CustomToasNotOK.makeText(getActivity(), getString(R.string.lifecardsdk_buy_card_notify_save_qr_not_success), CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                    startTimer();
                }
            });

            imgShareQR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareImg(bitmap);
                    dismiss();
                    startTimer();
                }
            });


        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
