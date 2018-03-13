package com.tlcn.mvpapplication.interactor.socketIO;

import android.util.Log;

import com.google.gson.Gson;
import com.tlcn.mvpapplication.BuildConfig;
import com.tlcn.mvpapplication.interactor.event_bus.EventManager;
import com.tlcn.mvpapplication.interactor.event_bus.type.MessageEvent;
import com.tlcn.mvpapplication.utils.KeyUtils;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by ducthinh on 07/02/2018.
 */

public class SocketManager {
    private Socket mSocket;
    private EventManager mEvent;

    public SocketManager(EventManager mEvent) {
        this.mEvent = mEvent;
    }

    public void disconnectSocket() {
        if (mSocket != null) {
            mSocket.disconnect();
        }
    }

    public void connectSocket() {
        if (mSocket == null) {
            IO.Options options = new IO.Options();
            options.reconnection = true;
            options.forceNew = true;
            try {
                mSocket = IO.socket(BuildConfig.SOCKET_URL, options);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    mEvent.sendEvent(new MessageEvent("ConnectSuccess"));
                    Log.d("Result", "ConnectSuccess");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    mEvent.sendEvent(new MessageEvent("Disconnect"));
                    Log.d("Result", "Disconnect");
                }
            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    mEvent.sendEvent(new MessageEvent("ConnectError"));
                    Log.d("Result", "ConnectError");
                }
            }).on(KeyUtils.KEY_SOCKET_LOCATIONS, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject jsonObject = (JSONObject) args[0];
                    Log.d("Result", new Gson().toJson(jsonObject));
                }
            });
            mSocket.connect();
        }
    }
}
