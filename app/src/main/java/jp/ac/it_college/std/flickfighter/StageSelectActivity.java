package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StageSelectActivity extends Activity{
    public static final String STAGE_ID = "stageId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stageselect);
    }

    public void goToBattle(final int stageId, String stageName){
        final Intent intent = new Intent(this, BattleActivity.class);
        new AlertDialog.Builder(this)
                .setTitle("ステージセレクト")
                .setMessage(stageName + "に挑戦しますか?")
                .setPositiveButton("挑戦", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        intent.putExtra(STAGE_ID, stageId);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                }).show();
    }

    public void stageSelect(View view) {
        switch (view.getId()) {
            case R.id.button_stage1:
                goToBattle(1, (String)((TextView)view).getText());
                break;
            case R.id.button_stage2:
                goToBattle(2, (String)((TextView)view).getText());
                break;
            case R.id.button_stage3:
                goToBattle(3, (String)((TextView)view).getText());
                break;
            case R.id.button_stage4:
                goToBattle(4, (String)((TextView)view).getText());
                break;
            case R.id.button_stage5:
                goToBattle(5, (String)((TextView)view).getText());
                break;
            case R.id.button_stage6:
                goToBattle(6, (String)((TextView)view).getText());
                break;
            case R.id.button_stage7:
                goToBattle(7, (String)((TextView)view).getText());
                break;
            case R.id.button_stage8:
                goToBattle(8, (String)((TextView)view).getText());
                break;
            case R.id.button_stage9:
                goToBattle(9, (String)((TextView)view).getText());
                break;
            case R.id.button_stage10:
                goToBattle(10, (String)((TextView)view).getText());
                break;
        }
    }
}
