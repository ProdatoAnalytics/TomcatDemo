package com.prodato.norma.demo.tomcat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Testet den Lebenszyklus einer Session und deren Attribute mitzuverfolgen.
 * Debug-Ausgabe im Listener in log4j aktivieren um Events mitverfolgen zu können.
 */
public class SessionLifecycleTestServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private static final long serialVersionUID = 1L;

    private static final String FORM_HTML;

    static {
        final StringBuilder sbuf = new StringBuilder();
        sbuf.append("<form action=\"/TomcatDemo/SessionLifecycleTestServlet\" method=\"post\">");
        sbuf.append("    <label for=\"idSessionVariable\">sessionVariable:</label>");
        sbuf.append("    <input type=\"text\" id=\"idSessionVariable\" name=\"sessionVariable\" value=\"#SESSION#\"/><br><br>");
        sbuf.append("    <label for=\"idRequestVariable\">requestVariable:</label>");
        sbuf.append("    <input type=\"text\" id=\"idRequestVariable\" name=\"requestVariable\"/><br><br>");
        sbuf.append("    <input type=\"submit\" value=\"Submit\"/>");
        sbuf.append("</form> ");
        FORM_HTML = sbuf.toString();
    }

    public SessionLifecycleTestServlet() {
    }

    /**
     * Beim ersten Aufruf kommt man in doGet. Alle späteren Aufrufe durch Submit erfolgen per doPost.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        SessionLifecycleTestServlet.processRequest(request, response, "Test von Sessions (Get)", true);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        SessionLifecycleTestServlet.processRequest(request, response, "Test von Sessions (Post)", false);
    }

    private static void processRequest(final HttpServletRequest request, final HttpServletResponse response, final String title, final boolean isGet) throws IOException {
        response.setContentType("text/html");

        final PrintWriter writer = response.getWriter();
        writer.append("<html>");
        writer.append("<head><title>").append(title).append("</title></head>");
        writer.append("<body>");

        final String sessionVariable = request.getParameter("sessionVariable");
        final String requestVariable = request.getParameter("requestVariable");

        final HttpSession session = request.getSession();

        // Session Variable in Session speichern (nur wenn man die Seite nicht über get neu betritt)
        logger.debug("Set storedSessionVariable: " + sessionVariable);
        if (!isGet) {
            session.setAttribute("storedSessionVariable", sessionVariable);
        }

        final String isNewSession = session.isNew() ? "Ja" : "Nein";
        final String storedSessionVariable;
        final String formString;
        if (session.getAttribute("storedSessionVariable") != null) {
            storedSessionVariable = (String) session.getAttribute("storedSessionVariable");
            // Eingabefeld mit bestehendem Wert belegen
            formString = FORM_HTML.replace("#SESSION#", storedSessionVariable);
        } else {
            storedSessionVariable = "--";
            // Eingabefeld darf leer bleiben
            formString = FORM_HTML.replace("#SESSION#", "");
        }
        writer.append(formString);

        writer.append("<p>Session Id: ").append(session.getId()).append("</p>");

        writer.append("<h2>Ergebnis</h2>");
        writer.append("<p>Session Variable: ").append(sessionVariable).append("</p>");
        // Wenn vorhanden: sollte immer einen Klick hinterherhinken
        // Leer beim ersten Betreten
        // Bleibt erhalten, wenn die Seite verlassen wird
        writer.append("<p>Stored Session Variable: ").append(storedSessionVariable).append("</p>");
        writer.append("<p>Request Variable: ").append(requestVariable).append("</p>");
        // Wenn nicht: Wann wird sie erzeugt?
        writer.append("<p>Session is new: ").append(isNewSession).append("</p>");

        SessionLifecycleTestServlet.writeAllAttributes(writer, session);

        writer.append("</body>");
        writer.append("</html>");
    }

    private static void writeAllAttributes(final PrintWriter writer, final HttpSession session) {
        // Ausgabe aller in der Session gespeicherten Werte
        writer.append("<h2>Alle Parameter</h2>");
        final Enumeration<String> attributeNames = session.getAttributeNames();
        writer.append("<table>");
        writer.append("<tr><th>Attribut:</th><th>Betrag:</th></tr>");
        while (attributeNames.hasMoreElements()) {
            final String attributeName = attributeNames.nextElement();
            final Object attributeValue = session.getAttribute(attributeName);
            String value;
            if (attributeValue == null) {
                value = "--";
            } else if (attributeValue instanceof String) {
                value = (String) attributeValue;
            } else {
                value = attributeValue.getClass().getCanonicalName();
            }
            writer.append("<tr><td>").append(attributeName).append("</td><td>").append(value).append("</td></tr>");
        }
        writer.append("</table>");
    }

}
