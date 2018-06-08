package com.infosystem.advertisment.ejb;

import com.infosystem.advertisment.config.ResteasyWrapper;
import com.infosystem.advertisment.dto.AdvInitialDataDto;
import com.infosystem.advertisment.dto.AdvTariffDto;
import com.infosystem.advertisment.model.Notification;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Singleton;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Logger;

@Singleton
public class AdvService {

    private final static Logger LOGGER = Logger.getLogger(AdvService.class.toString());

    @Inject
    private BeanManager beanManager;

    public void updateData(List<AdvTariffDto> notificationData){
        Notification newNotification = new Notification();
        newNotification.setData(notificationData);
        LOGGER.info(() -> "New notification data is set. Firing notification!");
        beanManager.fireEvent(newNotification);
    }

    public List<AdvTariffDto> initialDataLoad(){
        return getDataFromRest("/getInitialAdvData");
    }

    private List<AdvTariffDto> getDataFromRest(String endpoint){
        LOGGER.info(() -> "Getting data from rest.");
        ResteasyWebTarget target = ResteasyWrapper.RESTEASY_CLIENT.target(ResteasyWrapper.BASIC_URI + endpoint);
        Response response = target.request().get();
        return response.readEntity(new GenericType<AdvInitialDataDto>() {}).getAdvTariffDtoList();
    }
}