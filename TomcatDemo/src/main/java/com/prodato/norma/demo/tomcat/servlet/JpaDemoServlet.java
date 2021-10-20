package com.prodato.norma.demo.tomcat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.prodato.norma.demo.tomcat.jpa.demo.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JpaDemoServlet extends HttpServlet {

    private static final String STATEMENT_CREATE_TABLE = "CREATE TABLE book(id INTEGER, title VARCHAR(255))";

    private static final long serialVersionUID = 1L;

    public JpaDemoServlet() {
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        final PrintWriter writer = response.getWriter();
        writer.append("<html>");
        writer.append("<head><title>Hsql Demo</title></head>");
        writer.append("<body>");

        writer.append("<h1>JPA Demo</h1>");

        JpaDemoServlet.createTable();

        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("hsqlJpaTest");
        EntityManager em = factory.createEntityManager();

        Book book = new Book();
        book.setId(1);
        book.setTitle("The Lord Of The Rings");

        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.close();

        book = null;
        em = factory.createEntityManager();
        final TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
        for (final Book readBook : query.getResultList()) {
            writer.append("<p>Found book ").append(readBook.getTitle()).append("</p>");
        }

        writer.append("</body>");
        writer.append("</html>");
    }

    private static void createTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Treiberklasse registrieren
            Class.forName(SqlDemoServlet.DRIVER);
            // Verbindung zur Datenbank (über HsqlServlet) aufbauen
            conn = DriverManager.getConnection(SqlDemoServlet.URL, SqlDemoServlet.USERNAME, SqlDemoServlet.PASSWORD);

            // Tablle anlegen
            stmt = conn.createStatement();
            stmt.execute(STATEMENT_CREATE_TABLE);
            stmt.execute("INSERT INTO book (id, title) VALUES (2, 'Dune')");
            stmt.close();
            conn.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
