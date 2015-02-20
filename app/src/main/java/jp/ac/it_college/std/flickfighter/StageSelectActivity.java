package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StageSelectActivity extends Activity{

    private static final String STAGE_CLEAR = "stage_clear";
    public static final String STAGE_ID = "stageId";
    private ImageView clearLabel;
    private SharedPreferences playerStatus;

    private SoundPool soundPool;
    private int buttonClickSoundId;
    private int cancelSoundId;

    private MediaPlayer bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stageselect);

        playerStatus = getSharedPreferences("status", Context.MODE_PRIVATE);

        //img_clear_labelをステージの数だけ回してIDをそれぞれ取得し、クリアしてれば画像を表示
        for (int stageId = 1; stageId <= 10; stageId++) {
            int viewId = getResources().getIdentifier("img_clear_label" + stageId, "id", getPackageName());
            clearLabel = (ImageView) findViewById(viewId);

            if (playerStatus.getBoolean(stageId + STAGE_CLEAR, false)) {
                if (playerStatus.getBoolean(stageId + BattleActivity.PREF_CLEAR_TIME, false)
                         && playerStatus.getBoolean(stageId + BattleActivity.PREF_NO_DAMAGE, false)
                         && playerStatus.getBoolean(stageId + BattleActivity.PREF_RARE_CRUSHING, false)) {
                    clearLabel.setImageResource(R.drawable.clear1);
                } else {
                    clearLabel.setImageResource(R.drawable.clear2);
                }
            }
        }

        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        bgm = MediaPlayer.create(this, R.raw.status_and_stageselect_bgm01);
        bgm.setLooping(true);
        bgm.setVolume(0.3f, 0.3f);
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
                        soundPool.play(cancelSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
                        return;
                    }
                }).show();
    }

    public void goToStatus(View view) {
        soundPool.play(buttonClickSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
        Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
        startActivity(intent);
        finish();
    }

    public void stageSelect(View view) {
        soundPool.play(buttonClickSoundId, 1.0f, 1.0f, 0, 0, 1.0f);

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

    @Override
    protected void onResume() {
        super.onResume();
        //効果音の読み込み
        buttonClickSoundId = soundPool.load(this, R.raw.se_button_click01, 1);
        cancelSoundId = soundPool.load(this, R.raw.se_cancel01, 1);
        //BGM再生開始
        bgm.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //BGM再生停止
        bgm.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //SoundPoolの開放
        soundPool.release();
    }
}
