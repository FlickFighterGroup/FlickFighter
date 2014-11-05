package jp.ac.it_college.std.flickfighter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class KeyBoardDisplayButton extends Button {
    public KeyBoardDisplayButton(Context context) {
        super(context);
    }

    public KeyBoardDisplayButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager)getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(1, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }
}
