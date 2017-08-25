package de.dm.auth.activedirectory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

import java.lang.annotation.*;

/**
 * Custom @{@link Qualifier} annotation to easily retrieve the auto-configured {@link ActiveDirectoryLdapAuthenticationProvider}
 * @author Jakob Fels
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Qualifier
public @interface ActiveDirectoryProvider {

}
