package ru.vtb.opms.driving.adapter;

import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.vtb.opms.metadata.ArchitecturalElement;
import ru.vtb.opms.driving.port.MigrationTriggerPort;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

@ArchitecturalElement(
        type = ArchitecturalElement.Type.ADAPTER,
        variety = ArchitecturalElement.Variety.DAO,
        layer = ArchitecturalElement.Layer.INFRASTRUCTURE,
        description = "Triggers stored procedures on the Oracle side"
)
public final class MigrationTriggerDao implements MigrationTriggerPort {
    private final JdbcTemplate jdbcTemplate;
    private final int queryTimeoutMs;
    private final Logger logger;
    private final String remoteProcedureCallSql;

    public MigrationTriggerDao(JdbcTemplate jdbcTemplate, int queryTimeoutMs, String remoteProcedureCallSql, Logger logger) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryTimeoutMs = queryTimeoutMs;
        this.remoteProcedureCallSql = remoteProcedureCallSql;
        this.logger = logger;
    }

    private void tryInvokeStoredProcedureWithTimeout(int queryTimeoutMs) {
        try (
                Connection connection = jdbcTemplate.getDataSource().getConnection();
                CallableStatement statement = connection.prepareCall(remoteProcedureCallSql);
        ) {
            statement.setQueryTimeout(queryTimeoutMs);
            statement.executeUpdate();
        } catch (SQLTimeoutException e) {
            logger.warn("The query '{}' timed out", remoteProcedureCallSql, e);
        } catch (SQLException e) {
            logger.warn("The query '{}' failed", remoteProcedureCallSql, e);
        }
    }

    @Override
    public void invoke() {
        tryInvokeStoredProcedureWithTimeout(queryTimeoutMs);
    }
}
