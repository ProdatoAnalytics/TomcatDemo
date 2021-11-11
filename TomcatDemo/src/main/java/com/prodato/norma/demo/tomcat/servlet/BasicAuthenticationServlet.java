package com.prodato.norma.demo.tomcat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Enumeration;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Einfaches Servlet für BASIC Authentication.
 * Ursprung des Demos: http://www.avajava.com/tutorials/lessons/how-do-i-use-basic-authentication-with-tomcat.html?page=1
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
public class BasicAuthenticationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();
        out.println("This is the Test Servlet");

        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            out.print("<br/>Header Name: <em>" + headerName);
            final String headerValue = request.getHeader(headerName);
            out.print("</em>, Header Value: <em>" + headerValue);
            out.println("</em>");
        }
        out.println("<hr/>");
        final String authHeader = request.getHeader("authorization");
        final String encodedValue = authHeader.split(" ")[1];
        out.println("Base64-encoded Authorization Value: <em>" + encodedValue);
        String decodedValue = "";
        if (encodedValue != null) {
            decodedValue = new String(Base64.getDecoder().decode(encodedValue));
        }
        out.println("</em><br/>Base64-decoded Authorization Value: <em>" + decodedValue);
        out.println("</em>");
    }

}
