package com.prodato.norma.demo.tomcat.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;

import com.prodato.norma.demo.tomcat.utils.Menu;
import com.prodato.norma.demo.tomcat.utils.Menu.Menuitem;
import com.prodato.norma.demo.tomcat.utils.MenuLoader;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class MenuServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public MenuServlet() {
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        final String menuPath = getServletContext().getRealPath("/WEB-INF/menufiles");
        final MenuLoader menuLoader = new MenuLoader(new File(menuPath));

        final PrintWriter writer = response.getWriter();
        writer.append("<html>");
        writer.append("<head><title>Demo Menu</title></head>");
        writer.append("<body>");

        for (final Menu menu : menuLoader.getMenus()) {
            writer.append("<h1>").append(menu.getTitle()).append("</h1>");
            if (StringUtils.isNotBlank(menu.getDescription())) {
                writer.append("<p>").append(menu.getDescription()).append("</p>");
            }
            writer.append("<ul>");
            for (final Menuitem menuEntry : menu.getMenuitem()) {
                writer.append("<li><a href=\"").append(menuEntry.getUrl()).append("\">").append(menuEntry.getValue()).append("</a></li>");
            }
            writer.append("</ul>");
            writer.append("<hr/>");
        }

        writer.append("</body>");
        writer.append("</html>");
    }

}
