package com.prodato.norma.demo.tomcat.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class WelcomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static String[] XML_EXTENSIONS = { "xml" };

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        final String hintsPath = getServletContext().getRealPath("/WEB-INF/hints");
        final String xslPath = getServletContext().getRealPath("/WEB-INF/hints/TipsSchema.xsl");
        final Collection<File> xmlFiles = FileUtils.listFiles(new File(hintsPath), XML_EXTENSIONS, false);
        final File xslFile = new File(xslPath);

        final PrintWriter writer = response.getWriter();
        writer.append("<html>");
        writer.append("<head><title>Tomcat Testprojekt</title></head>");
        writer.append("<body>");

        writer.append("<h1>Tomcat Testprojekt</h1>");

        writer.append("<h2>Testfälle</h2>");
        writer.append("<p>Hier finden sich Servlets und andere Testfälle, die interaktiv aufgerufen werden können</p>");
        writer.append("<p><b><a href=\"menu\">Menu</a></b></p>");

        writer.append("<h2>Tips</h2>");

        writer.append("<p>Es folgt eine Liste von Verweisen, in welchen Klassen und Dateien man Beispielfälle zu unterschiedlichen Technologien finden kann</p>");

        for (final File xmlFile : xmlFiles) {
            writer.append(WelcomeServlet.transform(xmlFile, xslFile));
        }

        writer.append("</body>");
        writer.append("</html>");
    }

    private static String transform(final File xmlFile, final File xslFile) {
        String retString = "";
        final TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();

            transformer = factory.newTransformer(new StreamSource(xslFile));
            final StreamSource xmlsource = new StreamSource(xmlFile);
            final StreamResult output = new StreamResult(bos);
            transformer.transform(xmlsource, output);

            retString = bos.toString("UTF-8");
        } catch (final TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return retString;
    }

}
