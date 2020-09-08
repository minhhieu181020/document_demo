package com.lpb.lifecard;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.UUID;

public class SplashActivity extends AppCompatActivity {
    private ImageView imgSplashImage;
    private NumberProgressBar numberProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        numberProgressBar = findViewById(R.id.number_progress_bar);
        imgSplashImage = findViewById(R.id.imgSplashImage);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgSplashImage, "alpha", 0f, 1f);
        animator.setDuration(1000);
        animator.start();


        RunAble runAble = new RunAble(this);
        new Thread(runAble).start();
    }
    class RunAble implements Runnable {
        final int seconds;
        final Context context;

        RunAble(Context context) {
            this.seconds = 5;
            this.context = context;
        }

        @Override
        public void run() {
            for (int i = 0; i <= 100; i++) {
                Handler handler = new Handler(Looper.getMainLooper());
                final int intI = i;
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        numberProgressBar.setVisibility(View.VISIBLE);
                        numberProgressBar.setProgress(intI);
                        if (intI == 100) {
                            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            SplashActivity.this.finish();
                        }
                    }
                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }
}
