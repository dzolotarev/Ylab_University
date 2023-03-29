package io.ylab.intensive.lesson04.eventsourcing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Denis Zolotarev
 */
public class Command {

    public static final String SAVE = "save";
    public static final String DELETE = "delete";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String pack(String command, Person person) throws JsonProcessingException {
        Map<String, Person> mapPerson = new HashMap<>();
        mapPerson.put(command, person);
        return objectMapper.writeValueAsString(mapPerson);
    }

    public Map<String, Person> unpack(String json) throws JsonProcessingException {
        TypeReference<Map<String, Person>> mapType = new TypeReference<>() {
        };
        return objectMapper.readValue(json, mapType);
    }
}

