package me.luxoru.jsonflow.api.annotation;

import me.luxoru.jsonflow.api.serialize.JsonNodeConversionHandler;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field within a class (or superclass) annotated with {@link FlowSerializable}
 * to be included in the serialization and deserialization process handled by JsonFlow.
 * <p>
 * When deserializing, fields marked with this annotation are processed by the
 * custom deserialization logic. You can specify a custom key name for the field and
 * provide a custom serializer to handle type conversion.
 * <p>
 * If no custom serializer is provided, a default type conversion mechanism is used.
 *
 * @author Luxoru
 * @version 1.0
 * @see FlowSerializable
 * @see JsonNodeConversionHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FlowField {

    /**
     * Specifies the name of the field in the JSON representation.
     * If left empty, the field's Java name is used.
     *
     * @return the JSON field name
     */
    String value() default "";

    /**
     * Specifies the custom serializer class to use for this field.
     * The class must implement {@link JsonNodeConversionHandler}.
     * If left as the default, the system's default type conversion will be applied.
     *
     * @return the serializer class
     */
    Class<? extends JsonNodeConversionHandler> serializer() default JsonNodeConversionHandler.class;
}
