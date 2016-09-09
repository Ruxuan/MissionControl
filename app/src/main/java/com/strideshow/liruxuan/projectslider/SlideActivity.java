package com.strideshow.liruxuan.projectslider;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.strideshow.liruxuan.missioncontrolcenter.R;
import com.strideshow.liruxuan.projectsgrid.GridAdapter;
import com.strideshow.liruxuan.stridesocket.StrideSocketIO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ruxuan on 6/27/2016.
 */
public class SlideActivity extends AppCompatActivity {

    ViewPager viewPager;
    SlidePagerAdapter slidePagerAdapter;

    // Slides data
    JSONObject data;

    // Laser pointer
    ImageView laserPointerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get slideshow data
        try {
            data = new JSONObject(getIntent().getExtras().getString(GridAdapter.JSON_DATA));
        } catch (JSONException e) {
            e.printStackTrace();
            data = new JSONObject();
        }

        // Send active project to the server
        int projectIndex = data.optJSONObject("meta_data").optInt("id");
        StrideSocketIO.getInstance().activeProject(projectIndex);

        // Set
        setContentView(R.layout.mission_control_center_activity);

        // Setup viewpager
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        slidePagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), data);
        viewPager.setAdapter(slidePagerAdapter);
        viewPager.addOnPageChangeListener(new SlidePagerPageChangeListener());

        // Image draggable
        //final ViewGroup projectTools = (ViewGroup) findViewById(R.id.projectToolsView);
        laserPointerView = (ImageView) findViewById(R.id.laserView);
        laserPointerView.setOnTouchListener(new LaserOnTouchListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Send active project null to the server
        StrideSocketIO.getInstance().activeProject(-1);
    }

    private boolean isViewOverlapping(View foregroundView, View backgroundView) {
        int [] foregroundPosition  = new int[2];
        int [] backgroundPosition = new int[2];

        foregroundView.getLocationOnScreen(foregroundPosition);
        backgroundView.getLocationOnScreen(backgroundPosition);

        int foregroundY0 = foregroundPosition[1];
        int foregroundY1 = foregroundPosition[1] + foregroundView.getMeasuredHeight();

        int backgroundY0 = backgroundPosition[1];
        int backgroundY1 = backgroundPosition[1] + backgroundView.getMeasuredHeight();

        return (backgroundY0 <= foregroundY1) && (backgroundY1 >= foregroundY0);
    }

    // Handles laser pointer touch listener
    class LaserOnTouchListener implements View.OnTouchListener {
        private StrideSocketIO strideSocketIO = StrideSocketIO.getInstance();
        float dX, dY;

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
    }
}
