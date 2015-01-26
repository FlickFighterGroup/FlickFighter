package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;


public class ResultActivity extends Activity
        implements ViewSwitcher.ViewFactory, View.OnClickListener {
    private SharedPreferences playerStatus;
    private int stageId;
    public static final String PREF_POINT = "point";
    //規定時間
    public static final long STIPULATED_TIME = 180 * 1000;
    private int[] img =  {
            R.drawable.true_img
            ,R.drawable.false_img
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        playerStatus = getSharedPreferences("status", MODE_PRIVATE);
        stageId = getIntent().getExtras().getInt(StageSelectActivity.STAGE_ID);
        checkChallenge();

        //リトライ・終了ボタンのsetOnClickListener
        findViewById(R.id.button_retry).setOnClickListener(this);
        findViewById(R.id.button_to_status).setOnClickListener(this);
    }

    private void checkChallenge() {

        ImageSwitcher challenge1 = (ImageSwitcher) findViewById(R.id.challenge_clear1);
        challenge1.setFactory(this);
        ImageSwitcher challenge2 = (ImageSwitcher) findViewById(R.id.challenge_clear2);
        challenge2.setFactory(this);
        ImageSwitcher challenge3 = (ImageSwitcher) findViewById(R.id.challenge_clear3);
        challenge3.setFactory(this);

        SharedPreferences.Editor editor = playerStatus.edit();
        if (playerStatus.getLong(BattleActivity.PREF_CLEAR_TIME, -1) < STIPULATED_TIME) {
            challenge1.setImageResource(img[0]);
            //TODO:３分以内にクリアしたflagを受け取るまでこのまま
            if (!getIntent().getExtras().getBoolean(stageId + BattleActivity.PREF_CLEAR_TIME, false)) {
                editor.putInt(PREF_POINT, playerStatus.getInt(PREF_POINT, 0) + 1)
                        .putBoolean(stageId + BattleActivity.PREF_CLEAR_TIME, true)
                        .apply();
            }
        } else {
            challenge1.setImageResource(img[1]);
        }

        if (true) {
            challenge2.setImageResource(img[0]);
            editor.putInt(PREF_POINT, playerStatus.getInt(PREF_POINT, 0) + 1)
                    .apply();
        } else {
            challenge2.setImageResource(img[1]);
        }

        if (getIntent().getExtras().getBoolean(BattleActivity.PREF_NO_MISTAKES)) {
            challenge3.setImageResource(img[0]);
            if (!playerStatus.getBoolean(stageId + BattleActivity.PREF_NO_MISTAKES, false)) {
                editor.putInt(PREF_POINT, playerStatus.getInt(PREF_POINT, 0) + 1)
                        .putBoolean(stageId + BattleActivity.PREF_NO_MISTAKES, true)
                        .apply();
            }
        } else {
            challenge3.setImageResource(img[1]);
        }

    }

    @Override
    public View makeView() {
        // ApiDemos->Views->ImageSwitcherのソースからメソッドを丸々コピー
        ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(
                Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT));
        return i;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.button_retry:
                intent = new Intent(this, BattleActivity.class)
                        .putExtra(StageSelectActivity.STAGE_ID, stageId);
                break;
            case R.id.button_to_status:
                intent = new Intent(this, StatusActivity.class);
                break;
        }

        if(intent != null) {
            startActivity(intent);
            finish();
        }
    }
}
