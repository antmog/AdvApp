package com.infosystem.advertisment.ejb;

import com.infosystem.advertisment.jms.NewsListener;
import com.infosystem.advertisment.model.Item;
import com.infosystem.advertisment.model.Notification;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class NotificationService {

    private final static Logger LOGGER = Logger.getLogger(NotificationService.class.toString());

    @Inject
    private BeanManager beanManager;

    public void updateData(List<Item> notificationData){
        Notification newNotification = new Notification();
        newNotification.setData(notificationData);
        LOGGER.info(() -> "New notification data is set. Firing notification!");
        beanManager.fireEvent(newNotification);
    }

}