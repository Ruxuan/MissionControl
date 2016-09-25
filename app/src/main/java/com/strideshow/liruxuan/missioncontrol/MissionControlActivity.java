package com.strideshow.liruxuan.missioncontrol;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.strideshow.liruxuan.projectsgrid.GridFragment;
import com.strideshow.liruxuan.toolbar.SocketMenu;

/**
 * Created by Ruxuan on 8/10/2016.
 */
public class MissionControlActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    SocketMenu socketMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_control_activity);

        // Toolbar setup
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Open the main fragment for mission control
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Mission control grid container is the main fragment
        GridFragment fragment = new GridFragment();
        fragmentTransaction.add(R.id.missionControlActivityView, fragment);
        fragmentTransaction.commit();
    }

    // Menu Stuff****************************************************

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.socketRoom:

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (socketMenu == null) {
                    socketMenu = new SocketMenu();
                    fragmentTransaction.add(R.id.socketMenuContainerView, socketMenu);
                    fragmentTransaction.commit();
                } else {
                    fragmentTransaction.remove(socketMenu).commit();
                    socketMenu = null;
                }

                return true;
            case R.id.actionSettings:
                Toast.makeText(this, "Action Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem socketRoomItem = menu.findItem(R.id.socketRoom);
        // Add small red/green dot

        /*MenuItem testItem = menu.findItem(R.id.testing);
        Resources r = getResources();
        Drawable[] layers = new Drawable[2];
        layers[0] = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_devices_black_24dp, null);
        layers[1] = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_fiber_manual_record_black_8dp, null);
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        testItem.setIcon(layerDrawable);*/

        return true;
    }
}
