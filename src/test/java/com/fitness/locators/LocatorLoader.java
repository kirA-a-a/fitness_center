package com.fitness.locators;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fitness.locators.classes.AboutPageLocators;
import com.fitness.locators.classes.ContactsPageLocators;
import com.fitness.locators.classes.ExerciseDetailPageLocators;
import com.fitness.locators.classes.MainPageLocators;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

import static java.lang.String.format;

/**
 * Конфигурация для загрузки локаторов из YAML файлов.
 */
@Configuration
public class LocatorLoader {

    private static final String LOCATORS_PATH = "src/test/java/com/fitness/locators/yml";
    private final ObjectMapper mapper;

    public LocatorLoader() {
        this.mapper = new ObjectMapper(new YAMLFactory());
        this.mapper.setVisibility(
                mapper.getSerializationConfig()
                        .getDefaultVisibilityChecker()
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
        );
    }

    @Bean
    public MainPageLocators mainPageLocators() {
        return readYml(MainPageLocators.class);
    }

    @Bean
    public AboutPageLocators aboutPageLocators() {
        return readYml(AboutPageLocators.class);
    }

    @Bean
    public ContactsPageLocators contactsPageLocators() {
        return readYml(ContactsPageLocators.class);
    }

    @Bean
    public ExerciseDetailPageLocators exerciseDetailPageLocators() {
        return readYml(ExerciseDetailPageLocators.class);
    }

    @SneakyThrows
    private <T> T readYml(Class<T> valueType) {
        String fileName = valueType.getSimpleName().replace("Locators", "");
        return mapper.readValue(
                new File(format("%s/%s.yml", LOCATORS_PATH, fileName)),
                valueType
        );
    }
}
