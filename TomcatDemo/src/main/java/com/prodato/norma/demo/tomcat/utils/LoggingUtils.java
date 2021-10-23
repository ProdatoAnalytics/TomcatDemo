package com.prodato.norma.demo.tomcat.utils;

public class LoggingUtils {

    private LoggingUtils() {
    }

    /**
     * Erstellt aus einem Throwable lesbaren HTML-Code.
     */
    public static String buildHtmlStacktrace(final Throwable throwable) {
        final StringBuilder sbuf = new StringBuilder();

        sbuf.append("<p>");
        sbuf.append(throwable.getClass().getCanonicalName()).append(": <span style=\"color:red;font-weight:bold\">").append(throwable.getMessage()).append("</span>").append("<br/>");
        sbuf.append("<ul>");

        for (final StackTraceElement e : throwable.getStackTrace()) {
            sbuf.append("<li>at ").append(e.getClassName()).append(".").append(e.getMethodName()).append("<b>(").append(e.getFileName()).append(":").append(e.getLineNumber()).append(")</b>");
        }

        sbuf.append("</ul>");
        sbuf.append("</p>");

        return sbuf.toString();
    }

}
