package com.tlcn.mvpapplication.model;

import java.io.Serializable;

/**
 * Created by ducthinh on 03/10/2017.
 */

public class ObjectSerializable implements Serializable {
    private Object object;

    public ObjectSerializable() {
    }

    public ObjectSerializable(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
