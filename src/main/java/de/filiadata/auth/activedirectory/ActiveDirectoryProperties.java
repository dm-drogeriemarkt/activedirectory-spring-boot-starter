package de.filiadata.auth.activedirectory;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jakob Fels
 */
@ConfigurationProperties(ActiveDirectoryProperties.ACTIVEDIRECTORY_PROPERTIES_PREFIX)
public class ActiveDirectoryProperties {

    public static final String ACTIVEDIRECTORY_PROPERTIES_PREFIX = "security.activedirectory";
    /**
     * URL that points to an ActiveDirectory instance. Multiple URLs can be provided,
     * separated by a single whitespace character.
     */
    private String url = "ldaps://sample:636";
    /**
     * The AD domain that users authenticate against.
     */
    private String domain = "SAMPLE.INC";

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
}
