package me.luxoru.jsonflow.api.annotation;

import me.luxoru.jsonflow.api.serialize.JsonFlowDeserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FlowField {

    String fieldName();

    Class<? extends JsonFlowDeserializer> serializer()  default JsonFlowDeserializer.class;

}
