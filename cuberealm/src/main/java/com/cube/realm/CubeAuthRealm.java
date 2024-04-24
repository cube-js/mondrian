package com.cube.realm;

import java.security.Principal;

import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.realm.GenericPrincipal;

import java.util.ArrayList;
import java.util.List;

public class CubeAuthRealm extends RealmBase {

    private String username;
    private String password;

    @Override
    public Principal authenticate(String username, String credentials) {

        this.username = username;
        this.password = credentials;
        return getPrincipal(username);
        /* dummy authentication */
        // if (this.username.equals(this.password)) {
        //     log.info("Authentication is taking place for the user: " + username);
        //     return getPrincipal(username);
        // } else {
        //     return null;
        // }
    }

    @Override
    protected String getName() {
        return username;
    }

    @Override
    protected String getPassword(String username) {
        return password;
    }

    @Override
    protected Principal getPrincipal(String username) {
        List<String> roles = new ArrayList<String>();
        roles.add("CubeRole");
        Principal principal = new GenericPrincipal(username, password,roles);
        return principal;
    }
}
