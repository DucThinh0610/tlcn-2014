package com.tlcn.mvpapplication.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ducthinh on 25/10/2017.
 */

public class Notification implements Serializable {
    private String location_id;

    private String title;

    private String messageBody;

    public void initData(Map<String, String> data) {
        if (data.containsKey("location_id")) {
            this.location_id = data.get("location_id");
        }
        if (data.containsKey("title")) {
            this.title = data.get("title");
        }
        if (data.containsKey("messageBody")) {
            this.messageBody = data.get("messageBody");
        }
    }

    public String getLocation_id() {
        return location_id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessageBody() {
        return messageBody;
    }
}
