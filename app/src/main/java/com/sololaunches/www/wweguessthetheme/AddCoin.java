package com.sololaunches.www.wweguessthetheme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AddCoin extends AppCompatActivity {

    WweDBAdapter wweDBAdapter;
    PlayerStatsBean playerStatsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coin);
        wweDBAdapter = new WweDBAdapter(this, null, null, 1);
        playerStatsBean = wweDBAdapter.getPlayerStats();



    }
}
