package com.prodato.norma.demo.tomcat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Einfaches Servlet für FORM Authentication.
 * Im Gegensatz zum Basic-Beispiel wird die Gültigkeit unter reinem HTML mit getestet.
 * Ursprung des Demos: http://www.avajava.com/tutorials/lessons/how-do-i-use-form-authentication-with-tomcat.html
 *
 * tomcat-users.xml muss folgende Zeilen beinhalten:
 *
 * <role rolename="tomcat"/>
 * <user username="tomcat" password="tomcat" roles="tomcat"/>
 * <user username="myname" password="mypassword" roles="tomcat"/>
 * <user username="test" password="test"/>
 *
 * Eine projektspezifische Definition der Rollen ist nicht vorgesehen.
 *
 */
public class FormAuthenticationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        logger.debug("FormAuthenticationServlet mit session " + request.getSession().getId());
        final String logout = request.getParameter("logout");
        try {
            if ("true".equals(logout)) {
                // Ausloggen
                logger.debug("Logge aus");
                request.getSession().invalidate();
                // Über Redirect weiterleiten, damit der Parameter verloren geht und keine Endlosschleif eintritt
                response.sendRedirect(request.getContextPath() + "/FormAuthenticationServlet");
            } else {
                // Weiterleiten auf eine HTML-Seite
                logger.debug("Erfolgreich angemeldet und leite weiter");
                final RequestDispatcher rd = request.getRequestDispatcher("html/webcontent/hello.html");
                rd.forward(request, response);
            }
        } catch (final ServletException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
            response.setContentType("text/html");
            final PrintWriter out = response.getWriter();
            out.println("<p>Ein Fehler ist aufgreten. Logs anschauen!");
        }

    }

}
