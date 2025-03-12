package cn.codependency.framework.puzzle.common.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
public class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static final ObjectMapper MAPPER_WITH_CLASS = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.setDateFormat(new JacksonDateFormat());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        MAPPER.enable(new JsonGenerator.Feature[]{com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN,
                com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS});

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        MAPPER.registerModule(javaTimeModule);


        MAPPER_WITH_CLASS.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER_WITH_CLASS.setDateFormat(new JacksonDateFormat());
        MAPPER_WITH_CLASS.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER_WITH_CLASS.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        MAPPER_WITH_CLASS.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        MAPPER_WITH_CLASS.enable(new JsonGenerator.Feature[]{com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN});
        MAPPER_WITH_CLASS.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "$CLASS");

        JavaTimeModule javaTimeModule2 = new JavaTimeModule();
        javaTimeModule2.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule2.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule2.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule2.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule2.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule2.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        MAPPER_WITH_CLASS.registerModule(javaTimeModule2);


    }


    public static String toJson(Object o) {
        return toJson(o, false);
    }


    public static String toJson(Object o, boolean withClass) {
        if (Objects.isNull(o)) {
            return null;
        }
        try {
            if (withClass) {
                return MAPPER_WITH_CLASS.writeValueAsString(o);
            } else {
                return MAPPER.writeValueAsString(o);
            }

        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    public static <R extends T, T> R toObject(String json, Class<T> clazz) {
        return toObject(json, clazz, false);
    }

    public static <R extends T, T> R toObject(String json, Class<T> clazz, boolean withClass) {
        if (Objects.isNull(json)) {
            return null;
        }
        try {
            if (withClass) {
                return (R) MAPPER_WITH_CLASS.readValue(json, clazz);
            }
            return (R) MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        return toList(json, clazz, false);
    }

    public static <T> List<T> toList(String json, Class<T> clazz, boolean withClass) {
        if (Objects.isNull(json)) {
            return null;
        }
        try {
            if (withClass) {
                return MAPPER_WITH_CLASS.readValue(json, MAPPER_WITH_CLASS.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @SneakyThrows
    public static byte[] serialize(Object obj) {
        return MAPPER_WITH_CLASS.writeValueAsBytes(obj);
    }

    @SneakyThrows
    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        return MAPPER_WITH_CLASS.readValue(data, clazz);
    }

    public static <T> T copy(T obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        return (T) toObject(toJson(obj, true), obj.getClass(), true);
    }

}
