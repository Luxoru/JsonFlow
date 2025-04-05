package me.luxoru.jsonflow.api.entity;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Collection;
import java.util.Set;

public interface JsonEntity {

    Set<JsonEntity> getParents();

    void addParent(JsonEntity parent);

    void addParents(Collection<JsonEntity> entities);

    String getFileName();

    void setFileName(String fileName);

    ObjectNode toJsonObject();

}
