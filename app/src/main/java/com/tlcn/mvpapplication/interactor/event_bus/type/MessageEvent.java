package com.tlcn.mvpapplication.interactor.event_bus.type;

/**
 * Created by ducthinh on 07/02/2018.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
