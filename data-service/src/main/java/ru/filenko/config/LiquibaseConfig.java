package ru.filenko.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LiquibaseConfig {

    private static final Logger log = LoggerFactory.getLogger(LiquibaseConfig.class);

    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String datasourceUrl;

    @ConfigProperty(name = "quarkus.datasource.username")
    String datasourceUsername;

    @ConfigProperty(name = "quarkus.datasource.password")
    String datasourcePassword;

    @ConfigProperty(name = "quarkus.liquibase.change-log", defaultValue = "db/changeLog.xml")
    String changeLogLocation;

    @ConfigProperty(name = "quarkus.liquibase.clean-at-start")
    boolean cleanAtStart;

    public void runLiquibaseMigration(@Observes StartupEvent event) {
        final ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(Thread.currentThread().getContextClassLoader());
        try (final Liquibase liquibase = new Liquibase(
                changeLogLocation,
                resourceAccessor,
                DatabaseFactory.getInstance()
                        .openConnection(datasourceUrl, datasourceUsername, datasourcePassword, null, resourceAccessor))
        ) {
            if (cleanAtStart) {
                log.warn("Liquibase drop database");
                liquibase.dropAll();
            }
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            log.error("Liquibase error: {}", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
