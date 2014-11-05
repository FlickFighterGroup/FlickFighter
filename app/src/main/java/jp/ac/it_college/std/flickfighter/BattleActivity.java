package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


public class BattleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setContentView(R.layout.activity_battle);
    }

    public void goToResult(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

}
