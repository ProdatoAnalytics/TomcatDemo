package com.prodato.norma.demo.tomcat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class HelloWorldServlet
 */
public class HelloWorldServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public HelloWorldServlet() {
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // TODO Auto-generated method stub
        final PrintWriter writer = response.getWriter();
        writer.append("<html>");
        writer.append("<head><title>HelloWorldServlet</title></head>");
        writer.append("<body>");
        writer.append("<h1>Hello World Servlet</h1>");
        writer.append("<p>Served at: ").append(request.getContextPath()).append("</p>");
        writer.append("</body>");
        writer.append("</html>");
    }

}
