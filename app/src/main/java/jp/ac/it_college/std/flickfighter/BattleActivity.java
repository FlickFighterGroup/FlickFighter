package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class BattleActivity extends Activity implements TextWatcher{
    InputMethodManager inputMethodManager;
    TextView enemyString;
    EditText userInputText;
    String TAG = "BattleActivity";
    String beforeString = "";
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        randomStringView();
        beforeString = "";
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        enemyString = (TextView) findViewById(R.id.enemyString);
        userInputText = (EditText) findViewById(R.id.userInputText);
        userInputText.addTextChangedListener(this);
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
        text = WordBook.randomWordView(getIntent().getExtras().getInt(StageSelectActivity.STAGE_ID));
        textView.setText(text);
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

        if (enemyString.getText().toString().substring(0,s.length()).equals(s.toString())) {
            Log.d("judge", String.valueOf(true));
            if (enemyString.getText().length() == s.length()) {
                //文字を切り替える
                userInputText.setText("");
                randomStringView();
            } else {
                //文字列が一致すれば色を変える

                enemyString = (TextView)findViewById(R.id.enemyString);
                String txtStr = "<font color=#00ff00>" + text.substring(0,s.length()) + "</font>" + text.substring(s.length(), text.length()) ;
                enemyString.setText(Html.fromHtml(txtStr));
            }
        } else {
            //間違っていれば文字列を戻す
            Log.d("judge", String.valueOf(false));
            userInputText.setText(beforeString);
            userInputText.requestFocus();
            userInputText.setSelection(beforeString.length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "afterTextChanged()");
    }
}
