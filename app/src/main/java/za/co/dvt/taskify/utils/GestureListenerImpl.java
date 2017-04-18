package za.co.dvt.taskify.utils;

import android.content.Context;
import android.gesture.Gesture;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by YMalesa on 2017/04/12.
 */

public class GestureListenerImpl extends GestureDetector.SimpleOnGestureListener {
    private View mView;

    public GestureListenerImpl(View view){
        mView = view;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("GestureListenerImpl", "onFling::Swipe detected");
        if(e2.getX() > e1.getX()) {
            Log.d("GestureListenerImpl", "onFling::Left to right swipe, enabling add task layout");
            mView.setVisibility(View.VISIBLE);
        }else if(e2.getX() < e1.getX()){
            Log.d("GestureListenerImpl", "onFling::Right to left swipe, disabling add task layout");
            mView.setVisibility(View.GONE);
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
