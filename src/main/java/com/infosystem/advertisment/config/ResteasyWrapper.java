package com.infosystem.advertisment.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ejb.Singleton;

@Singleton
public class ResteasyWrapper {
    public static final String BASIC_URI = "http://localhost:8083/adv";

    public static final ResteasyClient RESTEASY_CLIENT = new ResteasyClientBuilder().build();
}
