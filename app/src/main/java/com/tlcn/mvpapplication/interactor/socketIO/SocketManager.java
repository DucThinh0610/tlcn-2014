package com.tlcn.mvpapplication.interactor.socketIO;

import android.util.Log;

import com.google.gson.Gson;
import com.tlcn.mvpapplication.BuildConfig;
import com.tlcn.mvpapplication.app.AppManager;
import com.tlcn.mvpapplication.interactor.event_bus.EventManager;
import com.tlcn.mvpapplication.interactor.event_bus.type.MessageEvent;
import com.tlcn.mvpapplication.interactor.event_bus.type.ObjectEvent;
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
                mSocket = IO.socket(AppManager.URL_SOCKET, options);
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
            }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("EVENT_ERROR", args[0].toString());
                }
            }).on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("Result", "RECONNECT");
                }
            }).on(KeyUtils.KEY_SOCKET_LOCATIONS, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    mEvent.sendEvent(new ObjectEvent(KeyUtils.KEY_EVENT_LOCATIONS, args));
                }
            }).on(KeyUtils.KEY_SOCKET_NEWS, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    mEvent.sendEvent(new ObjectEvent(KeyUtils.KEY_EVENT_NEWS, args));
                }
            });
            mSocket.connect();
        }
    }
}
