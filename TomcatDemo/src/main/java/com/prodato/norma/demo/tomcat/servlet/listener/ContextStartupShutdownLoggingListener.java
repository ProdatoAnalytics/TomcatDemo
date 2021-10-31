package com.prodato.norma.demo.tomcat.servlet.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;


/**
 * ServletContextListener werden beim Start bzw Herunterfahren eines Context aufgerufen.
 *
 */
public class ContextStartupShutdownLoggingListener implements ServletContextListener, ServletContextAttributeListener, ServletRequestListener, ServletRequestAttributeListener {

    private static final Logger LOGGER = LogManager.getLogger();

    /** Leerer Konstruktor ist notwendig */
    public ContextStartupShutdownLoggingListener() {
    }

    /*
     * ServletContextListener
     */

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        LOGGER.debug("ServletContext initialized: " + servletContext.getContextPath());
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        LOGGER.debug("ServletContext destroyed: " + servletContext.getContextPath());
    }

    /*
     * ServletContextAttributeListener
     */

    @Override
    public void attributeAdded(final ServletContextAttributeEvent scae) {
        final Object source = scae.getSource();
        final String name = scae.getName();
        final Object value = scae.getValue();

        LOGGER.debug("ServletContext attribute added - source: " + source.getClass().getSimpleName() + " name: " + name + " value: " + value);
    }

    @Override
    public void attributeRemoved(final ServletContextAttributeEvent scae) {
        final Object source = scae.getSource();
        final String name = scae.getName();
        final Object value = scae.getValue();

        LOGGER.debug("ServletContext attribute removed - source: " + source.getClass().getSimpleName() + " name: " + name + " value: " + value);
    }

    @Override
    public void attributeReplaced(final ServletContextAttributeEvent scae) {
        final Object source = scae.getSource();
        final String name = scae.getName();
        final Object value = scae.getValue();

        LOGGER.debug("ServletContext attribute replaced - source: " + source.getClass().getSimpleName() + " name: " + name + " value: " + value);
    }

    /*
     * ServletRequestListener
     */

    @Override
    public void requestInitialized(final ServletRequestEvent sre) {
        final HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        LOGGER.debug("ServletRequest initialized: " + request.getRequestURI());
    }

    @Override
    public void requestDestroyed(final ServletRequestEvent sre) {
        final HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        LOGGER.debug("ServletRequest destroyed: " + request.getRequestURI());
    }

    /*
     * ServletRequestAttributeListener
     */

    @Override
    public void attributeAdded(final ServletRequestAttributeEvent srae) {
        final Object source = srae.getSource();
        final String name = srae.getName();
        final Object value = srae.getValue();

        LOGGER.debug("ServletRequest attribute added - source: " + source.getClass().getSimpleName() + " name: " + name + " value: " + value);
    }

    @Override
    public void attributeRemoved(final ServletRequestAttributeEvent srae) {
        final Object source = srae.getSource();
        final String name = srae.getName();
        final Object value = srae.getValue();

        LOGGER.debug("ServletRequest attribute removed - source: " + source.getClass().getSimpleName() + " name: " + name + " value: " + value);
    }

    @Override
    public void attributeReplaced(final ServletRequestAttributeEvent srae) {
        final Object source = srae.getSource();
        final String name = srae.getName();
        final Object value = srae.getValue();

        LOGGER.debug("ServletRequest attribute replaced - source: " + source.getClass().getSimpleName() + " name: " + name + " value: " + value);
    }

}
