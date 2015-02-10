package jp.ac.it_college.std.flickfighter;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class DetectableKeyboard {

    private Activity activity;
    private int beforeHeight;

    public interface OnKeyboardVisibilityListener {
        public void onVisibilityChanged(boolean isVisible);
        public void onSuggestVisibilityChanged(int marginHeight);
    }

    public DetectableKeyboard(Activity activity) {
        this.activity = activity;
        beforeHeight = 0;
    }

    public final void setKeyboardListener(final OnKeyboardVisibilityListener listener) {
        final View activityRootView =
                ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

        activityRootView.getViewTreeObserver().
                addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    private boolean wasOpened;

                    private final Rect rect = new Rect();

                    @Override
                    public void onGlobalLayout() {
                        activityRootView.getWindowVisibleDisplayFrame(rect);

                        Log.d("rect.height", String.valueOf(rect.height()));

                        // 画面の高さとビューの高さを比べる
                        int heightDiff = activityRootView.getRootView().getHeight() - rect.height();

                        boolean isOpen = heightDiff > 100;

                        if (isOpen == wasOpened) {
                            // キーボードの表示状態は変わっていないはずなので何もしない
                            return;
                        }

                        wasOpened = isOpen;
                        listener.onVisibilityChanged(isOpen);
                    }
                });
    }
}