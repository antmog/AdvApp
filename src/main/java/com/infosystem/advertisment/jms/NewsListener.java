package com.infosystem.advertisment.jms;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosystem.advertisment.ejb.NotificationService;
import com.infosystem.advertisment.model.Item;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@MessageDriven(name = "MyMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/infoSystem"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class NewsListener implements MessageListener {

    private ObjectMapper jacksonMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final static Logger LOGGER = Logger.getLogger(NewsListener.class.toString());

    @EJB
    private NotificationService notificationService;

    @Override
    public void onMessage(Message msg) {
        LOGGER.info(() -> "initial Received message: " + msg);
        LOGGER.info(() -> "initial Received messsage class: " + msg.getClass());

        if (msg instanceof TextMessage) {
            LOGGER.info(() -> "textMessage Received: " + "initial");
            try {
                final String text = ((TextMessage) msg).getText();
                LOGGER.info(() -> "textMessage Received: " + text);

                Item item = jacksonMapper.readValue(text, Item.class);
                List<Item> notificationData = new ArrayList<>();
                LOGGER.info(() -> "after Jackson: " + item.getItemName());
                LOGGER.info(() -> "after Jackson: " + item.getDescription());
                for (int i = 0; i < 5; i++) {
                    notificationData.add(item);
                }
                notificationService.updateData(notificationData);
            } catch (final JMSException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
