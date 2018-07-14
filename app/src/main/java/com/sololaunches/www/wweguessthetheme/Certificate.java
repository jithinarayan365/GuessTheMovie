package com.sololaunches.www.wweguessthetheme;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Certificate extends AppCompatActivity {

    private boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
            //  System.exit(0);

        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);


        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_certificate);


        String points = (String) getIntent().getSerializableExtra("points");
        String player = (String) getIntent().getSerializableExtra("player");

        TextView score = (TextView)findViewById(R.id.score);
        TextView playerName = (TextView)findViewById(R.id.player);
        ImageButton nxt_btn  = (ImageButton)findViewById(R.id.nxt);
        score.setText("Score :"+points+ " points");
        playerName.setText(" "+player);


        nxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });



    }
}
