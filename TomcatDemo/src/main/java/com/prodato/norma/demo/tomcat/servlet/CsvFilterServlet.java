package com.prodato.norma.demo.tomcat.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CsvFilterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CsvFilterServlet() {
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        final String csvFileContent = (String) request.getAttribute("csvFileContent");
        final String csvFileContentAsTable = (String) request.getAttribute("csvFileContentAsTable");

        final PrintWriter writer = response.getWriter();
        if (csvFileContent != null) {
            writer.append("<html>");
            writer.append("<head><title>CsvFilterServlet mit CSV</title></head>");
            writer.append("<body>");
            writer.append("<h1>CSV Filter Test</h1>");

            writer.append("<textarea rows=\"8\" cols=\"50\">");
            writer.append(csvFileContent);
            writer.append("</textarea>");

            writer.append(csvFileContentAsTable);

            writer.append("</body>");
            writer.append("</html>");
        } else {
            // Kontextpfad muss abgeschnitten werden, damit man dynamisch an den Pfad kommt, der mit dem ServletContext gelesen werden kann.
            // d.h. es muss z.B. /TomcatDemo/ von /TomcatDemo/subtests/filter/Demo.csv abgezogen werden
            final String filePath = request.getRequestURI().substring(request.getContextPath().length() + 1);
            final InputStream fileStream = getServletContext().getResourceAsStream(filePath);
            // Funktioniert ab Java 9
            final String fileContent = new String(fileStream.readAllBytes(), request.getCharacterEncoding());
            writer.append(fileContent);
        }
    }

}
