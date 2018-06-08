package com.infosystem.advertisment.jms;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosystem.advertisment.dto.AdvTariffDto;
import com.infosystem.advertisment.ejb.AdvService;

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
    private AdvService advService;

    @Override
    public void onMessage(Message msg) {
        LOGGER.info(() -> "initial Received message: " + msg);
        LOGGER.info(() -> "initial Received messsage class: " + msg.getClass());

        if (msg instanceof TextMessage) {
            LOGGER.info(() -> "textMessage Received: " + "initial");
            try {
                final String text = ((TextMessage) msg).getText();
                LOGGER.info(() -> "textMessage Received: " + text);

                AdvTariffDto advTariffDto = jacksonMapper.readValue(text, AdvTariffDto.class);
                List<AdvTariffDto> notificationData = new ArrayList<>();
                LOGGER.info(() -> "after Jackson: " + advTariffDto.getName());
                LOGGER.info(() -> "after Jackson: " + advTariffDto.getDescription());
                for (int i = 0; i < 5; i++) {
                    notificationData.add(advTariffDto);
                }
                advService.updateData(notificationData);
            } catch (final JMSException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
