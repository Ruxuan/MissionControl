package com.strideshow.liruxuan.missioncontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set theme to app main theme
        //setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        // Content View
        setContentView(R.layout.login_activity);

        // Setup button
        Button demoButton = (Button) findViewById(R.id.demoButton);
        demoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to Mission control activity
                Intent toDemo = new Intent(view.getContext(), MissionControlActivity.class);
                startActivity(toDemo);
            }
        });

        /*
        TODO: if user logged in then redirect to his/her page
         StrideSocketIO.getInstance().requestRoom(
                        editTextRoom.getText().toString());
         */
    }
}
