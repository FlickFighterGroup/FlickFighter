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
        implements View.OnClickListener {

    private int stageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        stageId = getIntent().getExtras().getInt(StageSelectActivity.STAGE_ID);

        //リトライ・終了ボタンのsetOnClickListener
        findViewById(R.id.button_retry).setOnClickListener(this);
        findViewById(R.id.button_to_status).setOnClickListener(this);

        //ステージ突破成功時にチャレンジフラグメントを表示する
        if(true) {
            //各チャレンジの成功・失敗判定をBundleにput
            Bundle challengeFrag = new Bundle();
            //クリアタイム
            challengeFrag.putBoolean(
                    ChallengeFragment.CHALLENGE_CLEAR_WITHIN_3MIN,
                    getIntent().getBooleanExtra(BattleActivity.PREF_CLEAR_TIME, false));

            challengeFrag.putBoolean(
                    ChallengeFragment.CHALLENGE_CLEAR_NO_DAMAGE,
                    getIntent().getBooleanExtra(BattleActivity.PREF_CLEAR_TIME, false));

            challengeFrag.putBoolean(
                    ChallengeFragment.CHALLENGE_CLEAR_RARE_CRUSHING,
                    getIntent().getBooleanExtra(BattleActivity.PREF_CLEAR_TIME, false));

            //ステージ突破成功・失敗の判定後フラグメントを切り替える
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .add(R.id.fragment_container,
                                ChallengeFragment.newInstance(challengeFrag))
                        .commit();
            }
        }
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
