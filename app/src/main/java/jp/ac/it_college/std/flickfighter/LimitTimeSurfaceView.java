package jp.ac.it_college.std.flickfighter;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LimitTimeSurfaceView
        implements SurfaceHolder.Callback, Runnable {

    private EnemyActionListener listener;
    private SurfaceHolder mHolder;
    private Thread mThread;
    private boolean mIsAlive = false;
    private boolean mMeasurement = false;
    private static final float DEFAULT_LIMIT_TIME_BAR_SIZE = 1.0f;
    private static final float DEFAULT_DECREMENT = 0.005f;
    private float limitTime = DEFAULT_LIMIT_TIME_BAR_SIZE;
    private SurfaceView surfaceView;
    private Handler mHandler;

    public LimitTimeSurfaceView(SurfaceView surfaceView, Handler handler, EnemyActionListener listener) {
        this.surfaceView = surfaceView;
        this.listener = listener;
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
        mHandler = handler;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mThread = new Thread(this);
        mIsAlive = true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsAlive = false;
        while (mThread.isAlive());
    }

    public void startMeasurement (){
        mMeasurement = true;
    }

    public void stopMeasurement() {
        mMeasurement = false;
    }

    public void resetLimitTime() {
        limitTime = DEFAULT_LIMIT_TIME_BAR_SIZE;
    }

    @Override
    public void run() {
        //TODO カウントダウンしてバーを表示する処理
        try {
            while (mIsAlive) {
                if (mMeasurement) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Canvas canvas = mHolder.lockCanvas();
                    drawLimitTime();

                    if (limitTime <= 0) {
                        stopMeasurement();
                        listener.enemyAttack();
                        limitTime = DEFAULT_LIMIT_TIME_BAR_SIZE;
                    }

                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void drawLimitTime() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                surfaceView.setScaleX(limitTime -= DEFAULT_DECREMENT);
            }
        });
    }

    public interface EnemyActionListener {
        //リミットタイムが0になれば呼び出す
        void enemyAttack();
    }
}
