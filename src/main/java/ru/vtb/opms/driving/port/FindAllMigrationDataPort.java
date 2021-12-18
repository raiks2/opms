package ru.vtb.opms.driving.port;

import ru.vtb.opms.metadata.ArchitecturalElement;
import ru.vtb.opms.driving.dto.MigrationDataDto;

import java.util.List;

@ArchitecturalElement(type = ArchitecturalElement.Type.PORT, layer = ArchitecturalElement.Layer.APPLICATION)
public interface FindAllMigrationDataPort {
    public List<MigrationDataDto> invoke();
}
