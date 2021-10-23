package com.prodato.norma.demo.tomcat.servlet.filter;

import java.io.IOException;
import java.io.PrintWriter;

import com.prodato.norma.demo.tomcat.utils.CharResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;


public class LatexFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        // Statt dem eigentlichen Response, werden Ausgaben in den Wrapper geschrieben, der dann ausgelesen werden kann.
        final CharResponseWrapper wrapper = new CharResponseWrapper((HttpServletResponse) response);

        // Statt dem Response wird ein Wrapper weitergegeben
        chain.doFilter(request, wrapper);

        final String originalContent = wrapper.toString();
        final String alteredContent = originalContent.replace("Latex", "LaTeX");

        final PrintWriter responseWriter = response.getWriter();

        response.setContentLength(alteredContent.length());
        responseWriter.write(alteredContent);
    }

}
