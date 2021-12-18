package ru.vtb.opms.driven.adapter;

import org.springframework.scheduling.annotation.Scheduled;
import ru.vtb.opms.metadata.ArchitecturalElement;
import ru.vtb.opms.driven.port.TriggerDbMigrationPort;

@ArchitecturalElement(
        type = ArchitecturalElement.Type.ADAPTER,
        variety = ArchitecturalElement.Variety.SCHEDULED_JOB,
        layer = ArchitecturalElement.Layer.INFRASTRUCTURE,
        description = "A class that contains a method executed periodically by Spring, the flow entry point"
)
public final class ScheduledDbMigrationJob {
    private final TriggerDbMigrationPort triggerDbMigrationUseCase;

    public ScheduledDbMigrationJob(
            TriggerDbMigrationPort triggerDbMigrationUseCase
    ) {
        this.triggerDbMigrationUseCase = triggerDbMigrationUseCase;
    }

    //@Scheduled(fixedDelayString = "${migration.delayMs}")
    public void invoke() {
        triggerDbMigrationUseCase.invoke();
    }
}
