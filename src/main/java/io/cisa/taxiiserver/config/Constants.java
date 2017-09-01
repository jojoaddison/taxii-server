package io.cisa.taxiiserver.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    
    public static final String ACCEPT_TAXII_HEADER="application/vnd.oasis.taxii+json; version=2.0";
    public static final String CONTENT_TYPE_TAXII_HEADER="Content-Type=application/vnd.oasis.taxii+json; version=2.0";
    
    public static final String ACCEPT_STIX_HEADER="application/vnd.oasis.stix+json; version=2.0";
    public static final String CONTENT_TYPE_STIX_HEADER="Content-Type=application/vnd.oasis.stix+json; version=2.0";
<<<<<<< HEAD

	public static final String SPEC_VERSION = "2.0";

	public static final String BUNDLE_TYPE = "bundle";
=======
>>>>>>> 759fb197725503daf5a2192ee305fc05a398da5d
    
    
    private Constants() {
    }
}
