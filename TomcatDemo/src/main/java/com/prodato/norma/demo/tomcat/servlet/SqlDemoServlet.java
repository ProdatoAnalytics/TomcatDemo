package com.prodato.norma.demo.tomcat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class SqlDemoServlet extends HttpServlet {

    public static final String DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    public static final String URL = "jdbc:hsqldb:http://localhost:8080/TomcatDemo/HSQLDB";

    public static final String USERNAME = "SA";
    public static final String PASSWORD = "";

    private static final String STATEMENT_CREATE_TABLE = "CREATE TABLE t(a INTEGER, b BIGINT)";
    private static final String STATEMENT_INSERT_VALUES = "INSERT INTO t VALUES ?, ?";
    private static final String STATEMENT_SELECT_VALUES = "SELECT * FROM t";
    private static final String STATEMENT_DROP_TABLE = "DROP TABLE t";

    private static final long serialVersionUID = 1L;

    public SqlDemoServlet() {
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        final PrintWriter writer = response.getWriter();
        writer.append("<html>");
        writer.append("<head><title>Hsql Demo</title></head>");
        writer.append("<body>");

        writer.append("<h1>Hsql Demo</h1>");

        boolean exceptionOccured = false;

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;

        try {
            // Treiberklasse registrieren
            Class.forName(DRIVER);
            // Verbindung zur Datenbank (über HsqlServlet) aufbauen
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            writer.append("<p>Initialized</p>");

            // Tablle anlegen
            stmt = conn.createStatement();
            stmt.execute(STATEMENT_CREATE_TABLE);
            writer.append("<p>Created Table</p>");

            // Werte in Tabelle einfügen
            pstmt = conn.prepareStatement(STATEMENT_INSERT_VALUES);
            pstmt.setInt(1, 3);
            pstmt.setLong(2, 5L);
            pstmt.execute();
            writer.append("<p>Inserted values</p>");

            // Werte wieder aus Tabelle auslesen
            final ResultSet rs = stmt.executeQuery(STATEMENT_SELECT_VALUES);
            while (rs.next()) {
                writer.append("<p>Read Value a: ").append(String.valueOf(rs.getInt(1))).append(" Value b: ").append(String.valueOf(rs.getLong(2)));
            }

            // Tablle löschen
            stmt.execute(STATEMENT_DROP_TABLE);
            writer.append("<p>Dropped Table</p>");

            pstmt.close();
            stmt.close();
            conn.close();
        } catch (final SQLException e) {
            e.printStackTrace();
            exceptionOccured = true;
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
            exceptionOccured = true;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (final SQLException e) {
                e.printStackTrace();
                exceptionOccured = true;
            }
        }
        if (exceptionOccured) {
            writer.append("<p><b>Eine Exception ist geflogen!</b> Bitte Stacktrace in Konsole nachlesen</p>");
        }

        writer.append("</body>");
        writer.append("</html>");
    }
}
