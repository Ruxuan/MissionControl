package com.strideshow.liruxuan.stridesocket;

import android.support.v7.widget.LinearLayoutCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Ruxuan on 7/3/2016.
 */
public class StrideSocketIO {
    // Singleton
    private static StrideSocketIO instance = null;

    private String serverAddr = "http://ee8f5ec2.ngrok.io/demo";

    private Socket socket = null;

    public String roomKey = null;

    /*
    Singleton getInstance()
     */
    public static StrideSocketIO getInstance() {
        if (instance == null ) {
            instance = new StrideSocketIO();
        }

        return instance;
    }

    /*
    Private Ctor
     */
    private StrideSocketIO() {
        IO.Options opt = new IO.Options();
        opt.query      = "reqRoom=false";

        try {
            socket = IO.socket(serverAddr, opt);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.connect();
        this.setSocketListeners();
    }

    private void setSocketListeners() {
        // on connect
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                System.out.println("SOCKET CONNECTED");
            }
        });

        // on disconnect
        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                System.out.println("SOCKET DISCONNECTED");
            }
        });

        // Listen for room responses
        socket.on("respondRoom", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                System.out.println("successfully connected");
            }
        });
    }

    /*
    Request a room
     */
    public void requestRoom(String roomKey) {
        this.roomKey = roomKey;
        JSONObject j = new JSONObject();

        try {
            j.put("roomKey", roomKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("requestRoom", j, new Ack() {
            @Override
            public void call(Object... args) {
                System.out.println("Server received REQUEST ROOM");
            }
        });
    }


    /*
    Send next command for ImpressJS
     */
    public void next() {
        socket.emit("next", new Ack() {
            @Override
            public void call(Object... args) {
                System.out.println("Server received NEXT command");
            }
        });
    }

    /*
    Send prev command for ImpressJS
     */
    public void prev() {
        socket.emit("prev", new Ack() {
            @Override
            public void call(Object... args) {
                System.out.println("Server received PREV command");
            }
        });
    }

    /*
    Send goto command for ImpressJS

    Notes: I couldn't name it "goto" b/c it's a reserved keyword in Java
     */
    public void impressGoto(int slideIndex) {

        JSONObject j = new JSONObject();
        try {
            j.put("slideIndex", slideIndex);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        socket.emit("goto", j, new Ack() {
            @Override
            public void call(Object... args) {
                System.out.println("Server received GOTO command");
            }
        });
    }

    /*
    Send laser pointer info
     */
    public void laserPointer(float ratioX, float ratioY) {

        JSONObject j = new JSONObject();
        try {
            j.put("ratioX", ratioX);
            j.put("ratioY", ratioY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("laserPointer", j, new Ack() {
            @Override
            public void call(Object... args) {
                System.out.println("Server received laserPointer command");
            }
        });
    }
}
