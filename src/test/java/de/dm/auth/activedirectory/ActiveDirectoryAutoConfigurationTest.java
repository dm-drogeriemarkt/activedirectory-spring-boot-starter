package de.dm.auth.activedirectory;

import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ActiveDirectoryAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(ActiveDirectoryAutoConfiguration.class, CacheAutoConfiguration.class));

    @Test
    public void enable_ActiveDirectoryAutoConfiguration_if_property_enabled_is_missing() {
        this.contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(ActiveDirectoryAutoConfiguration.class);
        });
    }

    @Test
    public void enable_ActiveDirectoryAutoConfiguration_if_property_enabled_has_the_value_true() {
        this.contextRunner.withPropertyValues(ActiveDirectoryProperties.ACTIVEDIRECTORY_PROPERTIES_PREFIX + ".enabled=true").run((context) -> {
            assertThat(context).hasSingleBean(ActiveDirectoryAutoConfiguration.class);
        });
    }

    @Test
    public void disable_ActiveDirectoryAutoConfiguration_if_property_enabled_has_NOT_the_value_true() {
        this.contextRunner.withPropertyValues(ActiveDirectoryProperties.ACTIVEDIRECTORY_PROPERTIES_PREFIX + ".enabled=false").run((context) -> {
            assertThat(context).doesNotHaveBean(ActiveDirectoryAutoConfiguration.class);
        });
    }
}