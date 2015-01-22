package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity implements View.OnClickListener{
    private SharedPreferences playerStatus;
    private static final String ATTACK = "attackLevel";
    private static final String DEFENCE = "defenceLevel";
    private static final String LIFE = "lifeLevel";
    private static final String POINT = "point";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        findViewById(R.id.button_levelUp_attack).setOnClickListener(this);
        findViewById(R.id.button_levelUp_defence).setOnClickListener(this);
        findViewById(R.id.button_levelUp_life).setOnClickListener(this);

        playerStatus = getSharedPreferences("status", MODE_PRIVATE);
        statusDisplay(); //ステータスの状態を表示
    }

    public void goToStageSelect(View v){
        Intent intent = new Intent(this, StageSelectActivity.class);
        startActivity(intent);
        finish();
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
                        } else {
                            Toast.makeText(getApplicationContext(), "ポイントが足りません！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
        }
    }
}
