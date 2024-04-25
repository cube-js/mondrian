package mondrian.xmla.impl;

import java.security.Principal;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import mondrian.xmla.XmlaConstants;
import org.apache.catalina.realm.GenericPrincipal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CubeAuthXmlaRequestCallback extends AuthenticatingXmlaRequestCallback {

    public boolean processHttpHeader(
            HttpServletRequest request,
            HttpServletResponse response,
            Map<String, Object> context) {

        GenericPrincipal genericPrincipal = (GenericPrincipal) request.getUserPrincipal();

        if (genericPrincipal != null) {
            context.put(XmlaConstants.CONTEXT_XMLA_USERNAME, genericPrincipal.getName());
            context.put(XmlaConstants.CONTEXT_XMLA_PASSWORD, genericPrincipal.getPassword());
        } else {
            throw new UnsupportedOperationException("2309: User not authenticated");
        }

        return true;
    }

    @Override
    public String authenticate(String username, String password, String sessionID) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("2309: Unable to load PostgreSQL JDBC Driver");
        }
        
        // Connection parameters
        String url = "jdbc:postgresql://amber-goose.sql.aws-us-west-2.cubecloudapp.dev:5432/amber-goose";

        // Attempt connection
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
                throw new UnsupportedOperationException("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new UnsupportedOperationException(e.getMessage());
        }

        return "CubeRole";
    }
}
