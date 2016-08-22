package com.strideshow.liruxuan.toolbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.strideshow.liruxuan.missioncontrolcenter.R;
import com.strideshow.liruxuan.stridesocket.StrideSocketIO;

/**
 * Created by liruxuan on 2016-08-18.
 */
public class SocketMenu extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.socket_menu, container, false);

        Button socketRoomKeyButton = (Button) view.findViewById(R.id.socketRoomButton);
        final EditText socketRoomKeyEditText = (EditText) view.findViewById(R.id.socketRoomEditText);

        socketRoomKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("socket button clicked");
                String roomKey = socketRoomKeyEditText.getText().toString();
                StrideSocketIO.getInstance().requestRoom(roomKey);
            }
        });

        return view;
    }
}
