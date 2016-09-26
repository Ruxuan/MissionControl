package com.strideshow.liruxuan.projectslider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.strideshow.liruxuan.missioncontrol.R;
import com.strideshow.liruxuan.pocketsphinx.PocketSphinxSpeechRecognizer;
import com.strideshow.liruxuan.projectsgrid.GridAdapter;
import com.strideshow.liruxuan.projectslider.notesviewpager.SlideNotePagerAdapter;
import com.strideshow.liruxuan.projectslider.projecttools.LaserOnTouchListener;
import com.strideshow.liruxuan.projectslider.projectviewpager.SlidePagerAdapter;
import com.strideshow.liruxuan.projectslider.projectviewpager.SlidePagerPageChangeListener;
import com.strideshow.liruxuan.stridesocket.StrideSocketIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ruxuan on 6/27/2016.
 */
public class SlideActivity extends AppCompatActivity {
    // Slides projectData
    JSONObject projectData;

    // Pager
    ViewPager viewPager;

    // Slide Notes View pager
    ViewPager slideNotesViewPager;

    // Voice Command variables ****************************

    // Speech recognition
    PocketSphinxSpeechRecognizer recognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get slideshow projectData
        try {
            // Receive JSON from GridAdapter
            projectData = new JSONObject(getIntent().getExtras().getString(GridAdapter.JSON_DATA));
        } catch (JSONException e) {
            e.printStackTrace();
            projectData = new JSONObject();
        }

        // Send active project to the server
        int projectIndex = projectData.optJSONObject("meta_data").optInt("id");
        StrideSocketIO.getInstance().activeProject(projectIndex);

        // Set
        setContentView(R.layout.mission_control_center_activity);

        // Keep screen on during this activity
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Get viewpagers
        viewPager           = (ViewPager) findViewById(R.id.view_pager);
        slideNotesViewPager = (ViewPager) findViewById(R.id.slideNotesViewPager);

        // Speech recognizer setup
        recognizer = new PocketSphinxSpeechRecognizer(this, viewPager);

        // Main setup
        setupProjectViewPager();
        setupNotesViewPager();
        setupProjectTools();

        // Synchronized view pager scrolling
        // TODO: use fake drag to keep phone vibrate on slidepagerpagechangelistener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int mScrollState = ViewPager.SCROLL_STATE_IDLE;

            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                    return;
                }
                slideNotesViewPager.scrollTo(
                        viewPager.getScrollX(),
                        slideNotesViewPager.getScrollY());
            }

            @Override
            public void onPageSelected(final int position) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {
                mScrollState = state;
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    slideNotesViewPager.setCurrentItem(
                            viewPager.getCurrentItem(), false);
                }
            }
        });

        // TODO: sync or don't sync notes?
        // Sync the two view pagers
        /*slideNotesViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int mScrollState = ViewPager.SCROLL_STATE_IDLE;

            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                    return;
                }
                viewPager.scrollTo(
                        slideNotesViewPager.getScrollX(),
                        viewPager.getScrollY());
            }

            @Override
            public void onPageSelected(final int position) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {
                mScrollState = state;
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    viewPager.setCurrentItem(slideNotesViewPager.getCurrentItem(), false);
                }
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        // Call pocketsphinx speech recognizer . on destroy()
        recognizer.onDestroy();

        // Send active project null to the server
        StrideSocketIO.getInstance().activeProject(-1);

        super.onDestroy();
    }

    private void setupProjectViewPager() {
        // Setup viewpager
        SlidePagerAdapter slidePagerAdapter =
                new SlidePagerAdapter(getSupportFragmentManager(),
                        projectData);
        viewPager.setAdapter(slidePagerAdapter);
        viewPager.addOnPageChangeListener(
                new SlidePagerPageChangeListener(this));
    }

    private void setupProjectTools() {
        //ViewGroup projectTools = (ViewGroup) findViewById(R.id.projectToolsView);

        // Laser pointer
        ImageView laserPointerView = (ImageView) findViewById(R.id.laserView);
        laserPointerView.setOnTouchListener(
                new LaserOnTouchListener(viewPager, laserPointerView));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PocketSphinxSpeechRecognizer.PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "Audio Enabled",
                        Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(this,
                        "Audio Disabled",
                        Toast.LENGTH_LONG)
                        .show();
                finish();
            }
        }
    }

    private void setupNotesViewPager() {
        JSONArray stepsArray = projectData
                .optJSONObject("presentation")
                .optJSONArray("steps");

        // Setup slide notes view pager
        SlideNotePagerAdapter slideNotePagerAdapter =
                new SlideNotePagerAdapter(getSupportFragmentManager(),
                        stepsArray);
        slideNotesViewPager.setAdapter(slideNotePagerAdapter);

        // Add page listener
    }
}
