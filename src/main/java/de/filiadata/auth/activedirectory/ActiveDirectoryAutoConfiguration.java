package de.filiadata.auth.activedirectory;

import de.filiadata.auth.activedirectory.cache.AuthenticationCacheKeyGenerator;
import de.filiadata.auth.activedirectory.cache.CachingAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

import static de.filiadata.auth.activedirectory.ActiveDirectoryProperties.ACTIVEDIRECTORY_PROPERTIES_PREFIX;

@Configuration
@EnableCaching
@EnableConfigurationProperties(ActiveDirectoryProperties.class)
public class ActiveDirectoryAutoConfiguration {

    @Autowired
    private ActiveDirectoryProperties properties;

    @Bean
    @ActiveDirectoryProvider
    @ConditionalOnMissingBean
    @ConfigurationProperties(ACTIVEDIRECTORY_PROPERTIES_PREFIX)
    public CachingAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider provider;
        provider = new ActiveDirectoryLdapAuthenticationProvider(properties.getDomain(), properties.getUrl());
        return new CachingAuthenticationProvider(provider);
    }

    @Bean
    public KeyGenerator authKeyGenerator() {
        return new AuthenticationCacheKeyGenerator();
    }

}
