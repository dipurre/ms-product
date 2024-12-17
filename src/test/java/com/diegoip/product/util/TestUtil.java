package com.diegoip.product.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class TestUtil {

    /**
     * Method to parseJsonToList.
     */
    public static <T> List<T> parseJsonToList(File file, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType javaType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, clazz);
        return objectMapper.readValue(Files.readString(file.toPath(), StandardCharsets.UTF_8),
                javaType);
    }

    public static <T> List<T> parseJsonToList(String fileName, Class<T> clazz) {
        try {
            return parseJsonToList(ResourceUtils
                    .getFile("classpath:mocks/" + fileName), clazz);
        } catch (IOException e) {
            throw new RuntimeException("error in parseJsonToList: " + fileName);
        }
    }

    public static <T> T parseJsonToObject(File file, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper.readValue(Files.readString(file.toPath(), StandardCharsets.UTF_8),
                clazz);
    }

    public static <T> T parseJsonToObject(String fileName, Class<T> clazz) {
        try {
            return parseJsonToObject(ResourceUtils
                    .getFile("classpath:mocks/" + fileName), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error in parseJsonToObject: " + fileName);
        }
    }


}
