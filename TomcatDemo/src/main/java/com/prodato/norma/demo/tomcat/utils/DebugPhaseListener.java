package com.prodato.norma.demo.tomcat.utils;

import java.util.HashSet;
import java.util.Set;

import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

import org.apache.logging.log4j.Level;

public class DebugPhaseListener implements PhaseListener {


    private static final long serialVersionUID = 1L;

    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    private static final String LOG_PREFIX_BEFORE_PHASE = "beforePhase ";
    private static final String LOG_PREFIX_AFTER_PHASE = "afterPhase  ";

    private static final Level LOGGING_LEVEL = Level.DEBUG;
    private static final Set<PhaseId> IGNORE_PHASES;

    static {
        IGNORE_PHASES = new HashSet<>();
        IGNORE_PHASES.add(PhaseId.RESTORE_VIEW);
        IGNORE_PHASES.add(PhaseId.RENDER_RESPONSE);
    }

    public DebugPhaseListener() {
    }

    @Override
    public void beforePhase(final PhaseEvent event) {
        if (logger.isEnabled(LOGGING_LEVEL)) {
            logInfo(LOG_PREFIX_BEFORE_PHASE, event);
        }
    }

    @Override
    public void afterPhase(final PhaseEvent event) {
        if (logger.isEnabled(LOGGING_LEVEL)) {
            logInfo(LOG_PREFIX_AFTER_PHASE, event);
        }
    }

    private void logInfo(final String prefix, final PhaseEvent event) {
        final PhaseId phaseId = event.getPhaseId();
        // Nicht alle Phasen debuggen
        if (IGNORE_PHASES.contains(phaseId)) {
            return;
        }
        String viewId;
        try {
            viewId = " " + event.getFacesContext().getViewRoot().getViewId();
        } catch (final NullPointerException e) {
            viewId = "";
        }
        logger.log(LOGGING_LEVEL, prefix + phaseId + viewId);
    }

    @Override
    public PhaseId getPhaseId() {
        // PhaseListener soll für jede Phase aufgerufen werden.
        return PhaseId.ANY_PHASE;
    }

}
