package com.strideshow.liruxuan.toolbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.strideshow.liruxuan.missioncontrol.R;
import com.strideshow.liruxuan.stridesocket.StrideSocketIO;

/**
 * Created by liruxuan on 2016-08-18.
 */
public class SocketMenu extends Fragment {

    EditText socketRoomKeyEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.socket_menu_fragment, container, false);
        view.setClickable(false);

        Button socketRoomKeyButton = (Button) view.findViewById(R.id.socketRoomButton);
        socketRoomKeyEditText = (EditText) view.findViewById(R.id.socketRoomEditText);

        socketRoomKeyButton.setOnClickListener(new SocketRoomKeyButtonListener());

        return view;
    }

    class SocketRoomKeyButtonListener implements View.OnClickListener {

        StrideSocketIO strideSocketIO = StrideSocketIO.getInstance();

        @Override
        public void onClick(View v) {
            String roomKey = socketRoomKeyEditText.getText().toString();

            strideSocketIO.requestRoom(roomKey);
        }
    }
}
