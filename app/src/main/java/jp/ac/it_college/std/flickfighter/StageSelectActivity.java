package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StageSelectActivity extends Activity{
    public static final String STAGE_ID = "stageId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stageselect);
    }

    public void goToBattle(int stageId){
        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra(STAGE_ID, stageId);
        startActivity(intent);
    }

    public void stageSelect(View view) {
        switch (view.getId()) {
            case R.id.button_stage1:
                goToBattle(1);
                break;
            case R.id.button_stage2:
                goToBattle(2);
                break;
            case R.id.button_stage3:
                goToBattle(3);
                break;
            case R.id.button_stage4:
                goToBattle(4);
                break;
            case R.id.button_stage5:
                goToBattle(5);
                break;
            case R.id.button_stage6:
                goToBattle(6);
                break;
            case R.id.button_stage7:
                goToBattle(7);
                break;
            case R.id.button_stage8:
                goToBattle(8);
                break;
            case R.id.button_stage9:
                goToBattle(9);
                break;
            case R.id.button_stage10:
                goToBattle(10);
                break;
        }
    }
}
