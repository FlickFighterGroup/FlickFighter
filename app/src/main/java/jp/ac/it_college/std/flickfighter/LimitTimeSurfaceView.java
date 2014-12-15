package jp.ac.it_college.std.flickfighter;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LimitTimeSurfaceView
        implements SurfaceHolder.Callback, Runnable{
    private EnemyAttackListener listener;
    private SurfaceHolder mHolder;

    public LimitTimeSurfaceView(SurfaceView surfaceView, EnemyAttackListener listener) {
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
        this.listener = listener;
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void run() {
        //TODO カウントダウンしてバーを表示する処理
    }

    public static interface EnemyAttackListener {
        //リミットタイムが0になれば呼び出す
        void enemyAttack();
    }


}
