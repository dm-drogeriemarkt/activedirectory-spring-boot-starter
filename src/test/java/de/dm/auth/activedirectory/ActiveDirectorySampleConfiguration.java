package de.dm.auth.activedirectory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

/**
 * @author Jakob Fels
 */
//tag::sample[]
@Configuration
public class ActiveDirectorySampleConfiguration {

    @Autowired
    @ActiveDirectoryProvider // <1>
    private AuthenticationProvider provider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

}
//end::sample[]
