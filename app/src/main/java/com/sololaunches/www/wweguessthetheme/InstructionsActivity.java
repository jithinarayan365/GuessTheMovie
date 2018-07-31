package com.sololaunches.www.wweguessthetheme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class InstructionsActivity extends AppCompatActivity {

    ImageButton nxt_btn;
TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);


        nxt_btn = (ImageButton)findViewById(R.id.nxt_btn);
        message = (TextView)findViewById(R.id.message);
        message.setVisibility(View.INVISIBLE);



        nxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), ScreenOne.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
