package ru.vtb.opms.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Layer {
    Type value();

    enum Type {
        FRAMEWORK_INFRASTRUCTURE, INFRASTRUCTURE, APPLICATION, DOMAIN;
    }
}
