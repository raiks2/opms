package ru.vtb.opms.driven.dto;

import ru.vtb.opms.metadata.Layer;

@Layer(Layer.Type.INFRASTRUCTURE)
public class ServiceStatusDto {
    private final Status status;

    public ServiceStatusDto(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        OK, NOT_OK;
    }
}
