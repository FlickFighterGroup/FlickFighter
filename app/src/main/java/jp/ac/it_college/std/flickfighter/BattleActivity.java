package jp.ac.it_college.std.flickfighter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class BattleActivity extends Activity
        implements TextWatcher, LimitTimeSurfaceView.EnemyActionListener
        , DetectableKeyboard.OnKeyboardVisibilityListener {
    //TODO:バトル数左上に表示しろ ー＞　完了？
    //TODO:敵のHPバーと自分のHPバー表示
    private int playerPow;
    private int playerDefence;
    private int playerLife;
    private InputMethodManager inputMethodManager;
    private EditText userInputText;
    private LinearLayout textBox;
    private String TAG = "BattleActivity";
    private String text;
    private LimitTimeSurfaceView limitTimeSurfaceView;
    private SurfaceView limitTimeBar;
    private Handler mHandler;
    //Timer初期化
    private TextView timerLabel;
    //intentで渡すフィールド
    private int stageId;
    private long currentTime = 0;
    //getIntentで使う定数
    public static final String PREF_RARE_CRUSHING = "rare_crushing";
    public static final String PREF_CLEAR_TIME = "clear_time";
    public static final String PREF_NO_DAMAGE = "no_damage";
    public static final String PREF_CLEAR_JUDGE = "clearJudge";
    private ProgressBar playerLifeGauge;
    //フラグいろいろ
    private boolean rareFrag = false;
    private boolean no_damage = true;
    //TODO:変数名要変更
    private int maxBattleCount = 5;
    private int battleCount = 0;
    private TextView battleCountView;

    private TextView enemyString;
    private int enemyLife;
    private int enemyPow;
    private ProgressBar enemyLifeGauge;
    private ImageView enemyImage;
    //キーボード表示ボタン
    private Button mKeyBoardShownButton;

    //タイマー用フィールド
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        stageId = getIntent().getExtras().getInt(StageSelectActivity.STAGE_ID);
        SharedPreferences playerStatus = getSharedPreferences("status", MODE_PRIVATE);
        playerPow = playerStatus.getInt("attackLevel", 1);
        playerDefence = playerStatus.getInt("defenceLevel", 0);
        playerLife = playerStatus.getInt("lifeLevel", 5);

        //プレイヤーの体力ゲージ表示
        playerLifeGauge = (ProgressBar) findViewById(R.id.player_life_gauge);
        playerLifeGauge.setMax(playerLife);
        playerLifeGauge.setProgress(playerLife);

        battleCountView = (TextView) findViewById(R.id.battle_count);
        battleCountView.setText(battleCount + " / " + maxBattleCount);
        //敵キャラ表示
        enemyLifeGauge = (ProgressBar) findViewById(R.id.enemy_life_gauge);
        enemyImage = (ImageView) findViewById(R.id.enemy_image);
        randomStringView();
        enemySummon();

        //Timer表示
        timerLabel = (TextView) findViewById(R.id.timer_label);

        textBox = (LinearLayout) findViewById(R.id.text_box);

        mHandler = new Handler(getMainLooper());
        limitTimeBar = (SurfaceView) findViewById(R.id.limit_time_bar);
        limitTimeSurfaceView = new LimitTimeSurfaceView(limitTimeBar, mHandler, this);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        enemyString = (TextView) findViewById(R.id.enemyString);
        userInputText = (EditText) findViewById(R.id.userInputText);
        userInputText.addTextChangedListener(this);

        //キーボードの表示・非表示を検出するリスナーをセット
        new DetectableKeyboard(this).setKeyboardListener(this);

        //キーボード表示ボタンのonClickListener
        mKeyBoardShownButton = (Button) findViewById(R.id.keyBoardShownButton);
        mKeyBoardShownButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        keyBoardShown();
                    }
                });

        gameReady();
    }

    private void gameReady() {
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        enemyImage.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // タイマーをセット
                timer = new Timer();
                timerTask = new Task1();
                timer.scheduleAtFixedRate(timerTask, 0, 100);
                gameStart();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d("hasFocus:", String.valueOf(hasFocus));

        //バトル画面のレイアウトサイズを取得
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.root_layout);

        // 敵の画像などの表示位置を端末のウィンドウサイズに合わせて変更
        int marginHeight = rootLayout.getHeight() / 4;
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) findViewById(R.id.enemy_image)
                        .getLayoutParams();
        layoutParams.setMargins(0, marginHeight, 0, 0);

        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //エンターキー押下時の処理を無効にする
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
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
                            keyBoardShown();
                        }
                    }).show();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void gameStart() {
        textBox.setVisibility(View.VISIBLE);
        enemyLifeGauge.setVisibility(View.VISIBLE);
        limitTimeBar.setVisibility(View.VISIBLE);
        userInputText.requestFocus();
        limitTimeSurfaceView.startMeasurement();
    }

    public void gameStop() {
        textBox.setVisibility(View.INVISIBLE);
        enemyLifeGauge.setVisibility(View.INVISIBLE);
        limitTimeBar.setVisibility(View.INVISIBLE);
        limitTimeSurfaceView.stopMeasurement();
    }

    public void goToResult(boolean clear) {
        Intent intent = new Intent(this, ResultActivity.class)
                .putExtra(PREF_CLEAR_JUDGE, clear)
                .putExtra(StageSelectActivity.STAGE_ID, stageId)
                .putExtra(PREF_CLEAR_TIME, currentTime)
                .putExtra(PREF_NO_DAMAGE, no_damage)
                .putExtra(PREF_RARE_CRUSHING, rareFrag);
        startActivity(intent);
        finish();
    }

    public void keyBoardShown() {
        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    public void randomStringView() {
        //敵の文字列を表示
        TextView textView = (TextView) findViewById(R.id.enemyString);
        text = EnemyInfo.randomWordView(stageId);
        textView.setText(text);
    }

    public void enemySummon() {
        battleCount++;
        battleCountView.setText(battleCount + " / 5");

/*        //敵キャラ表示
        enemyImage = (ImageView) findViewById(R.id.enemy_image);*/
        //表示と同時に敵キャラのIdを設定

        int enemyId;
        enemyImage.setImageResource(
                EnemyInfo.enemyPath[enemyId = EnemyInfo.randomEnemySummons(EnemyInfo.enemyPath.length)]);

        if (enemyId == 0) {
            rareFrag = true;
        }
        enemyLife = EnemyInfo.enemyLifeSetting(enemyId);
        enemyPow = EnemyInfo.enemyPowSetting(enemyId);

        enemyLifeGauge.setMax(enemyLife);
        enemyLifeGauge.setProgress(enemyLife);
    }

    public void bossSummon() {
        battleCount++;
        battleCountView.setText(battleCount + " / 5");

        ImageView bossImage = (ImageView) findViewById(R.id.enemy_image);

        int bossId = stageId - 1;
        bossImage.setImageResource(EnemyInfo.bossPath[bossId]);
        enemyLife = EnemyInfo.bossLifeSetting(bossId);
        enemyLife = EnemyInfo.bossPowSetting(bossId);

        enemyLifeGauge.setMax(enemyLife);
        enemyLifeGauge.setProgress(enemyLife);
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
        if (s.length() > enemyString.length()) {
            return;
        }

        if (enemyString.getText().toString().substring(0, s.length())
                .equals(s.toString().substring(0, s.length()))) {
            Log.d("judge", String.valueOf(true));
            if (enemyString.getText().length() == s.length()) {
                //全部打ち終わったら文字を切り替える
                userInputText.setText("");
                //プレイヤー側の攻撃処理
                enemyLifeGauge.setProgress(enemyLife -= playerPow);
                //enemyLifeが0以下になったらかつ最大バトル数を上回らなければ新しく生成
                if (enemyLife <= 0) {
                    if (battleCount < maxBattleCount - 1) {
                        enemyCrushingAnimation(enemyImage);
                    } else if (battleCount == maxBattleCount - 1) {
                        enemyCrushingAnimation(enemyImage);
                    } else {
                        gameStop();
                        goToResult(true);
                    }
                }
                randomStringView();
                // リミットタイムをリセットする
                limitTimeSurfaceView.resetLimitTime();
            } else {
                //文字列が一致すれば色を変える
                String txtStr = "<font color=#00ff00>" + text.substring(0, s.length()) +
                        "</font>" + text.substring(s.length(), text.length());
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
        no_damage = false;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                enemyAnimation((ImageView) findViewById(R.id.enemy_image));
            }
        });
        playerLife -= (enemyPow - playerDefence) >= 0 ? enemyPow - playerDefence : 0;
        if (playerLife <= 0) {
            goToResult(false);
        }
        playerLifeGauge.setProgress(playerLife);

    }

    private void enemyCrushingAnimation(final View view) {
        gameStop();

        TranslateAnimation iterateAnimation = new TranslateAnimation(
                -30, 30, 0, 0);
        iterateAnimation.setRepeatMode(Animation.REVERSE);
        iterateAnimation.setRepeatCount(4);
        iterateAnimation.setDuration(250);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.f, 0.f);
        alphaAnimation.setDuration(1000);

        TranslateAnimation moveDownAnimation = new TranslateAnimation(
                0,0, 0, 100);
        moveDownAnimation.setDuration(1000);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(iterateAnimation);
        animationSet.addAnimation(moveDownAnimation);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (battleCount == maxBattleCount - 1) {
                    bossSummon();
                } else {
                    enemySummon();
                }
                readyAnimation(view);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animationSet);
    }

    private void readyAnimation(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.f, 1.f);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 0);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(1000);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gameStart();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animationSet);
    }

    /** implemented OnKeyboardVisibilityListener */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onVisibilityChanged(boolean isVisible) {
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) playerLifeGauge.getLayoutParams();

        if (isVisible) {
            /* キーボード表示時の処理をここに書く */

            //プレイヤーライフの表示位置固定を解除
            layoutParams.removeRule(RelativeLayout.ABOVE);
        } else {
            /* キーボード非表示時の処理をここに書く */

            //プレイヤーライフゲージの表示位置をキーボード表示ボタンの上に固定
            layoutParams.addRule(RelativeLayout.ABOVE, R.id.keyBoardShownButton);
        }
    }

    @Override
    public void onSuggestVisibilityChanged(int marginHeight) {
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) playerLifeGauge.getLayoutParams();
        layoutParams.setMargins(0, marginHeight - playerLifeGauge.getHeight(), 0, 0);
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