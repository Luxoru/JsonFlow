package me.luxoru.jsonflow.api.annotation;

import com.fasterxml.jackson.databind.JsonDeserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FlowSerializable {

    Class<? extends JsonDeserializer> using()
            default JsonDeserializer.None.class;

}
