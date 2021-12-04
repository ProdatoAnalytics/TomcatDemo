package com.prodato.norma.demo.tomcat.model;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 * Einfaches SesionScoped Model um CDI im generellen zu testen.
 * 
 * @author Michael Lehmeier
 *
 */
@Named(value = "cdiSessionBeanModel")
@SessionScoped
public class CdiSessionBeanModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Logger logger = LogManager.getLogger();

    private int someValue;

    public CdiSessionBeanModel() {
        someValue = 0;
    }

    public String plusOneAction() {
        someValue += 1;
        return "";
    }

    public int getSomeValue() {
        return someValue;
    }

}
