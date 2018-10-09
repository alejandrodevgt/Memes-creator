package dev.ale.android.memescreator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class HomeSplash extends AppCompatActivity {

    HomeSplash thisActivity;
    ImageView imageView;
    Animation zoomout;
    Animation zoom;
    boolean zoomed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_splash);
        thisActivity=this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);
        imageView = (ImageView) findViewById(R.id.image);


        try{getSupportActionBar().hide();}catch (Exception e){}
        CountDownTimer cdt = new CountDownTimer(2200,1000) {
            @Override
            public void onTick(long l) {
                if(zoomed){
                    imageView.setAnimation(zoomout);
                    imageView.startAnimation(zoomout);
                    zoomed=false;
                }else{
                    imageView.setAnimation(zoom);
                    imageView.startAnimation(zoom);
                    zoomed=true;
                }
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(thisActivity, MainActivity.class);
                startActivity(intent);
                thisActivity.finish();
            }
        };
        cdt.start();


    }
}
