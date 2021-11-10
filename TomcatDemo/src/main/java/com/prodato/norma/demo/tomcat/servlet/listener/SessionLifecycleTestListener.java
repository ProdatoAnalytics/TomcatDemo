package com.prodato.norma.demo.tomcat.servlet.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;


/**
 * Protokolliert das Erstellen und Vernichten von Sessions sowie deren Attribute.
 */
public class SessionLifecycleTestListener implements HttpSessionListener, HttpSessionAttributeListener {

    private static final Logger logger = LogManager.getLogger();

    public SessionLifecycleTestListener() {
    }

    @Override
    public void sessionCreated(final HttpSessionEvent se) {
        logger.debug("Session created with id " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent se) {
        logger.debug("Session destroyed with id " + se.getSession().getId());
    }

    @Override
    public void attributeAdded(final HttpSessionBindingEvent se) {
        logger.debug("Session Attribute added " + se.getName() + ": " + SessionLifecycleTestListener.getValue(se));
    }

    @Override
    public void attributeRemoved(final HttpSessionBindingEvent se) {
        logger.debug("Session Attribute removed " + se.getName() + ": " + SessionLifecycleTestListener.getValue(se));
    }

    @Override
    public void attributeReplaced(final HttpSessionBindingEvent se) {
        logger.debug("Session Attribute replaced " + se.getName() + ": " + SessionLifecycleTestListener.getValue(se));
    }

    private static String getValue(final HttpSessionBindingEvent se) {
        final Object object = se.getValue();
        String value;
        if (object == null) {
            value = "--";
        } else if (object instanceof String) {
            value = (String) object;
        } else {
            value = object.getClass().getCanonicalName();
        }
        return value;
    }

}
