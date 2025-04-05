package me.luxoru.jsonflow.api.annotation;

import com.fasterxml.jackson.databind.JsonDeserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as eligible for custom serialization and deserialization by JsonFlow.
 * <p>
 * Classes annotated with {@code @FlowSerializable} are treated as serializable entities
 * in the JsonFlow system. It is recommended (though not strictly required) that these
 * classes extend a base implementation such as {@code JsonEntity} to ensure compatibility
 * with the framework.
 * <p>
 * Optionally, you may provide a custom Jackson {@link JsonDeserializer} via the
 * {@link #using()} attribute to control how instances of the class are deserialized.
 * If not specified, the default deserializer will be used.
 *
 * @author Luxoru
 * @version 1.0
 * @see me.luxoru.jsonflow.api.entity.JsonEntity
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FlowSerializable {

    /**
     * Specifies a custom deserializer to be used for this class.
     * If left as {@link JsonDeserializer.None}, the default deserializer will be applied.
     *
     * @return the custom deserializer class
     */
    Class<? extends JsonDeserializer> using() default JsonDeserializer.None.class;

}
