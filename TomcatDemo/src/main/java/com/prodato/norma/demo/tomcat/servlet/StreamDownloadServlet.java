package com.prodato.norma.demo.tomcat.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class StreamDownloadServlet extends jakarta.servlet.http.HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int MAX_BUFFER_SIZE = 1000;

    public StreamDownloadServlet() {
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/pdf");
        resp.setHeader("Content-disposition", "attachment; filename=\"Hänsel und Gretel.pdf\"");

        try (InputStream in = req.getServletContext().getResourceAsStream("/WEB-INF/lib/HaenselUndGretel.pdf");
                OutputStream out = resp.getOutputStream()) {

            final byte[] buffer = new byte[MAX_BUFFER_SIZE];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
    }

}
