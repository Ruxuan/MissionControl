package com.strideshow.liruxuan.projectslider.projecttools;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.strideshow.liruxuan.stridesocket.StrideSocketIO;

/**
 * Created by liruxuan on 2016-09-16.
 */

// Handles laser pointer touch listener
public class LaserOnTouchListener implements View.OnTouchListener {
    private StrideSocketIO strideSocketIO = StrideSocketIO.getInstance();

    private ViewPager viewPager;
    private ImageView laserPointerView;

    float dX, dY;

    public LaserOnTouchListener(ViewPager viewPager, ImageView laserPointerView) {
        this.viewPager        = viewPager;
        this.laserPointerView = laserPointerView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - motionEvent.getRawX();
                dY = view.getY() - motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                view.animate()
                        .x(0)
                        .y(0)
                        .setDuration(500)
                        .start();
                strideSocketIO.laserPointer(0,0);
                break;
            case MotionEvent.ACTION_MOVE:
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();

                float posX = rawX + dX;
                float posY = rawY + dY;
                view.animate()
                        .x(posX)
                        .y(posY)
                        .setDuration(0)
                        .start();

                if (isViewOverlapping(laserPointerView, viewPager)) {
                    int [] pos = new int[2];
                    viewPager.getLocationOnScreen(pos);

                    float ratioY = (rawY - pos[1]) / viewPager.getMeasuredHeight();
                    float ratioX = rawX / viewPager.getMeasuredWidth();

                    strideSocketIO.laserPointer(ratioX, ratioY);
                }

                break;
            default:
                return false;
        }
        return true;
    }

    /*
    Checks if two views are overlapping or not
    */
    private boolean isViewOverlapping(View foregroundView, View backgroundView) {
        int [] foregroundPosition = new int[2];
        int [] backgroundPosition = new int[2];

        foregroundView.getLocationOnScreen(foregroundPosition);
        backgroundView.getLocationOnScreen(backgroundPosition);

        int foregroundY0 = foregroundPosition[1];
        int foregroundY1 = foregroundPosition[1] + foregroundView.getMeasuredHeight();

        int backgroundY0 = backgroundPosition[1];
        int backgroundY1 = backgroundPosition[1] + backgroundView.getMeasuredHeight();

        return (backgroundY0 <= foregroundY1) && (backgroundY1 >= foregroundY0);
    }
}