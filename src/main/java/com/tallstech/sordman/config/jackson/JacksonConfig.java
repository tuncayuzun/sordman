package com.tallstech.sordman.config.jackson;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.tallstech.sordman.constant.SordmanConstants.SORDMAN_OBJECT_MAPPER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;


@Configuration
public class JacksonConfig {

    @Bean(name = SORDMAN_OBJECT_MAPPER)
    @Primary
    ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return newObjectMapper(builder);
    }

    public static ObjectMapper newObjectMapper(Jackson2ObjectMapperBuilder builder) {

        var javaTimeModule = new SimpleModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer())
                .addDeserializer(LocalDate.class, new LocalDateDeserialization())
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer())
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserialization())
                .addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer())
                .addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserialization());

        return builder
                .serializationInclusion(NON_NULL)
                .failOnEmptyBeans(false)
                .failOnUnknownProperties(false)
                .featuresToEnable(
                        MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,
                        DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                        JsonParser.Feature.ALLOW_SINGLE_QUOTES,
                        JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
                        JsonReadFeature.ALLOW_MISSING_VALUES.mappedFeature()
                )
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        SerializationFeature.WRAP_ROOT_VALUE

                )
                .visibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY)
                .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .build().registerModule(javaTimeModule);
    }
}
