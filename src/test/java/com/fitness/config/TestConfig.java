package com.fitness.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для UI тестов.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.fitness.config.ui",
        "com.fitness.locators",
        "com.fitness.module",
        "com.fitness.tests.ui.steps"
})
public class TestConfig {
}
