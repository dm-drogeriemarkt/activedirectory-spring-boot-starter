package de.filiadata.auth.activedirectory.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Sha512DigestUtils;

import java.lang.reflect.Method;

/**
 * {@link KeyGenerator} to extract cache keys for an {@link Authentication}.
 *
 * @author Jakob Fels
 */
public final class AuthenticationCacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        Authentication authentication = getAuthentication(params);
        return generateKey(authentication);
    }

    private Authentication getAuthentication(Object[] params) {
        if (params.length != 1 || !Authentication.class.isInstance(params[0])) {
            throw new IllegalStateException("AuthenticationKeyGenerator is meant to be used to cache authentications only!");
        }
        return (Authentication) params[0];
    }

    private String generateKey(Authentication authentication) {
        final UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) authentication;

        String username = userToken.getName();
        String password = (String) authentication.getCredentials();
        return Sha512DigestUtils.shaHex(username + password);
    }
}
