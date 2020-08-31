package com.game.dabba.tictactoe;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //add button handlers
        Button btn_newGame = (Button) findViewById(R.id.btn_newGame);
        btn_newGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, gamePlay.class);
                HomePage.this.startActivity(myIntent);
            }
        });

        Button btn_stats = (Button) findViewById(R.id.btn_stats);
        btn_stats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, statistics.class);
                HomePage.this.startActivity(myIntent);
            }
        });

        Button btn_settings = (Button) findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, gameSettings.class);
                HomePage.this.startActivity(myIntent);
            }
        });
    }
}
