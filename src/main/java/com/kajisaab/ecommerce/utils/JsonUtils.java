package com.kajisaab.ecommerce.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class JsonUtils<T> {
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Initialize ObjectMapper

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {
        // private constructor
    }

    public static <T> List<T> readJsonArrayFromInputStreamReader(InputStreamReader inputStreamReader, Class<T[]> clazz) throws IOException {
        return Arrays.asList(objectMapper.readValue(inputStreamReader, clazz));
    }

    public static String toJson(Object obj) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).registerModule(new JavaTimeModule());
        try {
            if (obj == null)
                return null;
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static <T> Optional<T> fromJson(String json, Class<T> clazz) {
        try {
            T obj = objectMapper.readValue(json, clazz);
            return Optional.of(obj);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static <T> boolean isJsonObject(String value) {
        try {
            return value != null && new ObjectMapper().readTree(value).isObject();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public static boolean hasNestedJsonObject(JsonNode node) {
        // Iterate through the field of the JSON object.
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = node.get(fieldName);

            // Check if the field value is an object
            if (fieldValue.isObject()) {
                return true;
            }
        }
        return false;
    }

    public static String getNestedJsonObjectKey(JsonNode node) {
        // Iterate through the field of the JSON object.
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode fieldValue = node.get(fieldName);

            // Check if the field value is an object
            if (fieldValue.isObject()) {
                return fieldName;
            }
        }
        return null;
    }

}
