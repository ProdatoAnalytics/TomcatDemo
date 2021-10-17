package com.prodato.norma.demo.tomcat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class RedirectionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String FORM_HTML;

    static {
        final StringBuilder sbuf = new StringBuilder();
        sbuf.append("<form action=\"/TomcatDemo/RedirectionServlet\" method=\"post\">");
        sbuf.append("    <label for=\"idDividend\">Dividend:</label>");
        sbuf.append("    <input type=\"number\" id=\"idDividend\" name=\"dividend\"><br><br>");
        sbuf.append("    <label for=\"idDivisor\">Divisor:</label>");
        sbuf.append("    <input type=\"number\" id=\"idDivisor\" name=\"divisor\"><br><br>");
        sbuf.append("    <input type=\"submit\" value=\"Submit\">");
        sbuf.append("</form> ");
        FORM_HTML = sbuf.toString();
    }

    public RedirectionServlet() {
    }

    /**
     * Beim ersten Aufruf kommt man in doGet. Alle späteren Aufrufe durch Submit erfolgen per doPost.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        RedirectionServlet.processRequest(request, response, "Weiterleitung im Fehlerfall", false);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        RedirectionServlet.processRequest(request, response, "Weiterleitung im Fehlerfall: Ergebnis", true);
    }

    private static void processRequest(final HttpServletRequest request, final HttpServletResponse response, final String title, final boolean isResponse) throws IOException {
        response.setContentType("text/html");

        final PrintWriter writer = response.getWriter();
        writer.append("<html>");
        writer.append("<head><title>").append(title).append("</title></head>");
        writer.append("<body>");

        writer.append(FORM_HTML);

        if (isResponse) {
            writer.append("<p>Ergebnis</p>");

            final float dividend = Float.parseFloat(request.getParameter("dividend"));
            final float divisor = Float.parseFloat(request.getParameter("divisor"));
            // Löst bei Division durch 0 eine Exception aus!
            final BigDecimal result = new BigDecimal(dividend).divide(new BigDecimal(divisor));
            writer.append("<p>").append(String.valueOf(dividend)).append(" / ").append(String.valueOf(divisor)).append(" = ").append(String.valueOf(result)).append("</p>");
        }

        writer.append("</body>");
        writer.append("</html>");

    }

}
