package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity implements View.OnClickListener{
    private SharedPreferences playerStatus;
    private static final String ATTACK = "attackLevel";
    private static final String DEFENCE = "defenceLevel";
    private static final String LIFE = "lifeLevel";
    private static final String POINT = "point";

    private TextView pointView;

    private SoundPool soundPool;
    private int buttonClickSoundId;
    private int cancelSoundId;
    private int levelUpSoundId;
    private int errorSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        findViewById(R.id.button_levelUp_attack).setOnClickListener(this);
        findViewById(R.id.button_levelUp_defence).setOnClickListener(this);
        findViewById(R.id.button_levelUp_life).setOnClickListener(this);
        findViewById(R.id.button_stage_select).setOnClickListener(this);

        playerStatus = getSharedPreferences("status", MODE_PRIVATE);
        statusDisplay(); //ステータスの状態を表示
        pointView = (TextView) findViewById(R.id.point_view_label);
        //現在のポイントを表示
        pointView.setText(String.valueOf(playerStatus.getInt(POINT, 0)));

        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
    }

    private void levelUp(final String statusName) {
        new AlertDialog.Builder(this)
                .setTitle("レベルアップ")
                .setMessage("必要ポイント: 5 \nレベルアップしますか? \n 現在のポイント：" + playerStatus.getInt(POINT, 0))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (5 <= playerStatus.getInt(POINT, 0)) {
                            int defaultValue = 0;
                            if (statusName.equals(ATTACK)) {
                                defaultValue = 1;
                            } else if (statusName.equals(DEFENCE)) {
                                defaultValue = 0;
                            } else if (statusName.equals(LIFE)) {
                                defaultValue = 5;
                            }
                            //プリファレンスに保存されている各ステータス値を加算
                            SharedPreferences.Editor editor = playerStatus.edit();
                            editor.putInt(statusName, playerStatus.getInt(statusName, defaultValue) + 1)
                                    .putInt(POINT, playerStatus.getInt(POINT, 0) - 5)
                                    .apply();
                            statusDisplay();
                            Toast.makeText(getApplicationContext(), "レベルアップしました！", Toast.LENGTH_SHORT).show();
                            pointView.setText(String.valueOf(playerStatus.getInt(POINT,0)));
                            //レベルアップのSEを再生
                            soundPool.play(levelUpSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
                        } else {
                            //エラー音を再生
                            soundPool.play(errorSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
                            Toast.makeText(getApplicationContext(), "ポイントが足りません！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //キャンセル音再生
                        soundPool.play(cancelSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
                        return;
                    }
                })
                .show();
    }

    private void statusDisplay(){
        ((TextView)findViewById(R.id.status_attack_level))
                .setText(String.valueOf("Lv." + playerStatus.getInt(ATTACK, 1)));
        ((TextView)findViewById(R.id.status_defence_level))
                .setText(String.valueOf("Lv." + playerStatus.getInt(DEFENCE, 0)));
        ((TextView)findViewById(R.id.status_life_level))
                .setText(String.valueOf("Lv." + playerStatus.getInt(LIFE, 5)));
    }

    @Override
    public void onClick(View view) {
        soundPool.play(buttonClickSoundId, 1.0f, 1.0f, 0, 0, 1.0f);

        switch (view.getId()) {
            case R.id.button_levelUp_attack:
                levelUp(ATTACK);
                break;
            case R.id.button_levelUp_defence:
                levelUp(DEFENCE);
                break;
            case R.id.button_levelUp_life:
                levelUp(LIFE);
                break;
            case R.id.button_stage_select:
                Intent intent = new Intent(this, StageSelectActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //ボタン押下時の効果音を読み込み
        buttonClickSoundId = soundPool.load(this, R.raw.se_button_click01, 1);
        cancelSoundId = soundPool.load(this, R.raw.se_cancel01, 1);
        levelUpSoundId = soundPool.load(this, R.raw.se_levelup01, 1);
        errorSoundId = soundPool.load(this, R.raw.se_error01, 1);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //SoundPoolの開放
        soundPool.release();
    }
}
