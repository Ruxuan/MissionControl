package com.strideshow.liruxuan.splashscreen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.strideshow.liruxuan.ApiClient.ApiInterface;
import com.strideshow.liruxuan.ApiClient.ApiClient;
import com.strideshow.liruxuan.missioncontrolcenter.LoginActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Ruxuan on 8/9/2016.
 */
// TODO: add progress bar
public class SplashScreen extends AppCompatActivity {

    /*
    Minimum time that splash screen would be displayed
     */
    private static final long SPLASH_DURATION = 1000;

    /*
    Boolean variables used to show splash screen for a minimum amount of time
     */
    private boolean isTaskRunning = false;
    private boolean isTimerFinished = false;

    /*
    Data from server
     */
    private HashMap<String,String> appData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Timer setup
        Timer timer = new Timer();

        // Startup execute
        new AppStartupTask().execute();

        // Display splash screen for minimum of 1 second
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isTimerFinished = true;
                if (!isTaskRunning) {
                    startMainActivity();
                }
            }
        }, SPLASH_DURATION);
    }

    private void startMainActivity() {
        // TODO: if logged in, redirect to user activity

        Intent intent = new Intent(this, LoginActivity.class);
        // Pass server data
        //intent.putExtra("appData", appData);
        // start Main activity
        startActivity(intent);
    }

    /*
    Async task to:
    - Check if user is already logged in
    - Get initial data from server
     */
    class AppStartupTask extends AsyncTask<Void, Integer, HashMap<String, String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isTaskRunning = true;
        }

        @Override
        protected HashMap doInBackground(Void... params) {
            HashMap<String, String> data = new HashMap<>();

            return data;
        }

        /*@Override
        protected void onProgressUpdate(Integer... progress) {

        }*/

        @Override
        protected void onPostExecute(HashMap<String, String> result) {
            super.onPostExecute(result);

            appData = result;

            isTaskRunning = false;
            if (isTimerFinished) {
                startMainActivity();
            }
        }
    }
}
