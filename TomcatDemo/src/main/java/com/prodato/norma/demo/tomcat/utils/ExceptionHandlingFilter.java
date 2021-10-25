package com.prodato.norma.demo.tomcat.utils;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Sollte in einem Servlet eine Exception geworfen worden sein, wird diese am Ende der Ausgabe hinzugefügt.
 */
public class ExceptionHandlingFilter implements Filter {

    public ExceptionHandlingFilter() {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        // Statt dem eigentlichen Response, werden Ausgaben in den Wrapper geschrieben, der dann ausgelesen werden kann.
        final CharResponseWrapper wrapper = new CharResponseWrapper((HttpServletResponse) response);

        // Statt dem Response wird ein Wrapper weitergegeben
        try {
            chain.doFilter(request, wrapper);
        } catch (final Throwable e) {
            wrapper.getWriter().write(LoggingUtils.buildHtmlStacktrace(e));
            throw e; // Gehört eigentlich in das Book of Shame!
        } finally {
            final String content = wrapper.toString();
            final PrintWriter responseWriter = response.getWriter();

            response.setContentLength(content.length());
            responseWriter.write(content);
        }

    }

}
