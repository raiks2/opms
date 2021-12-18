package ru.vtb.opms.driving.dto;

import ru.vtb.opms.metadata.Layer;

@Layer(Layer.Type.APPLICATION)
public final class MigrationDataDto {
    private final String status;
    private final String procedureName;

    public MigrationDataDto(String status, String procedureName) {
        this.status = status;
        this.procedureName = procedureName;
    }

    public String getProcedureName() {
        return procedureName;
    }
}