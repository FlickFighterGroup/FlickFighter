package jp.ac.it_college.std.flickfighter;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class BattleLayout extends LinearLayout {

    public BattleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_battle, this);
    }

    //▼ ソフトウェアキーボードの表示を検知してレイアウトを変更する ▼
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int newHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int oldHeight = getMeasuredHeight();

        if (oldHeight > newHeight){
            // Keyboard is shown
            findViewById(R.id.keyBoardShownButton).setVisibility(GONE);
        } else {
            // Keyboard is hidden
            findViewById(R.id.keyBoardShownButton).setVisibility(VISIBLE);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
