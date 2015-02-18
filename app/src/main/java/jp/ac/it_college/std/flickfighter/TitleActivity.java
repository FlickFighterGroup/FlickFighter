package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;


public class TitleActivity extends Activity {

    private MediaPlayer bgm;
    private Winker winker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        winker = new Winker(findViewById(R.id.label_start));
        bgm = MediaPlayer.create(this, R.raw.title_bgm01);
        bgm.setLooping(true);
        bgm.setVolume(0.6f, 0.6f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // タイトル画面のSTARTの点滅
        winker.startWink();
        //BGM再生開始
        bgm.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //点滅停止
        winker.stopWink();
        //BGM一時停止
        bgm.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bgm.stop();
    }

    public void startClicked(View v){
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
        finish();
    }
}
