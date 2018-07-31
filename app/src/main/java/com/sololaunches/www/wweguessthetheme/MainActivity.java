package com.sololaunches.www.wweguessthetheme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton nextBtn;
    EditText player;
    TextView note;
    WweDBAdapter wweDBAdapter;
    WweConvenience wweConvenience;
    PlayerStatsBean playerStatsBean;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize = 0;
    InputMethodManager imm;

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
        note =(TextView)  findViewById(R.id.note);
        note.setVisibility(View.INVISIBLE);
        if(playerStatsBean!= null){
            if("N".equals(playerStatsBean.getStatus())){
                Intent i = new Intent(getApplication(), InstructionsActivity.class);
                startActivity(i);
                finish();
            }
        }



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                note.setVisibility(View.VISIBLE);

                imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(note.getWindowToken(), 0);

                String playerName = player.getText().toString();
                if(playerName.trim().equals("Enter player name here")){
                    playerName = "player";
                }

//                Log.d("playerStatsBean", "onClick: "+playerStatsBean.getPlayer());
                if(playerStatsBean== null){
                    wweDBAdapter.setPlayerName(playerName);
                }

                Intent i = new Intent(getApplication(), InstructionsActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
