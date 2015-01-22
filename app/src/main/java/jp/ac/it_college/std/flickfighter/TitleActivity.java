package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // タイトル画面のSTARTの点滅
        new Winker(findViewById(R.id.label_start))
                .startWink();
    }

    public void startClicked(View v){
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
        finish();
    }
}
