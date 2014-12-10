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

public class DoubleClickGesture extends GestureDetector.SimpleOnGestureListener {
    private ButtonView buttonView;
    String TAG = "CUSTOM";
    OnDoubleClickListener onDoubleClickListener;

    public DoubleClickGesture(ButtonView buttonView) {
        this.buttonView = buttonView;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "OnDoubleTapEvent CHILD");
        buttonView.doubleClick();
        return false;
    }

}
