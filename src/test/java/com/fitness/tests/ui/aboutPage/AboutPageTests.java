package com.fitness.tests.ui.aboutPage;

import com.fitness.PageObjectWrapper;
import com.fitness.config.TestConfig;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestConfig.class)
@DisplayName("Тесты страницы 'О нас'")
@Epic("UI")
@Feature("Страница 'О нас'")
@Owner("Сафронов Иван Дмитриевич")
public class AboutPageTests extends PageObjectWrapper {

    @BeforeEach
    void setUp() {
        aboutPageSteps.openPage();
    }

    @Test
    @DisplayName("Проверка заголовка страницы 'О нас'")
    @Description("Проверяет корректное отображение hero-секции на странице 'О нас'")
    @Story("Hero секция")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldDisplayHeroSection() {
        aboutPageSteps
                .checkHeroTitle("О нас")
                .checkHeroSubtitle("Фитнес-центр для тех, кто хочет покорить силу тяжести");
    }

    @Test
    @DisplayName("Проверка карточки 'Наша миссия'")
    @Description("Проверяет отображение карточки с информацией о миссии")
    @Story("Информационные карточки")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldDisplayMissionCard() {
        aboutPageSteps
                .checkMissionCardVisible()
                .checkMissionTitle();
    }

    @Test
    @DisplayName("Проверка карточки 'Наша команда'")
    @Description("Проверяет отображение карточки с информацией о команде")
    @Story("Информационные карточки")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldDisplayTeamCard() {
        aboutPageSteps
                .checkTeamCardVisible()
                .checkTeamTitle();
    }

    @Test
    @DisplayName("Проверка блоков предложений")
    @Description("Проверяет отображение всех блоков в секции 'Что мы предлагаем'")
    @Story("Секция предложений")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldDisplayOfferBlocks() {
        aboutPageSteps
                .checkOffersCardVisible()
                .checkOfferStructuredLearningVisible()
                .checkOfferIndividualApproachVisible()
                .checkOfferSafetyVisible();
    }

    @Test
    @DisplayName("Навигация на страницу контактов через CTA кнопку")
    @Description("Проверяет переход на страницу контактов при клике на кнопку 'Связаться с нами'")
    @Story("Навигация")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldNavigateToContactsPageViaCta() {
        aboutPageSteps
                .clickCtaButton();

        contactsPageSteps
                .checkHeroTitle("Контакты");
    }
}
