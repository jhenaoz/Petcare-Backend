package com.petcare.config;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.petcare.domain.util.CustomDateTimeDeserializer;
import com.petcare.domain.util.CustomDateTimeSerializer;
import com.petcare.domain.util.CustomLocalDateSerializer;
import com.petcare.domain.util.ISO8601LocalDateDeserializer;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Configuration
public class JacksonConfiguration {

    @Bean
    public JodaModule jacksonJodaModule() {
        JodaModule module = new JodaModule();
        module.addSerializer(DateTime.class, new CustomDateTimeSerializer());
        module.addDeserializer(DateTime.class, new CustomDateTimeDeserializer());
        module.addSerializer(LocalDate.class, new CustomLocalDateSerializer());
        module.addDeserializer(LocalDate.class, new ISO8601LocalDateDeserializer());
        return module;
    }
}
