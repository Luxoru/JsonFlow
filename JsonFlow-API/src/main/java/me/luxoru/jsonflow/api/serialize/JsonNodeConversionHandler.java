package me.luxoru.jsonflow.api.serialize;

/**
 * A merged interface for both serialization and deserialization of JSON nodes.
 * <p>
 * This interface extends both {@link JsonNodeSerializer} and {@link JsonNodeDeserializer},
 * providing a unified contract for handling the conversion of JSON nodes to and from
 * Java objects of type {@code T}.
 * <p>
 * Implementing this interface allows a class to handle both the serialization
 * and deserialization processes, enabling seamless conversion between JSON and Java objects.
 *
 * @param <T> the type of object this handler serializes or deserializes
 * @author Luxoru
 * @version 1.0
 */
public interface JsonNodeConversionHandler<T> extends JsonNodeSerializer<T>, JsonNodeDeserializer<T> {
}
