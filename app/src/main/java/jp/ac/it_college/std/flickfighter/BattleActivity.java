package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class BattleActivity extends Activity
        implements TextWatcher, LimitTimeSurfaceView.EnemyActionListener {
    private InputMethodManager inputMethodManager;
    private TextView enemyString;
    private EditText userInputText;
    private String TAG = "BattleActivity";
    private String text;
    private LimitTimeSurfaceView limitTimeSurfaceView;
    private Handler mHandler;
    //Timer初期化
    private TextView timerLabel;
    private Timer timer;
    //intentで渡すフィールド
    private int stageId;
    private long currentTime = 0 ;
    private boolean noMistakes;
    //getIntentで使う定数
    public static final String PREF_NO_MISTAKES = "PREF_NO_MISTAKESs";
    public static final String PREF_CLEAR_TIME = "clear_time";
    //敵キャラのパスを登録
    private int[] enemyPath = {
            R.drawable.zako1
            , R.drawable.zako2
            , R.drawable.zako3
            , R.drawable.zako4
    };
    private ImageView enemyImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        stageId = getIntent().getExtras().getInt(StageSelectActivity.STAGE_ID);
        randomStringView();
        randomEnemyView();

        //Timer表示
        timerLabel = (TextView) findViewById(R.id.timer_label);
        // タイマーをセット
        timer = new Timer();
        TimerTask timerTask = new Task1();
        timer.scheduleAtFixedRate(timerTask, 0, 100);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler = new Handler(getMainLooper());
        SurfaceView limitTimeBar = (SurfaceView) findViewById(R.id.limit_time_bar);
        limitTimeSurfaceView = new LimitTimeSurfaceView(limitTimeBar, mHandler, this);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        enemyString = (TextView) findViewById(R.id.enemyString);
        userInputText = (EditText) findViewById(R.id.userInputText);
        userInputText.addTextChangedListener(this);
        noMistakes = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        limitTimeSurfaceView.resetLimitTime();
        gameStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameStop();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //エンターキー押下時の処理を無効にする
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            gameStop();

            new AlertDialog.Builder(this)
                    .setTitle("確認")
                    .setMessage("ステージ選択画面へ戻りますか?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), StageSelectActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gameStart();
                        }
                    }).show();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



    public void gameStart() {
        limitTimeSurfaceView.startMeasurement();
    }

    public void gameStop() {
        limitTimeSurfaceView.stopMeasurement();
    }

    public void goToResult(View view) {
        Intent intent = new Intent(this, ResultActivity.class)
                .putExtra(StageSelectActivity.STAGE_ID, stageId)
                .putExtra(PREF_NO_MISTAKES, noMistakes)
                .putExtra(PREF_CLEAR_TIME, currentTime);
        startActivity(intent);
        finish();
    }

    public void keyBoardShown(View view) {
        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    public void randomStringView() {
        //敵の文字列を表示
        TextView textView = (TextView) findViewById(R.id.enemyString);
        text = EnemyInfo.randomWordView(stageId);
        textView.setText(text);
    }

    public void randomEnemyView() {
        //敵キャラ表示
        enemyImage = (ImageView) findViewById(R.id.enemy_image);
        enemyImage.setImageResource(enemyPath[EnemyInfo.randomEnemySummons(enemyPath.length)]);
    }

    public void enemyAnimation(ImageView view) {
        ScaleAnimation animation = new ScaleAnimation(
                1, 2, 1, 2,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);

        view.startAnimation(animation);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d(TAG, "beforeTextChanged() s:" + s.toString() +
                " start:" + String.valueOf(start) + " count:" + String.valueOf(count) +
                " after:" + String.valueOf(after));
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d(TAG, "onTextChanged() s:" + s.toString() +
                " start:" + String.valueOf(start) + " before:" + String.valueOf(before) +
                " count:" + String.valueOf(count));

        //入力された文字の長さがenemyStringより長い場合はメソッドを抜ける
        if(s.length() > enemyString.length()) {
            return;

        }

        if (enemyString.getText().toString().substring(0, s.length())
                .equals(s.toString().substring(0, s.length()))) {
            Log.d("judge", String.valueOf(true));
            if (enemyString.getText().length() == s.length()) {
                //全部打ち終わったら文字を切り替える
                userInputText.setText("");
                randomStringView();
                // リミットタイムをリセットする
                limitTimeSurfaceView.resetLimitTime();
            } else {
                //文字列が一致すれば色を変える
                String txtStr = "<font color=#00ff00>" + text.substring(0,s.length()) +
                        "</font>" + text.substring(s.length(), text.length()) ;
                enemyString.setText(Html.fromHtml(txtStr));
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "afterTextChanged()");
    }

    @Override
    public void enemyAttack() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                enemyAnimation((ImageView) findViewById(R.id.enemy_image));
            }
        });
        gameStart();

    }
    public class Task1 extends TimerTask {

        private Handler handler;
        private long startTime;

        public Task1() {
            handler = new Handler();
            startTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
            // Viewの操作だけじゃなくてトーストを出すのにもHandler使わないといけないのか。。。
            handler.post(new Runnable() {
                @Override
                public void run() {
                    currentTime = System.currentTimeMillis() - startTime;
                    String elapsedTime = String.format("%02d:%02d",
                            currentTime / 60000, currentTime % 60000 / 1000);
                    timerLabel.setText(String.valueOf(elapsedTime));
                }
            });
        }
    }
}