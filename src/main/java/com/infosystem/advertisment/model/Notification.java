package com.infosystem.advertisment.model;

import java.util.List;

public class Notification {

    private List<Item> notificationData;

    public void setData(List<Item> notificationData) {
        this.notificationData = notificationData;
    }

    public List<Item> getData() {
        return notificationData;
    }
}
