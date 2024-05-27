package mondrian.web.servlet;

import java.io.IOException;

import javax.servlet.http.*;

import mondrian.server.MondrianServerRegistry;

public class HealthCheckServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int schemaVersion = MondrianServerRegistry.INSTANCE.getSchemaVersion();
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Healthy. Schema Version: " + schemaVersion + "\n");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Unhealthy. Error: " + e.getMessage() + "\n");
        }
    }
}
