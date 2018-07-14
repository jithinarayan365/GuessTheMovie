package com.sololaunches.www.wweguessthetheme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.VideoListener;

import java.lang.ref.WeakReference;

public class CongratuationsActivity extends AppCompatActivity {

    RelativeLayout layout;
    boolean flag, one, two, three;
    public static final int CHANGE_BGCOLOR = 1;
    private StartAppAd startAppAdInterstitial = new StartAppAd(this);
    private StartAppAd startAppAd = new StartAppAd(this);
    String ad;
    MediaPlayer mediaPlayer;
    String PACKAGE_NAME;
    private WeakReference<Context> mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        PACKAGE_NAME = getApplicationContext().getPackageName();
        int songID = getResources().getIdentifier(PACKAGE_NAME + ":raw/raw", null, null);
        mediaPlayer = MediaPlayer.create(this, songID);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();

        setContentView(R.layout.activity_congratuations);

        StartAppSDK.init(this, "203580885", true);
        startAppAdInterstitial = new StartAppAd(this);
        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO);
        startAppAd.setVideoListener(new VideoListener() {
            @Override
            public void onVideoCompleted() {

            }
        });

        // StartAppAd.showSplash(this, savedInstanceState);

        TextView line1 = (TextView) findViewById(R.id.line1);
        TextView line2 = (TextView) findViewById(R.id.line2);
        ImageButton nxt = (ImageButton) findViewById(R.id.player);
        String coins = (String) getIntent().getSerializableExtra("coins");
        String points = (String) getIntent().getSerializableExtra("points");
        ad = (String) getIntent().getSerializableExtra("ad");
        Log.d("AD check", "onCreate: " + ad);


        layout = (RelativeLayout) findViewById(R.id.congrats);
        String nextColor = ""; // Next background color;
        Message m = handler.obtainMessage(CHANGE_BGCOLOR, nextColor);
        one = true;
        handler.sendMessageDelayed(m, 200);

        line1.setText(" You have scored " + points + " points");
        line2.setText("You have a total of " + coins + " coins");


        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                mediaPlayer.stop();
                layout.setBackgroundResource(R.drawable.frame_1);
                Intent intent = new Intent(getApplicationContext(), ScreenOne.class);
                intent.putExtra("ad", ad);

                startActivity(intent);
                finish();


                if (ad.equals("Y")) {
                    startAppAdInterstitial.showAd();

                } else if (ad.equals("V")) {
                    startAppAd.showAd();
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        startAppAdInterstitial.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        startAppAdInterstitial.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onBackPressed() {
        startAppAd.onBackPressed();
        super.onBackPressed();
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {

                layout.setBackgroundResource(R.drawable.frame_1);

                if (one) {

                    Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.frame_1);
                    BitmapDrawable background = new BitmapDrawable(getResources(), bMap);
                    layout.setBackground(background);
                    two = true;
                    one = false;
                } else if (two) {
                    Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.frame_2);
                    BitmapDrawable background = new BitmapDrawable(getResources(), bMap);
                    layout.setBackground(background);
                    two = false;
                    three = true;
                } else if (three) {
                    Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.frame_3);
                    BitmapDrawable background = new BitmapDrawable(getResources(), bMap);
                    layout.setBackground(background);
                    one = true;
                    three = false;
                }
                String nextColor = ""; // Next background color;
                Message m = obtainMessage(CHANGE_BGCOLOR, nextColor);
                sendMessageDelayed(m, 200);
            }
        }
    };


}
