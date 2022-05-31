package kr.kjca.project_greentopia;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class RepeatListener implements View.OnTouchListener {

    private Handler handler = new Handler();
    private int initialInterval;
    private final int normalInterval;
    private final OnClickListener clickListener;
    private Runnable handlerRunnable = new
            Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, normalInterval);
                    clickListener.onClick(downView);
                }
            };
    private View downView;

    /**
     * @param initialInterval The interval after first click event
     * @param  normalInterval The interval after second and subsequent click events
     */
    public RepeatListener(int initialInterval, int normalInterval, OnClickListener clickListener) {
        if (clickListener == null) throw new
                IllegalArgumentException("null runnable");
        if (initialInterval < 0 || normalInterval < 0) throw new IllegalArgumentException("negative interval");
        this.initialInterval =
                initialInterval;
        this.normalInterval = normalInterval;
        this.clickListener = clickListener;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacks(handlerRunnable);
                handler.postDelayed(handlerRunnable, initialInterval);
                downView = view;
                clickListener.onClick(view);
                break;
            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL:
                handler.removeCallbacks(handlerRunnable);
                downView = null;
                break;
        }
        return false;
    }
}
