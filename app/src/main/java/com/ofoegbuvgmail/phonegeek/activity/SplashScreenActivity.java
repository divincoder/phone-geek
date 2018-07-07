package com.ofoegbuvgmail.phonegeek.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.content.Intent;
import android.view.animation.Animation;
import  android.os.Handler;
import android.view.View;

import com.ofoegbuvgmail.phonegeek.R;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView logo;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        overridePendingTransition(R.anim.fade_in, android.R.anim.slide_in_left);
        View decorView = getWindow().getDecorView();
    // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
//// Remember that you should never show the action bar if the
//// status bar is hidden, so hide that too if necessary.
//         ActionBar actionBar = getActionBar();
//            actionBar.hide();


        logo = findViewById(R.id.logo);

        animation = android.view.animation.AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setDuration(2000);

        logo.setAnimation(animation);

 animation.start();

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        finish();
                    }
                }, 2000);
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {

            }
        });
    }
}
