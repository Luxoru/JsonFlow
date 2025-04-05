package me.luxoru.jsonflow.api.entity;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Collection;
import java.util.Set;

/**
 * Interface representing a JSON entity that can be serialized and deserialized.
 * <p>
 * Implementing this interface allows an entity to maintain parent-child relationships
 * with other entities, manage its associated file name, and convert itself to a
 * JSON {@link ObjectNode} representation.
 * <p>
 * Entities implementing this interface should provide logic for converting their
 * state to and from JSON format and managing their relationships with other entities.
 *
 * @author Luxoru
 * @version 1.0
 */
public interface JsonEntity {

    /**
     * Returns the set of parent entities associated with this entity.
     *
     * @return a set of parent {@code JsonEntity} instances, or {@code null} if no parents are defined
     */
    Set<JsonEntity> getParents();

    /**
     * Adds a parent entity to this entity.
     *
     * @param parent the parent {@code JsonEntity} to add
     */
    void addParent(JsonEntity parent);

    /**
     * Adds a collection of parent entities to this entity.
     *
     * @param entities the collection of {@code JsonEntity} instances to add as parents
     */
    void addParents(Collection<JsonEntity> entities);

    /**
     * Retrieves the file name associated with this entity.
     *
     * @return the file name as a {@code String}
     */
    String getFileName();

    /**
     * Sets the file name for this entity.
     *
     * @param fileName the file name to set
     */
    void setFileName(String fileName);

    /**
     * Converts this entity to a JSON {@link ObjectNode} representation.
     * <p>
     * This method is used to serialize the entity into a format that can be converted to JSON.
     *
     * @return an {@link ObjectNode} representing the entity's state in JSON format
     */
    ObjectNode toJsonObject();
}
