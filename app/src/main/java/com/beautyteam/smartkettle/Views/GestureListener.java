package com.beautyteam.smartkettle.Views;

/**
 * Created by Admin on 10.12.2014.
 */
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Admin on 10.12.2014.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private DeviceInfoView viewGroup;
    String TAG = "CUSTOM";

    public GestureListener(DeviceInfoView viewGroup) {
        this.viewGroup = viewGroup;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll");
        viewGroup.scroll((int) distanceX);
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling");
        viewGroup.fling((int) velocityX);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }

}

