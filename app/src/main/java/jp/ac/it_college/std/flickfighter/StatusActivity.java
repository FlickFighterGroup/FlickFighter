package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StatusActivity extends Activity{

    //プレイヤーのステータスを取得
    SharedPreferences getPlayerStatus = getSharedPreferences("player_status", MODE_PRIVATE);
    int attack = getPlayerStatus.getInt("attack",1);
    int defense = getPlayerStatus.getInt("defence",0);
    int life = getPlayerStatus.getInt("life",3);
    int point = getPlayerStatus.getInt("point",0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("attack", Integer.toString(attack));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
    }

    public void upGrade(String status_name, int status_point){
        if (point >= 5){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("確認");
            alertDialog.setMessage("ポイントを５消費しますがよろしいですか？");

            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        }else{

        }
    }

    public void stageSelect(View v){
        Intent intent = new Intent(this, StageSelectActivity.class);
        startActivity(intent);
    }
}
