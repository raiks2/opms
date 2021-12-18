package ru.vtb.opms.configuration;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.vtb.opms.metadata.Layer;
import ru.vtb.opms.core.application.usecase.TriggerDbMigrationUseCase;
import ru.vtb.opms.driven.adapter.ScheduledDbMigrationJob;
import ru.vtb.opms.driven.port.TriggerDbMigrationPort;
import ru.vtb.opms.driving.adapter.MigrationTriggerDao;
import ru.vtb.opms.driving.adapter.FindAllMigrationDataDao;
import ru.vtb.opms.driving.port.FindAllMigrationDataPort;
import ru.vtb.opms.driving.port.MigrationTriggerPort;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
@Layer(Layer.Type.INFRASTRUCTURE)
public class OpmsConfiguration {
    @Bean
    public TriggerDbMigrationPort triggerDbMigrationUseCase(
            FindAllMigrationDataPort findMigrationDataRepositoryPort,
            MigrationTriggerPort migrationTriggerPort,
            @Value("${migration.numThreads}") int numThreads
    ) {
        return new TriggerDbMigrationUseCase(findMigrationDataRepositoryPort, migrationTriggerPort, numThreads, LoggerFactory.getLogger("TriggerDbMigrationUseCase"));
    }

    @Bean
    public ScheduledDbMigrationJob scheduledDbMigrationJob(
            TriggerDbMigrationPort triggerDbMigrationUseCase
    ) {
        return new ScheduledDbMigrationJob(triggerDbMigrationUseCase);
    }

    @Bean
    public MigrationTriggerPort oracleMigrationTrigger(
            @Qualifier("oracleJdbcTemplate") JdbcTemplate oracleJdbcTemplate,
            @Value("${migration.queryTimeoutMs}") int queryTimeoutMs,
            @Value("{migration.query.remoteProcedureCallSql}") String remoteProcedureCallSql
    ) {
        return new MigrationTriggerDao(oracleJdbcTemplate, queryTimeoutMs, remoteProcedureCallSql, LoggerFactory.getLogger("MigrationTrigger"));
    }

    @Bean
    public FindAllMigrationDataDao findAllMigrationDataRepository(@Qualifier("postgresJdbcTemplate") JdbcTemplate postgresJdbcTemplate) {
        return new FindAllMigrationDataDao(postgresJdbcTemplate);
    }

    @Bean(name = "oracleDs")
    @ConfigurationProperties("migration.oracle.datasource")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgresDs")
    @ConfigurationProperties("migration.postgres.datasource")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oracleJdbcTemplate")
    public JdbcTemplate oracleJdbcTemplate(@Qualifier("oracleDs") DataSource oracleDataSource) {
        return new JdbcTemplate(oracleDataSource);
    }

    @Bean(name = "postgresJdbcTemplate")
    public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDs") DataSource postgresDataSource) {
        return new JdbcTemplate(postgresDataSource);
    }
}
