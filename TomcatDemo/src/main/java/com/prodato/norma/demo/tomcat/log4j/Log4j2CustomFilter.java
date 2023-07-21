package com.prodato.norma.demo.tomcat.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;


@Plugin(name = Log4j2CustomFilter.FILTER_NAME, category = Node.CATEGORY, elementType = Filter.ELEMENT_TYPE, printObject = true)
public class Log4j2CustomFilter extends AbstractFilter {

    static final String FILTER_NAME = "Log4j2CustomFilter";

    public static final Logger logger = LogManager.getLogger();

    private Log4j2CustomFilter() {
        super();
        logger.info("Log4j2CustomFilter Konstruktor");
    }

    @Override
    public Result filter(final LogEvent event) {
        logger.debug("Log4j2CustomFilter Filter wurde aufgerufen!");
        final Result returnValue = super.filter(event);
        String message = event.getMessage().getFormattedMessage();
        logger.debug("Message: " + message);
        if ("custom DENY".equals(message)) {
            logger.debug("Sending Deny");
            return Result.DENY;
        }
        return returnValue;
    }

    @PluginFactory
    public static Log4j2CustomFilter createFilter() {
        logger.info("Baue Log4j2CustomFilter");
        return new Log4j2CustomFilter();
    }
}
