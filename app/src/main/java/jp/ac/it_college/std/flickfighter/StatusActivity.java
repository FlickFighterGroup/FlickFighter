package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StatusActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences getPlayerStatus = getSharedPreferences("player_status", MODE_PRIVATE);
        int attack = getPlayerStatus.getInt("attack",1);
        int defense = getPlayerStatus.getInt("defence",0);
        int life = getPlayerStatus.getInt("life",3);
        Log.v("attack", Integer.toString(attack));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
    }

    public void upGrade(View v){
        Intent intent = new Intent(this, UpGradeActivity.class);
        startActivity(intent);
    }
    public void stageSelect(View v){
        Intent intent = new Intent(this, StageSelectActivity.class);
        startActivity(intent);
    }
}
