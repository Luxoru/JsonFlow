package me.luxoru.jsonflow.core.util.databind;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

public class FlowObjectMapper extends ObjectMapper {

    private final Set<String> registeredModules = new HashSet<>();
    private static FlowObjectMapper INSTANCE;

    @Override
    public ObjectMapper registerModule(Module module) {
        if (registeredModules.contains(module.getModuleName())) {
            System.out.println("Module " + module.getModuleName() + " is already registered.");
            return this;
        }
        registeredModules.add(module.getModuleName());
        return super.registerModule(module);
    }

    public static FlowObjectMapper instance(){
        if(INSTANCE != null)return INSTANCE;

        INSTANCE = new FlowObjectMapper();

        return INSTANCE;
    }

}
