package me.luxoru.jsonflow.api.annotation;

import me.luxoru.jsonflow.api.serialize.JsonFlowConversionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowParameter {

    String value();
    Class<? extends JsonFlowConversionHandler> serializer()  default JsonFlowConversionHandler.class;

}
