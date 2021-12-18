package ru.vtb.opms.driving.adapter;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.vtb.opms.metadata.ArchitecturalElement;
import ru.vtb.opms.driving.dto.MigrationDataDto;
import ru.vtb.opms.driving.port.FindAllMigrationDataPort;

import java.util.List;

@ArchitecturalElement(
        type = ArchitecturalElement.Type.ADAPTER,
        variety = ArchitecturalElement.Variety.DAO,
        layer = ArchitecturalElement.Layer.INFRASTRUCTURE,
        description = "Fetches the migration data"
)
public final class FindAllMigrationDataDao implements FindAllMigrationDataPort {
    private final JdbcTemplate jdbcTemplate;

    public FindAllMigrationDataDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MigrationDataDto> invoke() {
        return jdbcTemplate.queryForList("SELECT status, procedure_name FROM migration", MigrationDataDto.class);
    }
}
