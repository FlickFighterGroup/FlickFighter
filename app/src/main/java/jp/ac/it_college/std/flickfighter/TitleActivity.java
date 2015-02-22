package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
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

    public void startClicked(View v) {
        Intent intent = new Intent(this, StatusAndStageSelectActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new AlertDialog.Builder(this)
                    .setTitle("確認")
                    .setMessage("ゲームを終了しますか?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
