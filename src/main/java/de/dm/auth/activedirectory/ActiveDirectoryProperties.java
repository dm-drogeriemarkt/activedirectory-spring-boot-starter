package de.dm.auth.activedirectory;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jakob Fels
 */
@ConfigurationProperties(ActiveDirectoryProperties.ACTIVEDIRECTORY_PROPERTIES_PREFIX)
public class ActiveDirectoryProperties {

    public static final String ACTIVEDIRECTORY_PROPERTIES_PREFIX = "security.activedirectory";
    /**
     * URLs that point to ActiveDirectory instances. One or more URLs can be provided,
     * separated by a single whitespace character.
     */
    private String url = "ldaps://sample01:636 ldaps://sample02:636";
    /**
     * The AD domain that users authenticate against.
     */
    private String domain = "SAMPLE.INC";
    /**
     * The connect timeout in milliseconds against the given server
     */
    private String connectTimeout = "1000";
    /**
     * The read timeout in milliseconds against the given server
     */
    private String readTimeout = "5000";


    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getConnectTimeout() { return connectTimeout; }
    public void setConnectTimeout(String connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getReadTimeout() {
        return readTimeout;
    }
    public void setReadTimeout(String readTimeout) {
        this.readTimeout = readTimeout;
    }

}
