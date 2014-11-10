package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;



public class BattleActivity extends Activity {
    InputMethodManager inputMethodManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        randomStringView();
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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
}
