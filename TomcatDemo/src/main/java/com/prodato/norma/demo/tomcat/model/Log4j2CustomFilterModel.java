package com.prodato.norma.demo.tomcat.model;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named(value = "log4j2CustonFilterModel")
@SessionScoped
public class Log4j2CustomFilterModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final Logger logger = LogManager.getLogger();

    public Log4j2CustomFilterModel() {
    }

    public String simpleLoggingAction() {
        logger.debug("simpleLoggingAction");
        return "";
    }

    public String regexFilterLoggingAction() {
        logger.debug("regexfiltered (Fehler, wenn das gesehen wird!)");
        logger.debug("regexFilterLoggingAction: Oberhalb darf keine Zeile stehen!");
        return "";
    }

    public String customDenialLoggingAction() {
        logger.debug("custom DENY");
        logger.debug("customDenialLoggingAction: Oberhalb darf keine Zeile stehen!");
        return "";
    }

}
