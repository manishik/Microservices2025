package learn.manish.creditCard.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
            .withUserConfiguration(DatabaseConfiguration.class)
            .withPropertyValues(
                    "manish.datasource.jdbc-url=jdbc:h2:mem:configuration;DB_CLOSE_DELAY=-1",
                    "manish.datasource.driver-class-name=org.h2.Driver",
                    "manish.datasource.username=sa",
                    "manish.datasource.password=");

    @Test
    void createsConfiguredDataSourceAndTransactionManager() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(DataSource.class);
            assertThat(context).hasSingleBean(PlatformTransactionManager.class);
            assertThat(context.getBean(PlatformTransactionManager.class))
                    .isInstanceOf(DataSourceTransactionManager.class);
            assertThat(context.getBean(DataSource.class).getConnection().isValid(1)).isTrue();
        });
    }
}
