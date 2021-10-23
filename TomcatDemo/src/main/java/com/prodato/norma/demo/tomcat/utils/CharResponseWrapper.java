package com.prodato.norma.demo.tomcat.utils;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;


/**
 * Wrapper: Ermöglicht das Abfangen eines Response.
 */
public class CharResponseWrapper extends HttpServletResponseWrapper {
    private final CharArrayWriter writer;

    public CharResponseWrapper(final HttpServletResponse response) {
        super(response);
        writer = new CharArrayWriter();
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(writer);
    }

    @Override
    public String toString() {
        return writer.toString();
    }
}
