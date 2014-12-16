package jp.ac.it_college.std.flickfighter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LimitTimeSurfaceView
        implements SurfaceHolder.Callback, Runnable {
    private EnemyAttackListener listener;
    private SurfaceHolder mHolder;
    private Thread mThread;
    private boolean mIsAlive = false;
    private boolean mMeasurement = false;
    private float limitTime = 10;
    private Paint mPaint;

    public LimitTimeSurfaceView(SurfaceView surfaceView, EnemyAttackListener listener) {
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
        this.listener = listener;
    }

    public void startMeasurement() {
        mMeasurement = true;
    }

    public void endMeasurement() {
        mMeasurement = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mThread = new Thread(this);
        mIsAlive = true;
        mPaint = new Paint();
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsAlive = false;
        while (mThread.isAlive()) ;
    }

    @Override
    public void run() {
        //TODO カウントダウンしてバーを表示する処理
        try {
            while (mIsAlive) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Canvas canvas = mHolder.lockCanvas();
                mPaint.setColor(Color.WHITE);
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);

                String time = String.format("%.2f", limitTime -= 0.01f);
                mPaint.setColor(Color.BLACK);
                mPaint.setAntiAlias(true);
                mPaint.setTextSize(38);
                Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                float textWidth = mPaint.measureText(time);
                float centerY = canvas.getHeight() / 2f;
                float baseX = canvas.getWidth() - textWidth;
                float baseY = centerY - (fontMetrics.ascent + fontMetrics.descent) / 2;

                canvas.drawText(time, baseX, baseY, mPaint);
                mHolder.unlockCanvasAndPost(canvas);

                if (limitTime <= 0) {
                    listener.enemyAttack();
                    limitTime = 5;
                }
            }
        } catch (NullPointerException e) {
            Log.e("LimitTimeSurfaceView", "Null", e);
        }
    }

    public static interface EnemyAttackListener {
        //リミットタイムが0になれば呼び出す
        void enemyAttack();
    }


}
