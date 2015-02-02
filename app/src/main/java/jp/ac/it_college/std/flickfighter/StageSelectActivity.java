package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StageSelectActivity extends Activity{

    private static final String STAGE_CLEAR = "stage_clear";
    public static final String STAGE_ID = "stageId";
    private ImageView clearLabel;
    private SharedPreferences playerStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stageselect);

        playerStatus = getSharedPreferences("status", Context.MODE_PRIVATE);

        //img_clear_labelをステージの数だけ回してIDをそれぞれ取得し、クリアしてれば画像を表示
        for (int i = 1; i < 10; i++) {
            int viewId = getResources().getIdentifier("img_clear_label" + i, "id", getPackageName());
            clearLabel = (ImageView) findViewById(viewId);
            if (playerStatus.getBoolean(i + STAGE_CLEAR, false)) {
                clearLabel.setImageResource(R.drawable.true_img);
            }
        }
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
                        finish();
                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                }).show();
    }

    public void goToStatus(View view) {
        Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
        startActivity(intent);
        finish();
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
