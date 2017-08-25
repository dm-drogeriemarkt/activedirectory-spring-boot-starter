package de.dm.auth.activedirectory.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * @author Jakob Fels
 */
public class AuthenticationCacheKeyGeneratorTest {

    private AuthenticationCacheKeyGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new AuthenticationCacheKeyGenerator();
    }

    @Test
    public void testGeneratedKey() throws Exception {
        UsernamePasswordAuthenticationToken authentication1 = new UsernamePasswordAuthenticationToken("Name", "Password");
        UsernamePasswordAuthenticationToken authentication2 = new UsernamePasswordAuthenticationToken("Name", "WrongPassword");

        Object key1 = generator.generate(null, null, authentication1);
        Object key2 = generator.generate(null, null, authentication2);

        Assert.assertThat(key1, is(not(equalTo(key2))));
    }

    @Test
    public void testGeneratedKeyIsConsistent() throws Exception {
        UsernamePasswordAuthenticationToken authentication1 = new UsernamePasswordAuthenticationToken("Name", "Password");
        UsernamePasswordAuthenticationToken authentication2 = new UsernamePasswordAuthenticationToken("Name", "Password");

        Object key1 = generator.generate(null, null, authentication1);
        Object key2 = generator.generate(null, null, authentication2);

        Assert.assertThat(key1, is(equalTo(key2)));
    }

    @Test(expected = IllegalStateException.class)
    public void testGeneratorOnlyWorksWithAuthentication() throws Exception {

        Object key = generator.generate(null, null, "Not an authentication");

        Assert.assertThat(key, is(nullValue()));
    }
}
