package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StatusActivity extends Activity{
    private static final String STATUS_ATTACK = "attackpoint";
    private static final String STATUS_DEFENSE = "defensepoint";
    private static final String STATUS_LIFE = "lifepoint";
    private SharedPreferences preferences = getPreferences(MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int Attack = preferences.getInt(STATUS_ATTACK,1);
        int Defense = preferences.getInt(STATUS_DEFENSE,1);
        int Life = preferences.getInt(STATUS_LIFE,3);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Log.v("test", Integer.toString(Attack + Defense + Life));
    }

    public void upGrade(View v){
        Intent intent = new Intent(this, UpGradeActivity.class);
        startActivity(intent);
    }
    public void stageSelect(View v){
        Intent intent = new Intent(this, StageSelectActivity.class);
        startActivity(intent);
    }
}
