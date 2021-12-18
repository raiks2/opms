package ru.vtb.opms.core.application.usecase;

import org.slf4j.Logger;
import ru.vtb.opms.metadata.ArchitecturalElement;
import ru.vtb.opms.driven.port.TriggerDbMigrationPort;
import ru.vtb.opms.driving.dto.MigrationDataDto;
import ru.vtb.opms.driving.port.FindAllMigrationDataPort;
import ru.vtb.opms.driving.port.MigrationTriggerPort;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@ArchitecturalElement(
        type = ArchitecturalElement.Type.USE_CASE,
        layer = ArchitecturalElement.Layer.APPLICATION,
        description = "A business use case"
)
public class TriggerDbMigrationUseCase implements TriggerDbMigrationPort {
    private final FindAllMigrationDataPort findAllMigrationDataRepositoryPort;
    private final MigrationTriggerPort migrationTriggerPort;
    private final int numThreads;
    private final Logger logger;

    public TriggerDbMigrationUseCase(FindAllMigrationDataPort findMigrationDataRepositoryPort, MigrationTriggerPort migrationTriggerPort, int numThreads, Logger logger) {
        this.findAllMigrationDataRepositoryPort = findMigrationDataRepositoryPort;
        this.migrationTriggerPort = migrationTriggerPort;
        this.numThreads = numThreads;
        this.logger = logger;
    }

    private void doBulkMigration() {
        logger.info("Starting the bulk migration stage");
        List<MigrationDataDto> migrationDataDtos = findAllMigrationDataRepositoryPort.invoke();
        logger.info("Attempting to create a fixed thread pool with {} threads", numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        try {
            logger.info("Attempting to execute stored procedures. Will wait for all of them to complete");
            executorService.invokeAll(createTasksFromDtos(migrationDataDtos));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("Successfully finished the bulk migration stage");
    }

    /**
     * Creates a list of tasks suitable for submission to the ExecutorService by mapping DTOs to
     * corresponding lambdas containing logic for calling stored procedures
     */
    private List<Callable<Object>> createTasksFromDtos(List<MigrationDataDto> dtos) {
        return dtos.stream()
                .<Callable<Object>>map(
                        dto -> () -> {
                            return callStoredProcedure(dto.getProcedureName());
                        }
                )
                .collect(Collectors.toList());
    }

    private Object callStoredProcedure(String procedureName) {
        migrationTriggerPort.invoke();
        return null;
    }

    private String calculateHash() {
        return "Some hash";
    }

    private void doIncrementalMigration() {
        // Execute triggers
    }

    public void invoke() {
        doBulkMigration();
        String hash = calculateHash();
        doIncrementalMigration();
    }
}
