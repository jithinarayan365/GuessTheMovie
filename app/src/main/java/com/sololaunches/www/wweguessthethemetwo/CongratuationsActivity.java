package com.sololaunches.www.wweguessthethemetwo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class CongratuationsActivity extends AppCompatActivity {

    boolean flag, one, two, three;
    public static final int CHANGE_BGCOLOR = 1;
    String ad;
    MediaPlayer mediaPlayer;
    String PACKAGE_NAME;
    private WeakReference<Context> mContext;
    ImageView wwe_image;
    private boolean exit = false;


    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }


    @Override
    public void onBackPressed() {
        if (exit) {
            mediaPlayer.pause();
            finish();
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Make to run your application only in portrait mode

        PACKAGE_NAME = getApplicationContext().getPackageName();
        int songID = getResources().getIdentifier(PACKAGE_NAME + ":raw/raw", null, null);
        mediaPlayer = MediaPlayer.create(this, songID);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();
        setContentView(R.layout.activity_congratuations);

        wwe_image = (ImageView) findViewById(R.id.wwe_image);


        TextView line1 = (TextView) findViewById(R.id.line1);
        TextView line2 = (TextView) findViewById(R.id.line2);
        ImageButton nxt = (ImageButton) findViewById(R.id.player);
        String coins = (String) getIntent().getSerializableExtra("coins");
        String points = (String) getIntent().getSerializableExtra("points");
        ad = (String) getIntent().getSerializableExtra("ad");


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
                Intent intent = new Intent(getApplicationContext(), ScreenOne.class);
                intent.putExtra("ad", ad);
                startActivity(intent);
                finish();
                if (ad.equals("F")) {
                    Intent intent1 = new Intent(getApplicationContext(), Certificate.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {


                if (one) {
                    wwe_image.setImageResource(R.drawable.wwe_then);
                    two = true;
                    one = false;

                } else if (two) {
                    Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.wwe_now);
                    wwe_image.setImageBitmap(bMap);
                    two = false;
                    three = true;

                } else if (three) {
                    Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.wwe_forever);
                    wwe_image.setImageBitmap(bMap);
                    one = true;
                    three = false;

                }
                String nextColor = ""; // Next background color;
                Message m = obtainMessage(CHANGE_BGCOLOR, nextColor);
                sendMessageDelayed(m, 2000);
            }
        }
    };


}
