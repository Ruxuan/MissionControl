package com.strideshow.liruxuan.projectslider.projectviewpager;

import android.content.Context;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import com.strideshow.liruxuan.stridesocket.StrideSocketIO;

/**
 * Created by Ruxuan on 6/28/2016.
 */
public class SlidePagerPageChangeListener implements ViewPager.OnPageChangeListener {
    // Constant for offset
    private static final float THRESHOLD_OFFSET = 0.5f;

    // Slideshow status variables
    private int sliderState   = 0;
    private int slidePosition = 0;

    // Slideshow swipe variables
    // -1 left
    // 1 right
    // 0 standby
    private int newDirection = 0;
    private int newPosition  = 0;

    // Context
    Context context;

    // Vibrator
    Vibrator vibrator;

    // Vibrate length
    long vibrateLen = 50;

    StrideSocketIO strideSocketIO = StrideSocketIO.getInstance();

    public SlidePagerPageChangeListener(Context context) {
        super();
        this.context  = context;
        this.vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Dragging
        if (sliderState == 1 && positionOffset != 0.0f) {
            if (THRESHOLD_OFFSET > positionOffset && newDirection != 1) {
                // Update
                newDirection = 1;
                slidePosition++;

                // Vibrator response
                vibrator.vibrate(vibrateLen);

                // Call stride sockets
                this.strideSocketIO.next();
            } else if (THRESHOLD_OFFSET < positionOffset && newDirection != -1) {
                // Update
                newDirection = -1;
                slidePosition--;

                // Vibrator response
                vibrator.vibrate(vibrateLen);

                // Call stride sockets
                this.strideSocketIO.prev();
            }
        }
        // Settling
        else if (sliderState == 2) {
            // Sync
            while (slidePosition != newPosition) {
                if (slidePosition > newPosition) {
                    slidePosition--;

                    // Vibrator response
                    vibrator.vibrate(vibrateLen);

                    // Stride socket call
                    this.strideSocketIO.prev();
                } else {
                    slidePosition++;

                    // Vibrator response
                    vibrator.vibrate(vibrateLen);

                    // Stride socket call
                    this.strideSocketIO.next();
                }
            }

            // Reset swipe variables
            sliderState  = 0;
            newDirection = 0;
        }
    }

    @Override
    public void onPageSelected(int position) {
        newPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 0 - idle
        // 1 - dragging
        // 2 - settling
        sliderState = state;
    }
}
