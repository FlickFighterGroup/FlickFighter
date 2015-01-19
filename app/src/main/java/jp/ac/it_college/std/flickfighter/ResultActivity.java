package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;


public class ResultActivity extends Activity implements ViewSwitcher.ViewFactory {
    private SharedPreferences playerStatus;
    private int stageId;
    private int[] img =  {
            R.drawable.true_img
            ,R.drawable.false_img
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        playerStatus = getSharedPreferences("status", MODE_PRIVATE);
        stageId = getIntent().getExtras().getInt("stage_id");
        checkChallenge();
    }

    private void checkChallenge() {

        ImageSwitcher challenge1 = (ImageSwitcher) findViewById(R.id.challenge_clear1);
        challenge1.setFactory(this);
        ImageSwitcher challenge2 = (ImageSwitcher) findViewById(R.id.challenge_clear2);
        challenge2.setFactory(this);
        ImageSwitcher challenge3 = (ImageSwitcher) findViewById(R.id.challenge_clear3);
        challenge3.setFactory(this);

        SharedPreferences.Editor editor = playerStatus.edit();
        if (true) {
            challenge1.setImageResource(img[0]);
            //TODO:３分以内にクリアしたflagを受け取るまでこのまま
//            if (playerStatus.getBoolean("stage_challenge1", false)) {

//            } else {
                editor.putInt("point", playerStatus.getInt("point", 0) + 1)
                        .putBoolean("stage1_challenge1", true)
                        .apply();
//            }
        } else {
            challenge1.setImageResource(img[1]);
        }

        if (true) {
            challenge2.setImageResource(img[0]);
            editor.putInt("point", playerStatus.getInt("point", 0) + 1)
                    .apply();
        } else {
            challenge2.setImageResource(img[1]);
        }

        if (getIntent().getExtras().getBoolean("no_mistake")) {
            challenge3.setImageResource(img[0]);
            if (playerStatus.getBoolean(stageId + "3", false)) {

            } else {
                editor.putInt("point", playerStatus.getInt("point", 0) + 1)
                        .putBoolean(stageId + "3", true)
                        .apply();
            }
        } else {
            challenge3.setImageResource(img[1]);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}
