package me.luxoru.jsonflow.api.entity;

public interface JsonEntity {

    default JsonEntity getParent() {
        return null;
    }

    void setParent(JsonEntity parent);

    String getFileName();

    void setFileName(String fileName);

}
