package com.prodato.norma.demo.tomcat.servlet.filter;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.CharSequenceReader;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;


public class CsvToTableFilter implements Filter {

    private FilterConfig filterConfig;

    /**
     * Überschreibt eine Default-Methode des Interfaces, die dort leer ist.
     * Wird vom ServletContext aufgerufen.
     * Elegante Möglichkeit, Initialisierungen vorzunehmen.
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;

        // Kontextpfad muss abgeschnitten werden, damit man dynamisch an den Pfad kommt, der mit dem ServletContext gelesen werden kann.
        // d.h. es muss z.B. /TomcatDemo/ von /TomcatDemo/subtests/filter/Demo.csv abgezogen werden
        final String filePath = req.getRequestURI().substring(req.getContextPath().length() + 1);
        final InputStream csvFileStream = filterConfig.getServletContext().getResourceAsStream(filePath);
        // Funktioniert ab Java 9
        final String csvFileContent = new String(csvFileStream.readAllBytes(), request.getCharacterEncoding());

        // Wandle Csv in Html um
        final StringBuilder sbuf = new StringBuilder();
        sbuf.append("<table>");
        final CSVParser csvParser = CSVFormat.EXCEL.parse(new CharSequenceReader(csvFileContent));
        for (final CSVRecord line : csvParser) {
            sbuf.append("<tr>");
            line.forEach(e -> sbuf.append("<td>" + e + "</td>"));
            sbuf.append("</tr>");
        }
        sbuf.append("</table>");

        // Reiche Ergebnis weiter an das Servlet
        request.setAttribute("csvFileContent", csvFileContent);
        request.setAttribute("csvFileContentAsTable", sbuf.toString());

        // Hier findet die weitere Bearbeitung im Servlet (oder irgendeinem anderen Filter) statt.
        chain.doFilter(request, response);

    }

}
