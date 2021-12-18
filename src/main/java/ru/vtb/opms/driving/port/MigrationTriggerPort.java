package ru.vtb.opms.driving.port;

import ru.vtb.opms.metadata.ArchitecturalElement;

@ArchitecturalElement(type = ArchitecturalElement.Type.PORT, layer = ArchitecturalElement.Layer.APPLICATION)
public interface MigrationTriggerPort {
    public void invoke();
}
