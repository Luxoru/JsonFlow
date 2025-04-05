package me.luxoru.jsonflow.api.annotation;

import me.luxoru.jsonflow.api.serialize.JsonNodeConversionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a plain Java object (POJO) as serializable by JsonFlow using a custom serializer.
 * <p>
 * This annotation is used to register a class for custom node-level serialization
 * and deserialization via a specified {@link JsonNodeConversionHandler}.
 * Unlike {@link FlowSerializable}, this does not require the class to extend any
 * base entity and is useful for simple objects that need specialized conversion logic.
 * <p>
 * The {@link #serializer()} attribute must be provided to indicate which handler
 * should be used for converting the class to and from {@code JsonNode}.
 *
 * @author Luxoru
 * @version 1.0
 * @see JsonNodeConversionHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NodeSerializable {

    /**
     * The serializer class that handles conversion between this POJO and a JsonNode.
     *
     * @return a class extending {@link JsonNodeConversionHandler}
     */
    Class<? extends JsonNodeConversionHandler> serializer();
}
