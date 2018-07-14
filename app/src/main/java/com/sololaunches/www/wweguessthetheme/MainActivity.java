package com.sololaunches.www.wweguessthetheme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton nextBtn;
    EditText player;
    WweDBAdapter wweDBAdapter;
    WweConvenience wweConvenience;
    PlayerStatsBean playerStatsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        wweConvenience = new WweConvenience();
        wweDBAdapter = new WweDBAdapter(this, null, null, 1);
        nextBtn = (ImageButton) findViewById(R.id.nxt_btn);
        player = (EditText) findViewById(R.id.player);
        playerStatsBean =  wweConvenience.getPlayerStats(getApplicationContext());

        if(playerStatsBean!= null){
            if("N".equals(playerStatsBean.getStatus())){
                Intent i = new Intent(getApplication(), ScreenOne.class);
                startActivity(i);
                finish();
            }
        }



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String playerName = player.getText().toString();
                if(playerName.trim().equals("Enter player name here")){
                    playerName = "player";
                }

//                Log.d("playerStatsBean", "onClick: "+playerStatsBean.getPlayer());
                if(playerStatsBean== null){
                    wweDBAdapter.setPlayerName(playerName);
                }

                Intent i = new Intent(getApplication(), ScreenOne.class);
                startActivity(i);
                finish();
            }
        });
    }
}
