package com.game.dabba.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class gameSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        Switch switchPlaySecond = (Switch) findViewById(R.id.switchPlaySecond);
        switchPlaySecond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSettings();
            }
        });

        Switch switchSelectO = (Switch) findViewById(R.id.switchSelectO);
        switchSelectO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSettings();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //check scoring data
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean playFirst = sharedPref.getBoolean(getString(R.string.setting_play_first), true);
        boolean selectX = sharedPref.getBoolean(getString(R.string.setting_select_x), true);


        Switch switchPlaySecond = (Switch) findViewById(R.id.switchPlaySecond);
        switchPlaySecond.setChecked(!playFirst);
        Switch switchSelectO = (Switch) findViewById(R.id.switchSelectO);
        switchSelectO.setChecked(!selectX);
    }

    private void updateSettings(){

        Switch switchPlaySecond = (Switch) findViewById(R.id.switchPlaySecond);
        Switch switchSelectO = (Switch) findViewById(R.id.switchSelectO);

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.setting_select_x), !switchSelectO.isChecked());
        editor.putBoolean(getString(R.string.setting_play_first), !switchPlaySecond.isChecked());
        editor.commit();
    }
}
