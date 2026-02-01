package com.fitness.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для DB тестов.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.fitness.tests.db.steps"
})
public class DbTestConfig {
}
