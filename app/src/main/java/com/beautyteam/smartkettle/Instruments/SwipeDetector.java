package com.beautyteam.smartkettle.Instruments;

/**
 * Created by Admin on 28.10.2014.
 */

import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.logging.Logger;

public abstract class SwipeDetector implements View.OnTouchListener {

    public static enum Action {
        LR, // Left to Right
        RL, // Right to Left
        TB, // Top to bottom
        BT, // Bottom to Top
        None // when no action was detected
    }

    private static final String logTag = "SwipeDetector";
    private static final int MIN_DISTANCE = 50;
    private float downX, downY, upX, upY;
    private Action mSwipeDetected = Action.None;
    public View view;

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    public abstract void ActionRL();

    public boolean onTouch(View v, MotionEvent event) {
        view = v;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                mSwipeDetected = Action.None;
                return false; // allow other events like Click to be processed
            }
            case MotionEvent.ACTION_MOVE: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // horizontal swipe detection
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {

                        Log.d(logTag, "Swipe Left to Right");
                        mSwipeDetected = Action.LR;

                        return true;
                    }
                    if (deltaX > 0) {
                        Log.d(logTag, "Swipe Right to Left");
                        ActionRL();
                        v.setBackgroundColor(Color.rgb(0,0,0));
                        mSwipeDetected = Action.RL;
                        return true;
                    }
                } else

                    // vertical swipe detection
                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        // top or down
                        if (deltaY < 0) {
                            Log.d(logTag, "Swipe Top to Bottom");
                            mSwipeDetected = Action.TB;
                            return false;
                        }
                        if (deltaY > 0) {
                            Log.d(logTag, "Swipe Bottom to Top");
                            mSwipeDetected = Action.BT;
                            return false;
                        }
                    }
                return true;
            }
        }
        return false;
    }




}
