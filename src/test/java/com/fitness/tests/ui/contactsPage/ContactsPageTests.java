package com.fitness.tests.ui.contactsPage;

import com.fitness.PageObjectWrapper;
import com.fitness.config.TestConfig;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestConfig.class)
@DisplayName("Тесты страницы контактов")
@Epic("UI")
@Feature("Страница контактов")
@Owner("Сафронов Иван Дмитриевич")
public class ContactsPageTests extends PageObjectWrapper {

    @BeforeEach
    void setUp() {
        contactsPageSteps.openPage();
    }

    @Test
    @DisplayName("Проверка заголовка страницы контактов")
    @Description("Проверяет корректное отображение hero-секции на странице контактов")
    @Story("Hero секция")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldDisplayHeroSection() {
        contactsPageSteps
                .checkHeroTitle("Контакты")
                .checkHeroSubtitleVisible();
    }

    @Test
    @DisplayName("Проверка отображения контактных карточек")
    @Description("Проверяет наличие всех карточек с контактной информацией")
    @Story("Контактная информация")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldDisplayContactCards() {
        contactsPageSteps
                .checkAddressCardVisible()
                .checkPhoneCardVisible()
                .checkEmailCardVisible();
    }

    @Test
    @DisplayName("Проверка отображения формы обратной связи")
    @Description("Проверяет наличие формы для отправки сообщения")
    @Story("Форма обратной связи")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldDisplayContactForm() {
        contactsPageSteps
                .checkContactFormVisible()
                .checkFooterVisible();
    }

    @Test
    @DisplayName("Заполнение формы обратной связи")
    @Description("Проверяет возможность заполнения всех полей формы")
    @Story("Форма обратной связи")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldFillContactForm() {
        contactsPageSteps
                .enterName("Иван Иванов")
                .enterEmail("ivan@example.com")
                .enterPhone("+7 999 123 45 67")
                .enterMessage("Тестовое сообщение для проверки формы")
                .checkHeroTitle("Несуществующий заголовок для демонстрации видео");
    }

    @Test
    @DisplayName("Валидация пустой формы")
    @Description("Проверяет отображение ошибок валидации при отправке пустой формы")
    @Story("Валидация формы")
    @Severity(SeverityLevel.NORMAL)
    @Tag("functional")
    void shouldShowValidationErrorsOnEmptyForm() {
        contactsPageSteps
                .clickSubmitButton()
                .checkNameErrorVisible()
                .checkEmailErrorVisible();
    }
}
