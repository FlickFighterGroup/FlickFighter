package jp.ac.it_college.std.flickfighter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class DetectableKeyboardEventLayout extends RelativeLayout {
    public interface KeyboardListener {
        void onKeyboardShown();
        void onKeyboardHidden();
    }

    private static final int MIN_KEYBOARD_HEIGHT = 100;
    private KeyboardListener keyboardListener;

    public DetectableKeyboardEventLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetectableKeyboardEventLayout(Context context) {
        super(context);
    }

    public void setKeyboardListener(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int proposedheight = MeasureSpec.getSize(heightMeasureSpec);
        int actualHeight = getHeight();
        final int difference = actualHeight - proposedheight;

        if (keyboardListener == null) {
            return;
        }


        if (difference == 0) {
            return;
        }
        // UI スレッド以外からも onMeasure メソッドが呼ばれるので Handler を使う
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (difference > MIN_KEYBOARD_HEIGHT){
                    keyboardListener.onKeyboardShown();
                } else if (difference < -MIN_KEYBOARD_HEIGHT) {
                    keyboardListener.onKeyboardHidden();
                }
            }
        });
    }
}