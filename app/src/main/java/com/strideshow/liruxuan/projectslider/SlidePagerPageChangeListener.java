package com.strideshow.liruxuan.projectslider;

import android.support.v4.view.ViewPager;
import com.strideshow.liruxuan.stridesocket.StrideSocketIO;

/**
 * Created by Ruxuan on 6/28/2016.
 */
public class SlidePagerPageChangeListener implements ViewPager.OnPageChangeListener {
    // Constant for offset
    private static final float THRESHOLD_OFFSET = 0.5f;

    // Slideshow size
    private int slideShowSize = 3;

    // Slideshow status variables
    private int sliderState   = 0;
    private int slidePosition = 0;

    // Slideshow swipe variables
    // -1 left
    // 1 right
    // 0 standby
    private int newDirection = 0;
    private int newPosition  = 0;

    StrideSocketIO strideSocketIO;

    public SlidePagerPageChangeListener() {
        super();
        this.strideSocketIO = StrideSocketIO.getInstance();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Dragging
        if (sliderState == 1 && positionOffset != 0.0f) {
            if (THRESHOLD_OFFSET > positionOffset && newDirection != 1) {
                newDirection = 1;
                slidePosition++;
                System.out.println("NEXT");
                this.strideSocketIO.next();
            } else if (THRESHOLD_OFFSET < positionOffset && newDirection != -1) {
                newDirection = -1;
                slidePosition--;
                System.out.println("PREV");
                this.strideSocketIO.prev();
            }
        }
        // Settling
        else if (sliderState == 2) {
            // Sync
            while (slidePosition != newPosition) {
                if (slidePosition > newPosition) {
                    slidePosition--;
                    this.strideSocketIO.prev();
                    System.out.println("BACK LEFT");
                } else {
                    slidePosition++;
                    this.strideSocketIO.next();
                    System.out.println("BACK RIGHT");
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
