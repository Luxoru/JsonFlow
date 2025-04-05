package me.luxoru.jsonflow.api.manager;

import me.luxoru.jsonflow.api.entity.JsonEntity;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Interface for managing and interacting with {@link JsonEntity} instances.
 * <p>
 * This manager provides methods to read JSON data from files, retrieve entities by name,
 * and manage a collection of JSON entities.
 * <p>
 * Implementing this interface allows for efficient loading, caching, and retrieval of
 * JSON entities in a system.
 *
 * @author Luxoru
 * @version 1.0
 */
public interface JsonEntityManager {

    /**
     * Reads a JSON file and deserializes it into an entity of the specified class type.
     *
     * @param <T> the type of the {@code JsonEntity} to deserialize
     * @param jsonFileName the name of the JSON file to read
     * @param jsonClazz the class type to deserialize the JSON into
     * @return the deserialized {@code JsonEntity} instance
     * @throws FileNotFoundException if the file cannot be found
     */
    <T extends JsonEntity> T readFile(String jsonFileName, Class<T> jsonClazz) throws FileNotFoundException;

    /**
     * Reads a JSON file and deserializes it into an entity of the specified class type,
     * with an option to add the file to the cache.
     *
     * @param <T> the type of the {@code JsonEntity} to deserialize
     * @param jsonFileName the name of the JSON file to read
     * @param jsonClazz the class type to deserialize the JSON into
     * @param addFileToCache whether to add the file to the cache
     * @return the deserialized {@code JsonEntity} instance
     * @throws FileNotFoundException if the file cannot be found
     */
    <T extends JsonEntity> T readFile(String jsonFileName, Class<T> jsonClazz, boolean addFileToCache) throws FileNotFoundException;

    /**
     * Reads a JSON file and deserializes it into an entity of the specified class type.
     *
     * @param <T> the type of the {@code JsonEntity} to deserialize
     * @param jsonFile the file to read
     * @param jsonClazz the class type to deserialize the JSON into
     * @return the deserialized {@code JsonEntity} instance
     * @throws FileNotFoundException if the file cannot be found
     */
    <T extends JsonEntity> T readFile(File jsonFile, Class<T> jsonClazz) throws FileNotFoundException;

    /**
     * Reads a JSON file and deserializes it into an entity of the specified class type,
     * with an option to add the file to the cache.
     *
     * @param <T> the type of the {@code JsonEntity} to deserialize
     * @param jsonFile the file to read
     * @param jsonClazz the class type to deserialize the JSON into
     * @param addFileToCache whether to add the file to the cache
     * @return the deserialized {@code JsonEntity} instance
     * @throws FileNotFoundException if the file cannot be found
     */
    <T extends JsonEntity> T readFile(File jsonFile, Class<T> jsonClazz, boolean addFileToCache) throws FileNotFoundException;

    /**
     * Retrieves a {@code JsonEntity} by its name.
     *
     * @param name the name of the entity to retrieve
     * @return the {@code JsonEntity} associated with the specified name, or {@code null} if not found
     */
    JsonEntity getEntity(String name);

    /**
     * Retrieves a {@code JsonEntity} by its name, with type information.
     *
     * @param <T> the type of the {@code JsonEntity} to retrieve
     * @param name the name of the entity to retrieve
     * @param clazz the class type of the entity
     * @return the {@code JsonEntity} associated with the specified name and class type, or {@code null} if not found
     */
    <T extends JsonEntity> T getEntity(String name, Class<T> clazz);

    /**
     * Retrieves all the {@code JsonEntity} instances managed by this manager.
     *
     * @return a collection of all {@code JsonEntity} instances
     */
    Collection<JsonEntity> getEntities();

    /**
     * Retrieves the set of file names associated with the JSON entities managed by this manager.
     *
     * @return a set of file names
     */
    Set<String> getFileNames();

    /**
     * Retrieves a map of file names to {@code JsonEntity} instances.
     *
     * @return a map where the keys are file names and the values are the corresponding {@code JsonEntity} instances
     */
    Map<String, JsonEntity> getFileMap();
}
