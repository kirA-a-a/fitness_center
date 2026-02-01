package com.fitness.config.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SelenideConfig {

    @Getter
    @Value("${selenide.baseUrl:http://localhost:8080}")
    private String baseUrl;

    @Getter
    @Value("${selenide.browser:chrome}")
    private String browser;

    @PostConstruct
    public void configure() {
        Configuration.baseUrl = baseUrl;
        Configuration.browser = browser;

        // Allure listener для скриншотов
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
    }
}
