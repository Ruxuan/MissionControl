package com.strideshow.liruxuan.stridesocket;

import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
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

    private String serverAddr = "http://strideshow.me/mobile";

    // Socket IO socket object
    private Socket socket = null;

    // StrideShow network information
    // String inputted roomKey
    public String roomKey = null;

    // String current active project
    public int activeProject = -1;

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
        try {
            socket = IO.socket(serverAddr);

            socket.connect();
            this.setSocketListeners();

        } catch (URISyntaxException e) {
            socket = null;
            e.printStackTrace();
        }
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

            // Mobile Device Info
            JSONObject deviceInfo = new JSONObject();
            deviceInfo.put("model", Build.MODEL);
            deviceInfo.put("sdk", Build.VERSION.SDK_INT);
            deviceInfo.put("os", "Android");

            // Get name of android sdk version E.g. Android Nougat
            Field[] fields = Build.VERSION_CODES.class.getFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                int fieldValue = -1;

                try {
                    fieldValue = field.getInt(new Object());
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                if (fieldValue == Build.VERSION.SDK_INT) {
                    // Over write previous input
                    deviceInfo.put("os", "Android " + fieldName);
                }
            }

            // Send it with JSON
            j.put("mobileDeviceInfo", deviceInfo);
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
    Sends active project to client
     */
    public void activeProject(int projectIndex) {
        if (roomKey == null || socket == null) return;

        this.activeProject = projectIndex;

        JSONObject j = new JSONObject();
        try {
            if (projectIndex == -1) {
                j.put("mobileActiveProject", null);
            } else {
                j.put("mobileActiveProject", projectIndex);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("mobileActiveProject", j);
    }



    /*
    Send next command for ImpressJS
     */
    public void next() {
        if (roomKey == null  || socket == null) return;
        // TODO: use goto
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
        if (roomKey == null  || socket == null) return;
        // TODO: use goto
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
        if (roomKey == null  || socket == null) return;

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
        if (roomKey == null || socket == null) return;

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
