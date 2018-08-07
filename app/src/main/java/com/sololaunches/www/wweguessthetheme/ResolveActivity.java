package com.sololaunches.www.wweguessthetheme;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResolveActivity extends AppCompatActivity {
    TextView tv, coinsTxt;
    ImageButton imageButton;
    String PACKAGE_NAME;
    WweDBAdapter wweDBAdapter;
    WweConvenience wweConvenience;
    PlayerStatsBean playerStatsBean;
    MediaPlayer mediaPlayer;
    TextView trivia;
    String lastStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        wweDBAdapter = new WweDBAdapter(this, null, null, 1);

        final String display = (String) getIntent().getSerializableExtra("player");
        final String image = (String) getIntent().getSerializableExtra("image");
        lastStatus = (String) getIntent().getSerializableExtra("finalstat");
        final String triviaGot = (String) getIntent().getSerializableExtra("trivia");

        trivia = (TextView) findViewById(R.id.trivia);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        tv = (TextView) findViewById(R.id.answer);
        coinsTxt = (TextView) findViewById(R.id.coins);

        tv.setText(image);
        trivia.setText(triviaGot);
        imageButton = (ImageButton) findViewById(R.id.ok);
        ImageView superstar = (ImageView) findViewById(R.id.super_star);
        int imgId = getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + image, null, null);
        superstar.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
        playerStatsBean = wweDBAdapter.getPlayerStats();

        int songID = getResources().getIdentifier(PACKAGE_NAME + ":raw/" + image, null, null);
        mediaPlayer = MediaPlayer.create(this, songID);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();

        int coins = Integer.parseInt(playerStatsBean.getCoins()) - 50;
        int points = Integer.parseInt(playerStatsBean.getPoints()) + 3;
        coinsTxt.setText(coins + " coins left");

        playerStatsBean.setPoints(String.valueOf(points));
        playerStatsBean.setCoins(String.valueOf(coins));
        wweDBAdapter.updateStateUp(playerStatsBean, image);

        Toast toast = Toast.makeText(getApplicationContext(), "Click OK for next Level", Toast.LENGTH_LONG);
        toast.show();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();

                if (lastStatus.equalsIgnoreCase("F")) {
                    wweDBAdapter.updateFinishGame(playerStatsBean.getPlayer());
                    Intent intent1 = new Intent(getApplicationContext(), Certificate.class);
                    intent1.putExtra("points", playerStatsBean.getPoints());
                    intent1.putExtra("player", playerStatsBean.getPlayer());
                    startActivity(intent1);
                    finish();
                } else {

                    Intent intent = new Intent(getApplicationContext(), ScreenOne.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        Runtime.getRuntime().gc();
    }
}
