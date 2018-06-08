package com.infosystem.advertisment.model;

import com.infosystem.advertisment.dto.AdvTariffDto;

import java.util.List;

public class Notification {

    private List<AdvTariffDto> notificationData;

    public void setData(List<AdvTariffDto> notificationData) {
        this.notificationData = notificationData;
    }

    public List<AdvTariffDto> getData() {
        return notificationData;
    }
}
