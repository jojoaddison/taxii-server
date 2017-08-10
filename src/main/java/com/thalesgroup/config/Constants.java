package com.thalesgroup.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String ACCEPT_HEADER="Accept=application/vnd.oasis.taxii+json; version=2.0";
    
    private Constants() {
    }
}
