package ru.vtb.opms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vtb.opms.metadata.Layer;

@SpringBootApplication
@Layer(Layer.Type.INFRASTRUCTURE)
public class OraclePostgresMigrationServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(OraclePostgresMigrationServiceApp.class);
    }
}