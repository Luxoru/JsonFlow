package me.luxoru.jsonflow.api.annotation;

import me.luxoru.jsonflow.api.serialize.JsonNodeConversionHandler;

public @interface NodeSerializable {

    Class<? extends JsonNodeConversionHandler> serializer();

}
