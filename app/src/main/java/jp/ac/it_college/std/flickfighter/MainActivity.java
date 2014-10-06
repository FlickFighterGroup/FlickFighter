package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void buttonClicked(View view){
        Intent intent = new Intent(this, BattleActivity.class);
        startActivity(intent);
    }
}
