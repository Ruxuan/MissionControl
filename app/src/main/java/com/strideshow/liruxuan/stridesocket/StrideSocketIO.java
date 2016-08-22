package com.strideshow.liruxuan.stridesocket;

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

    private String serverAddr = "http://f7a6d21a.ngrok.io/demo";

    private String socketId = null;
    private Socket socket = null;

    public String room = null;

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
                JSONObject j = (JSONObject) args[0];

                try {
                    socketId = j.getString("room");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    Request a room
     */
    public void requestRoom(String room) {
        this.room = room;
        JSONObject j = new JSONObject();

        try {
            j.put("roomKey", room);
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
        if (socketId == null) {
            System.out.println("Socket id doesn't exist yet");
            return;
        }

        JSONObject j = new JSONObject();
        try {
            j.put("room", socketId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("next", j, new Ack() {
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
        if (socketId == null) {
            System.out.println("Socket id doesn't exist yet");
            return;
        }

        JSONObject j = new JSONObject();
        try {
            j.put("room", socketId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("prev", j, new Ack() {
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
        if (socketId == null) {
            System.out.println("Socket id doesn't exist yet");
        }

        JSONObject j = new JSONObject();
        try {
            j.put("room", socketId);
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
}
