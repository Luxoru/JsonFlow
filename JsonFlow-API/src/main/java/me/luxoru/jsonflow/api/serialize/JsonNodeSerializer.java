package me.luxoru.jsonflow.api.serialize;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Interface for serializing a JSON node into an object of type {@code T}.
 * <p>
 * Implementing classes must provide the logic for converting a {@link JsonNode}
 * into a Java object of type {@code T}.
 * <p>
 * This is part of the overall serialization/deserialization process that allows
 * the conversion of JSON data into Java objects.
 *
 * @param <T> the type of object to serialize
 * @author Luxoru
 * @version 1.0
 */
public interface JsonNodeSerializer<T> {

    /**
     * Serializes the given {@link JsonNode} into an object of type {@code T}.
     *
     * @param node the {@link JsonNode} to serialize
     * @return the object of type {@code T} created from the JSON node
     */
    T serialize(JsonNode node);
}
