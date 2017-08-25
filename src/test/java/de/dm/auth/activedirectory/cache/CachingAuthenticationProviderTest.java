package de.dm.auth.activedirectory.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

/**
 * @author Jakob Fels
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestConfiguration.class)
@CacheEvict
public class CachingAuthenticationProviderTest {

    @Autowired
    private AuthenticationProvider mockAuthenticationProvider;
    @Autowired
    private CachingAuthenticationProvider cachingAuthenticationProvider;

    @Before
    public void setUp() throws Exception {
        Mockito.reset(mockAuthenticationProvider);
    }

    @Test
    public void testCaching() throws Exception {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("name", "password");
        UsernamePasswordAuthenticationToken successfulAuthentication = new UsernamePasswordAuthenticationToken("name", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        Mockito.when(mockAuthenticationProvider.authenticate(authentication)).thenReturn(successfulAuthentication);
        cachingAuthenticationProvider.authenticate(authentication);
        cachingAuthenticationProvider.authenticate(authentication);

        Mockito.verify(mockAuthenticationProvider, Mockito.times(1)).authenticate(authentication);
    }

    @Test
    public void testCachingEnabledToggle() throws Exception {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("name", "password");
        UsernamePasswordAuthenticationToken successfulAuthentication = new UsernamePasswordAuthenticationToken("name", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        Mockito.when(mockAuthenticationProvider.authenticate(authentication)).thenReturn(successfulAuthentication);

        cachingAuthenticationProvider.setCacheEnabled(false);

        cachingAuthenticationProvider.authenticate(authentication);
        cachingAuthenticationProvider.authenticate(authentication);

        Mockito.verify(mockAuthenticationProvider, Mockito.times(2)).authenticate(authentication);
    }

    @Test
    public void testCachingUnlessCondition() throws Exception {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("name", "password");

        Mockito.when(mockAuthenticationProvider.authenticate(authentication)).thenReturn(authentication);
        cachingAuthenticationProvider.authenticate(authentication);
        cachingAuthenticationProvider.authenticate(authentication);

        Mockito.verify(mockAuthenticationProvider, Mockito.times(2)).authenticate(authentication);
    }


}
