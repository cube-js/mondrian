package mondrian.xmla.impl;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;


public class CubeAuthXmlaRequestCallback extends AuthenticatingXmlaRequestCallback {
    
    public void preAction(HttpServletRequest request, Element[] requestSoapParts, Map<String, Object> context) throws Exception {
        // TODO Auto-generated method stub
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        throw new UnsupportedOperationException("preAction: Username: " + username + " Password: " + password);
    }
    
    @Override
    public String authenticate(String username, String password, String sessionID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("1941: Username: " + username + " Password: " + password + " SessionID: " + sessionID);
    }
}
