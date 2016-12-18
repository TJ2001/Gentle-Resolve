package com.example.tim.gentleresolve.main_ui;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tim.gentleresolve.R;

public class SplashScreenActivity extends AppCompatActivity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashTread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        Animation fromTop =  AnimationUtils.loadAnimation(this, R.anim.alpha);
        fromTop.reset();
        LinearLayout rectangles = (LinearLayout) findViewById(R.id.lin_lay);
        rectangles.clearAnimation();
        rectangles.startAnimation(anim);
        rectangles.startAnimation(fromTop);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();

        fromTop = AnimationUtils.loadAnimation(this, R.anim.translate_top);
        fromTop.reset();

        ImageView rectangleOne = (ImageView) findViewById(R.id.rectangleOne);
        rectangleOne.clearAnimation();
        rectangleOne.startAnimation(fromTop);
        ImageView rectangleTwo = (ImageView) findViewById(R.id.rectangleTwo);
        rectangleTwo.clearAnimation();
        rectangleTwo.startAnimation(anim);
        ImageView rectangleThree = (ImageView) findViewById(R.id.rectangleThree);
        rectangleThree.clearAnimation();
        rectangleThree.startAnimation(fromTop);

        ImageView rectangleFour = (ImageView) findViewById(R.id.rectangleFour);
        rectangleFour.clearAnimation();
        rectangleFour.startAnimation(anim);
        ImageView rectangleFive = (ImageView) findViewById(R.id.rectangleFive);
        rectangleFive.clearAnimation();
        rectangleFive.startAnimation(fromTop);


        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2700) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreenActivity.this,
                            LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreenActivity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreenActivity.this.finish();
                }

            }
        };
        splashTread.start();

    }
}
