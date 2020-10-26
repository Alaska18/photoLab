package com.afshan.android.photolab;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
{
    Context context;
    View childView;
    GestureDetector gestureDetector;
    RecyclerTouchListener(Context context) {
        this.context = context;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e)
    {
        childView = rv.findChildViewUnder(e.getX(), e.getY());
        if ((childView != null) && gestureDetector.onTouchEvent(e))
        {

            childView.setBackgroundColor(context.getResources().getColor(R.color.blue_bright));
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e)
    {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
