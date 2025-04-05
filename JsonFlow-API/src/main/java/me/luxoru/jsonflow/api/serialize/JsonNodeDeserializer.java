package me.luxoru.jsonflow.api.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Interface for deserializing an object of type {@code T} into a JSON node.
 * <p>
 * Implementing classes must provide the logic for converting a Java object of type
 * {@code T} into an {@link ObjectNode} representation using a provided
 * {@link ObjectMapper}.
 * <p>
 * This is part of the overall serialization/deserialization process that allows
 * the conversion of Java objects into JSON-compatible structures.
 *
 * @param <T> the type of object to deserialize
 * @author Luxoru
 * @version 1.0
 */
public interface JsonNodeDeserializer<T> {

    /**
     * Deserializes the given object of type {@code T} into an {@link ObjectNode}.
     *
     * @param object the object to deserialize
     * @param mapper the {@link ObjectMapper} to use for the conversion
     * @return the {@link ObjectNode} representation of the object
     */
    ObjectNode deserialize(T object, ObjectMapper mapper);
}
