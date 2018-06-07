package com.infosystem.advertisment.jsf;

import com.infosystem.advertisment.ejb.NotificationService;
import com.infosystem.advertisment.model.Item;
import com.infosystem.advertisment.model.Notification;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named
@ApplicationScoped
public class AdvertismentBean {

    private final static Logger LOGGER = Logger.getLogger(AdvertismentBean.class.toString());

    private List<Item> itemList = new ArrayList<>();

    @Inject
    @Push(channel = "tariffList")
    private PushContext push;

    @PostConstruct
    public void init() {
        LOGGER.info(() -> "Bean init.");
        for (int i = 0; i < 5; i++) {
            itemList.add(new Item("name"+i,"description"+i));
        }
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void onNewNotification(@Observes Notification newNotification) {
        LOGGER.info(() -> "Got bean notification!");
        // updata data
        this.itemList = newNotification.getData();
        LOGGER.info(() -> "Data updated.");
        push.send("update");
    }

}
