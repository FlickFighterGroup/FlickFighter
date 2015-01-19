package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;


public class ResultActivity extends Activity implements ViewSwitcher.ViewFactory {
    private int[] img =  {
            R.drawable.true_img
            ,R.drawable.false_img
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        checkChallenge();
    }

    private void checkChallenge() {
        ImageSwitcher challenge1 = (ImageSwitcher) findViewById(R.id.challenge_clear1);
        challenge1.setFactory(this);
        ImageSwitcher challenge2 = (ImageSwitcher) findViewById(R.id.challenge_clear2);
        challenge2.setFactory((android.widget.ViewSwitcher.ViewFactory) this);
        ImageSwitcher challenge3 = (ImageSwitcher) findViewById(R.id.challenge_clear3);
        challenge3.setFactory((android.widget.ViewSwitcher.ViewFactory) this);

        if (false) {
            challenge1.setImageResource(img[0]);
        } else {
            challenge1.setImageResource(img[1]);
        }

        if (true) {
            challenge2.setImageResource(img[0]);
        } else {
            challenge2.setImageResource(img[1]);
        }

        if (false) {
            challenge3.setImageResource(img[0]);
        } else {
            challenge3.setImageResource(img[1]);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View makeView() {
        // ApiDemos->Views->ImageSwitcherのソースからメソッドを丸々コピー
        ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(
                Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT));
        return i;
    }
}
