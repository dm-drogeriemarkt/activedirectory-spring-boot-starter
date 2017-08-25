package de.dm.auth.activedirectory.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Jakob Fels
 */
public class CachingAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(CachingAuthenticationProvider.class);
    private final AuthenticationProvider delegate;
    /**
     * Enable caching of authentication.
     */
    private boolean cacheEnabled = true;

    public CachingAuthenticationProvider(AuthenticationProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    @Cacheable(condition = "#root.target.isCacheEnabled()",
            unless = "!#result.isAuthenticated()",
            cacheNames = "authCache",
            keyGenerator = "authKeyGenerator")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LOG.debug("Caching of credentials is {}", cacheEnabled ? "enabled" : "disabled");
        return delegate.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return delegate.supports(authentication);
    }

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }
}
