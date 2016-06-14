package de.filiadata.auth.activedirectory.cache;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationProvider;

/**
 * @author Jakob Fels
 */
@Configuration
@EnableCaching
@EnableAutoConfiguration
public class TestConfiguration {

    @Bean
    public AuthenticationProvider mockAuthenticationProvider() {
        return Mockito.mock(AuthenticationProvider.class);
    }

    @Bean
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CachingAuthenticationProvider cachingAuthenticationProvider() {
        return new CachingAuthenticationProvider(mockAuthenticationProvider());
    }
}
