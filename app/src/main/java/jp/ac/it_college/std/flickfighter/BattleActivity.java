package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class BattleActivity extends Activity {
    InputMethodManager inputMethodManager;
    TextView enemyString;
    EditText userInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        randomStringView();
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        enemyString = (TextView) findViewById(R.id.enemyString);
        userInputText = (EditText) findViewById(R.id.userInputText);
    }

    public void goToResult(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    public void keyBoardShown(View view) {
        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    public void randomStringView(){
        //敵の文字列を表示
        TextView textView = (TextView) findViewById(R.id.enemyString);
        textView.setText(WordBook.randomWordView(getIntent().getExtras().getInt(StageSelectActivity.STAGE_ID)));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                //Enterキーが押された時に文字列を判定する
                charJudge(enemyString, userInputText);
            }
            return true;
        }

        return super.dispatchKeyEvent(event);
    }

    private void charJudge(TextView enemyChar, EditText userText) {
        if (enemyChar.getText().length() != userText.getText().length()
                || !enemyChar.getText().toString().equals(userText.getText().toString())) {
            //文字の長さと文字が一致しない場合
            Log.v("charJudge", String.valueOf(false));
            userText.setTextColor(Color.RED);
            return;
        }

        //文字が一致した場合
        Log.v("charJudge", String.valueOf(true));
        userText.setTextColor(Color.GREEN);
    }
}
