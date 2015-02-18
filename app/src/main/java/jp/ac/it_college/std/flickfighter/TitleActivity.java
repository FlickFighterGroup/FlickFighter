package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;


public class TitleActivity extends Activity {

    private MediaPlayer bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        bgm = MediaPlayer.create(this, R.raw.title_bgm01);
        bgm.setLooping(true);
        bgm.setVolume(0.6f, 0.6f);
        bgm.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // タイトル画面のSTARTの点滅
        new Winker(findViewById(R.id.label_start))
                .startWink();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bgm.stop();
    }

    public void startClicked(View v){
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
        finish();
    }
}
