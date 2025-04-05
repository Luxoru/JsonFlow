package me.luxoru.jsonflow.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field to be ignored during the serialization and deserialization process
 * handled by JsonFlow.
 * <p>
 * When a field is annotated with {@code @FlowIgnore}, it will be completely excluded
 * from both the JSON output and the data parsed during deserialization, even if it
 * would otherwise be included based on {@link FlowField} or naming conventions.
 *
 * @author Luxoru
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FlowIgnore {
}
