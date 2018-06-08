package com.infosystem.advertisment.jsf;

import com.infosystem.advertisment.dto.AdvTariffDto;
import com.infosystem.advertisment.ejb.AdvService;
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
public class AdvBean {

    private final static Logger LOGGER = Logger.getLogger(AdvBean.class.toString());

    private List<AdvTariffDto> itemList = new ArrayList<>();

    @Inject
    private AdvService advService;

    @Inject
    @Push(channel = "tariffList")
    private PushContext push;

    @PostConstruct
    public void init() {
        LOGGER.info(() -> "Bean init.");
        // get initial data
        itemList = advService.initialDataLoad();
    }

    public List<AdvTariffDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<AdvTariffDto> itemList) {
        this.itemList = itemList;
    }

    public void onNewNotification(@Observes Notification newNotification) {
        LOGGER.info(() -> "Got bean notification!");
        // updata data
        this.itemList = newNotification.getData();
        LOGGER.info(() -> "Data updated.");
      //  PrimeFaces.current().ajax().update("prr");
//        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":prr");
        push.send("update");
    }

}
