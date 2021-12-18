package ru.vtb.opms.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ArchitecturalElement {
    Layer layer();
    Type type();
    Variety variety() default Variety.GENERIC;
    String description() default "";

    enum Type {
        GENERIC, PORT, ADAPTER, USE_CASE, SERVICE;
    }

    enum Layer {
        FRAMEWORK_INFRASTRUCTURE, INFRASTRUCTURE, APPLICATION, DOMAIN;
    }

    enum Variety {
        GENERIC,

        SCHEDULED_JOB,

        REST_ENDPOINT,
        REST_CLIENT,

        SOAP_ENDPOINT,
        SOAP_CLIENT,

        DAO,
        REPOSITORY,

        MESSAGE_LISTENER,
        MESSAGE_SENDER;
    }
}
