package com.prodato.norma.demo.tomcat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ShowStatusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ShowStatusServlet() {
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        final PrintWriter writer = response.getWriter();
        writer.append("<html>");
        writer.append("<head><title>Statusanzeige eines Standardservlets</title></head>");
        writer.append("<body>");

        ShowStatusServlet.printStrings(writer, "Context", getContextInformation());
        ShowStatusServlet.printStrings(writer, "Request", getRequestInformation(request));

        writer.append("</body>");
        writer.append("</html>");
    }

    private List<Pair<String, String>> getContextInformation() {
        final ServletContext context = getServletContext();
        final String contextPath = context.getContextPath();
        final int effectiveMajorVersion = context.getEffectiveMajorVersion();
        final int effectiveMinorVersion = context.getEffectiveMinorVersion();
        final int majorVersion = context.getMajorVersion();
        final int minorVersion = context.getMinorVersion();
        final String responseCharacterEncoding = context.getResponseCharacterEncoding();
        final String serverInfo = context.getServerInfo();
        final String servletContextName = context.getServletContextName();
        final int sessionTimeout = context.getSessionTimeout();
        final String virtualServerName = context.getVirtualServerName();

        final List<Pair<String, String>> list = new ArrayList<>();
        list.add(Pair.with("contextPath", contextPath));
        list.add(Pair.with("effectiveVersion", majorVersion + "." + minorVersion));
        list.add(Pair.with("version", effectiveMajorVersion + "." + effectiveMinorVersion));
        list.add(Pair.with("responseCharacterEncoding", responseCharacterEncoding));
        list.add(Pair.with("serverInfo", serverInfo));
        list.add(Pair.with("servletContextName", servletContextName));
        list.add(Pair.with("sessionTimeout", String.valueOf(sessionTimeout)));
        list.add(Pair.with("virtualServerName", virtualServerName));

        return list;
    }

    private List<Pair<String, String>> getRequestInformation(final HttpServletRequest request) {
        final String authType = request.getAuthType();
        final String characterEncoding = request.getCharacterEncoding();
        final String contentLength = String.valueOf(request.getContentLength());
        final String contentType = request.getContentType();
        final String contextPath = request.getContextPath();
        final String localAddr = request.getLocalAddr();
        final String string = request.getLocale().toString();
        final String localName = request.getLocalName();
        final String localPort = String.valueOf(request.getLocalPort());
        final String method = request.getMethod();
        final String pathInfo = request.getPathInfo();
        final String pathTranslated = request.getPathTranslated();
        final String protocol = request.getProtocol();
        final String queryString = request.getQueryString();
        final String remoteAddr = request.getRemoteAddr();
        final String remoteHost = request.getRemoteHost();
        final String remotePort = String.valueOf(request.getRemotePort());
        final String remoteUser = request.getRemoteUser();
        final String requestedSessionId = request.getRequestedSessionId();
        final String requestURI = request.getRequestURI();
        final String requestURL = request.getRequestURL().toString();
        final String scheme = request.getScheme();
        final String serverName = request.getServerName();
        final String serverPort = String.valueOf(request.getServerPort());
        final String servletPath = request.getServletPath();

        final List<Pair<String, String>> list = new ArrayList<>();
        list.add(Pair.with("authType", authType));
        list.add(Pair.with("characterEncoding", characterEncoding));
        list.add(Pair.with("contentLength", contentLength));
        list.add(Pair.with("contentType", contentType));
        list.add(Pair.with("contextPath", contextPath));
        list.add(Pair.with("localAddr", localAddr));
        list.add(Pair.with("string", string));
        list.add(Pair.with("localName", localName));
        list.add(Pair.with("localPort", localPort));
        list.add(Pair.with("method", method));
        list.add(Pair.with("pathInfo", pathInfo));
        list.add(Pair.with("pathTranslated", pathTranslated));
        list.add(Pair.with("protocol", protocol));
        list.add(Pair.with("queryString", queryString));
        list.add(Pair.with("remoteAddr", remoteAddr));
        list.add(Pair.with("remoteHost", remoteHost));
        list.add(Pair.with("remotePort", remotePort));
        list.add(Pair.with("remoteUser", remoteUser));
        list.add(Pair.with("requestedSessionId", requestedSessionId));
        list.add(Pair.with("requestURI", requestURI));
        list.add(Pair.with("requestURL", requestURL));
        list.add(Pair.with("scheme", scheme));
        list.add(Pair.with("serverName", serverName));
        list.add(Pair.with("serverPort", serverPort));
        list.add(Pair.with("servletPath", servletPath));

        return list;
    }

    private static void printStrings(final PrintWriter writer, final String header, final List<Pair<String, String>> strings) {
        writer.append("<h2>").append(header).append("</h2>");
        strings.stream()
                .forEach(e -> {
                    writer.append("<p><b>").append(e.getValue0()).append("</b>: ").append(e.getValue1()).append("</p>");
                });
    }

}
