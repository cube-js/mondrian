package mondrian.xmla.impl;

import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mondrian.olap.Util;
import mondrian.xmla.XmlaConstants;
import org.apache.catalina.realm.GenericPrincipal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.Element;

public class CubeAuthXmlaRequestCallback extends AuthenticatingXmlaRequestCallback {

    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public void init(ServletConfig servletConfig) throws ServletException {
        // Nothing to initialize here. Subclasses can override
        // this if they wish.
        ServletConfig tServletConfig = servletConfig;
        System.out.println("CubeAuthXmlaRequestCallback initialized");
    }

    public void preAction(
        HttpServletRequest request,
        Element[] requestSoapParts,
        Map<String, Object> context)
        throws Exception
    {
        /*
         * This is where the magic happens. At this stage, we have
         * the username/password known. We will delegate the authentication
         * process down to the subclass.
         */
        final String roleNames =
            authenticate(
                (String) context.get(XmlaConstants.CONTEXT_XMLA_USERNAME),
                (String) context.get(XmlaConstants.CONTEXT_XMLA_PASSWORD),
                (String) context.get(XmlaConstants.CONTEXT_XMLA_SESSION_ID));
        context.put(
            XmlaConstants.CONTEXT_ROLE_NAME,
            roleNames);
    }

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
        // try {
        //     Class.forName("org.postgresql.Driver");
        // } catch (ClassNotFoundException e) {
        //     e.printStackTrace();
        //     throw new UnsupportedOperationException("2309: Unable to load PostgreSQL JDBC Driver");
        // }
        
        // // Connection parameters
        // String url = "jdbc:postgresql://amber-goose.sql.aws-us-west-2.cubecloudapp.dev:5432/amber-goose";

        // // Attempt connection
        // try (Connection conn = DriverManager.getConnection(url, username, password)) {
        //     if (conn != null) {
        //         System.out.println("Connected to the PostgreSQL server successfully.");
        //     } else {
        //         System.out.println("Failed to make connection!");
        //         throw new UnsupportedOperationException("Failed to make connection!");
        //     }
        // } catch (SQLException e) {
        //     System.out.println(e.getMessage());
        //     throw new UnsupportedOperationException(e.getMessage());
        // }
        userContext.set(username);

        return "user";
    }

    public static String getUserContext() {
        return userContext.get();
    }

    public void postAction(
        HttpServletRequest request,
        HttpServletResponse response,
        byte[][] responseSoapParts,
        Map<String, Object> context)
        throws Exception
    {
        Map<String, Object> tContext = context;
        return;
    }
}
