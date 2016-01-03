package com.idlepilot.xuzy.scrap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Administrator on 2015/12/27.
 */
public class SplashActivity extends Activity {
    private GifView startGif;

    //等待gif播放完毕
    private final int SPLASH_DISPLAY_LENGHT = 8000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        startGif= (GifView) findViewById(R.id.startGif);
        startGif.setMovieResource(R.raw.start);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
