package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StatusActivity extends Activity implements View.OnClickListener{
    private SharedPreferences playerStatus;
    private static final String ATTACK = "attackLevel";
    private static final String DEFENCE = "defenceLevel";
    private static final String LIFE = "lifeLevel";

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

    public void stageSelect(View v){
        Intent intent = new Intent(this, StageSelectActivity.class);
        startActivity(intent);
    }

    private void levelUp(String statusName) {
        //プリファレンスに保存されている各ステータス値を加算
        SharedPreferences.Editor editor = playerStatus.edit();
        editor.putInt(statusName, playerStatus.getInt(statusName, 0) + 1)
                .apply();

        statusDisplay();
    }

    private void statusDisplay(){
        ((TextView)findViewById(R.id.status_attack_level))
                .setText(String.valueOf("Lv." + playerStatus.getInt(ATTACK, 0)));
        ((TextView)findViewById(R.id.status_defence_level))
                .setText(String.valueOf("Lv." + playerStatus.getInt(DEFENCE, 0)));
        ((TextView)findViewById(R.id.status_life_level))
                .setText(String.valueOf("Lv." + playerStatus.getInt(LIFE, 0)));
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
