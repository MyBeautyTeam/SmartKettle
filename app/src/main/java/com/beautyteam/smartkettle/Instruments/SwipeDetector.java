package com.beautyteam.smartkettle.Instruments;

/**
 * Created by Admin on 28.10.2014.
 */

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class SwipeDetector implements View.OnTouchListener {

    public Boolean getIsHandled() {
        return isHandled;
    }

    public void setIsHandled(Boolean isHandled) {
        this.isHandled = isHandled;
    }

    public static enum Action {
        LR, // Left to Right
        RL, // Right to Left
        TB, // Top to bottom
        BT, // Bottom to Top
        None // when no action was detected
    }

    private static final String logTag = "SwipeDetector";
    private static int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private View view;
    private Action mSwipeDetected = Action.None;
    private Boolean isHandled = true;

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public View getView() {
        return view;
    }


    public abstract void actionLR();
    public abstract void actionRL();
    public abstract void actionTB();
    public abstract void actionBT();



    public Action getAction() {
        return mSwipeDetected;
    }

//    public abstract void ActionRL();

    public boolean onTouch(View v, MotionEvent event) {
        this.view = v;
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
                        if (isHandled) {
                            actionLR();
                        }
                        Log.d(logTag, "Swipe Left to Right");
                        mSwipeDetected = Action.LR;

                        return true;
                    }
                    if (deltaX > 0) {
                        Log.d(logTag, "Swipe Right to Left");
                        if (isHandled) {
                            actionRL();
                        }
                        mSwipeDetected = Action.RL;
                        return true;
                    }
                } else

                    // vertical swipe detection
                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        // top or down
                        if (deltaY < 0) {
                            Log.d(logTag, "Swipe Top to Bottom");
                            if (isHandled) {
                                actionTB();
                            }
                            mSwipeDetected = Action.TB;
                            return false;
                        }
                        if (deltaY > 0) {
                            Log.d(logTag, "Swipe Bottom to Top");
                            if (isHandled) {
                                actionBT();
                            }
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
