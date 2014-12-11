package com.beautyteam.smartkettle.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.beautyteam.smartkettle.R;

/**
 * Created by Admin on 10.12.2014.
 */

public class ButtonView extends Button {
    String TAG = "CUSTOM";
    private GestureDetector  gestureDetector;
    OnDoubleClickListener onDoubleClickListener;

    public ButtonView(Context context) {
        this(context, null);
    }

    public ButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.AppTheme);

        // this is our custom implementation of the OnGestureListener interface
    }

    public ButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        DoubleClickGesture gestureListener = new DoubleClickGesture(this);
        gestureDetector = new GestureDetector(context, gestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }


    public void setOnDoubleClickListener(OnDoubleClickListener listener) {
        this.onDoubleClickListener = listener;
    }

    public void doubleClick() {
        if (this.onDoubleClickListener != null) {
            this.onDoubleClickListener.doubleClick();
        }
    }

}
