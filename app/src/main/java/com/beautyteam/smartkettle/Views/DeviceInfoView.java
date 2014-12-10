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
    Boolean mIsScrolling = false;
    String TAG = "CUSTOM";
    private int widthOfScreen;

    View secondChild;

    private OverScroller scroller;
    private GestureDetector  gestureDetector;

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
        /*
        ViewConfiguration vc = ViewConfiguration.get(this.getContext());
        int mTouchSlop = vc.getScaledTouchSlop();

        final int action = MotionEventCompat.getActionMasked(event);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Release the scroll.
            mIsScrolling = false;
            return false; // Do not intercept touch event, let the child handle it
        }*/
        /*
        if (action == MotionEvent.ACTION_DOWN) {
            last_x = event.getX();
            last_y = event.getY();
            return false;
        }

        if (action == event.ACTION_MOVE) {
            if (mIsScrolling) {
                return true;
            }

            final float xDiff = calculateDistanceX(event);

            if (xDiff > mTouchSlop) {
                mIsScrolling = true;
                return true;
            }

        }*/
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        /*
        Boolean enableScroll;
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            down_x = (int) e.getX();
            down_y = (int) e.getY();
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final Scroller scroller = new Scroller(getContext());

                //scroller.fling(deltaXPast, 0, 5, 0, 0, 100, 0, 1);
                scroller.startScroll(0,0, 3,3);
                for (int i=0; i<100; i++)
                    Log.d("scroll", "x=" + scroller.getCurrX());
            }
        });

        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.abs(down_x - (int) e.getX()) > Math.abs(down_y - (int) e.getY())) {
                Log.d("QWERTY", "e.getX=" + (e.getX()) + "  delta=" + deltaXPast);
                this.getChildAt(1).offsetLeftAndRight((int) (e.getX() - deltaXPast));
                deltaXPast = (int) e.getX();
            }
        }
        return true;
        */
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
        scroller.fling(offset, 0, -(int)velocityX/2, 0, 0, -widthOfScreen/2, 0, 0, 50, 0);
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
        } else if (offset < -widthOfScreen/2) {
            offset = -widthOfScreen/2;
        }
    }
}