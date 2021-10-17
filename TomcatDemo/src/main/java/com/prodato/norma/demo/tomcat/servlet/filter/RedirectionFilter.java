package com.prodato.norma.demo.tomcat.servlet.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;


public class RedirectionFilter implements Filter {

    public RedirectionFilter() {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse res = (HttpServletResponse) response;

        // Exceptions werden hier abgefangen
        try {
            chain.doFilter(request, response);
        } catch (final Exception e) {
            System.out.println("Exception gefangen!");
            e.printStackTrace();
            res.sendRedirect("/TomcatDemo/error.html");
        }
    }

}
