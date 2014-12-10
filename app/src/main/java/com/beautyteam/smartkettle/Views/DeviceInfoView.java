package com.beautyteam.smartkettle.Views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

import com.beautyteam.smartkettle.R;

/**
 * Created by Admin on 10.12.2014.
 */
public class DeviceInfoView extends ViewGroup {
    String TAG = "CUSTOM";
    private int widthOfScreen;

    View secondChild;

    private OverScroller scroller;
    private GestureDetector gestureDetector;

    private int offset = 0;

    public DeviceInfoView(Context context) {
        this(context, null);
    }

    public DeviceInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.AppTheme);
        scroller = new OverScroller(context);

        // this is our custom implementation of the OnGestureListener interface
        GestureListener gestureListener = new GestureListener(this);
        gestureDetector = new GestureDetector(context, gestureListener);
    }

    public DeviceInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        View v = getChildAt(0);
        widthOfScreen = v.getMeasuredWidth();
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v = getChildAt(1);
        secondChild = v;
        v.layout(v.getMeasuredWidth(), 0, 2 * v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            v.measure(widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    public void scroll(int distanceX) {
        scroller.forceFinished(true);
        checkOffset();
        offset -= distanceX;

        this.getChildAt(1).setTranslationX(offset);
        Log.d(TAG, "scroll offset = " + (offset));
    }

    // called when the GestureListener detects fling
    public void fling(int velocityX) {
        scroller.forceFinished(true);
        scroller.fling(offset, 0, -(int)velocityX/2, 0, 0, -2*widthOfScreen/3, 0, 0, 50, 0);
        /*
        public void fling(int startX, int startY, int velocityX, int velocityY,
            int minX, int maxX, int minY, int maxY, int overX, int overY) {
         */
        flingView();
        Log.d(TAG, "fling");
    }

    private void flingView() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (scroller.computeScrollOffset()) {
                    offset = scroller.getCurrX();
                    Log.d(TAG, "fling offset =" + offset);
                    secondChild.setTranslationX(offset);
                    flingView();
                }
            }
        }, 20);

    }

    private void checkOffset() {
        if (offset > 0) {
            offset = 0;
        } else if (offset < -2*widthOfScreen/3) {
            offset = -widthOfScreen/2;
        }
    }
}
