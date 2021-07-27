package de.dm.auth.activedirectory;

import de.dm.auth.activedirectory.cache.AuthenticationCacheKeyGenerator;
import de.dm.auth.activedirectory.cache.CachingAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

import java.util.Hashtable;

@Configuration
@EnableCaching
@EnableConfigurationProperties(ActiveDirectoryProperties.class)
@ConditionalOnProperty(prefix = ActiveDirectoryProperties.ACTIVEDIRECTORY_PROPERTIES_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class ActiveDirectoryAutoConfiguration {

    @Autowired
    private ActiveDirectoryProperties properties;

    @Bean
    @ActiveDirectoryProvider
    @ConditionalOnMissingBean
    @ConfigurationProperties(ActiveDirectoryProperties.ACTIVEDIRECTORY_PROPERTIES_PREFIX)
    public CachingAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider provider;
        String ldapUrls = String.join(" ", properties.getUrls());
        provider = new ActiveDirectoryLdapAuthenticationProvider(properties.getDomain(), ldapUrls);
        Hashtable<String, Object> env = new Hashtable<>(); // NOSONAR - the javax.ldap api demands a hashtable
        env.put("com.sun.jndi.ldap.connect.timeout", properties.getConnectTimeout());
        env.put("com.sun.jndi.ldap.read.timeout", properties.getReadTimeout());
        provider.setContextEnvironmentProperties(env);
        provider.setSearchFilter("(&(objectClass=user)(samAccountName={1}))");
        provider.setAuthoritiesMapper(authoritiesMapper());
        return new CachingAuthenticationProvider(provider);
    }

    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        SimpleAuthorityMapper simpleAuthorityMapper = new SimpleAuthorityMapper();
        simpleAuthorityMapper.setConvertToUpperCase(true);
        return simpleAuthorityMapper;
    }

    @Bean
    public KeyGenerator authKeyGenerator() {
        return new AuthenticationCacheKeyGenerator();
    }

    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrls(properties.getUrls());
        return ldapContextSource;
    }

    @Bean
    public LdapTemplate ldapTemplateForLdapHealthCheck() {
        return new LdapTemplate(ldapContextSource());
    }

}
