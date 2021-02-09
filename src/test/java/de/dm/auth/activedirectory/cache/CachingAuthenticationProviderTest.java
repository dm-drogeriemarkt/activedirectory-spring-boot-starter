package de.dm.auth.activedirectory.cache;

import de.dm.auth.activedirectory.ActiveDirectoryProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

/**
 * @author Jakob Fels
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@TestPropertySource(properties = { ActiveDirectoryProperties.ACTIVEDIRECTORY_PROPERTIES_PREFIX + ".urls=ldaps://example-ad01.inc:636,ldaps://example-ad02.inc:636"} )
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
