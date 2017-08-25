package de.dm.auth.activedirectory;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Jakob Fels
 */
//tag::sample[]
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class RoleSampleConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .anyRequest().hasRole("SAMPLE-ADMINS"); // <1>
    }
}
//end::sample[]
