package com.tlcn.mvpapplication.interactor.event_bus;

import com.tlcn.mvpapplication.interactor.event_bus.type.Empty;
import com.tlcn.mvpapplication.interactor.event_bus.type.MessageEvent;
import com.tlcn.mvpapplication.interactor.event_bus.type.ObjectEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ducthinh on 07/02/2018.
 */

public class EventManager {
    public void register(Object obj) {
        EventBus.getDefault().register(obj);
    }

    public void unRegister(Object obj) {
        EventBus.getDefault().register(obj);
    }

    public boolean isRegister(Object obj){
        return EventBus.getDefault().isRegistered(obj);
    }

    public void sendEvent(Empty empty) {
        EventBus.getDefault().post(empty);
    }

    public void sendEvent(MessageEvent messageEvent) {
        EventBus.getDefault().post(messageEvent);
    }

    public void sendEvent(ObjectEvent objectEvent) {
        EventBus.getDefault().post(objectEvent);
    }
}
