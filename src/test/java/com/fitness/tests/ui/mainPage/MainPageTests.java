package com.fitness.tests.ui.mainPage;

import com.fitness.PageObjectWrapper;
import com.fitness.config.TestConfig;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestConfig.class)
@DisplayName("Тесты главной страницы")
@Epic("UI")
@Feature("Главная страница")
@Owner("Сафронов Иван Дмитриевич")
public class MainPageTests extends PageObjectWrapper {

    @BeforeEach
    void setUp() {
        mainPageSteps.openPage();
    }

    @Test
    @DisplayName("Проверка отображения навигационной панели")
    @Description("Проверяет корректное отображение навигационной панели на главной странице")
    @Story("Навигация")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("smoke")
    void shouldDisplayNavigationBar() {
        mainPageSteps
                .checkNavBarVisible()
                .checkFooterVisible();
    }

    @Test
    @DisplayName("Проверка заголовка и подзаголовка главной страницы")
    @Description("Проверяет корректное отображение hero-секции с заголовком и подзаголовком")
    @Story("Hero секция")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldDisplayHeroSection() {
        mainPageSteps
                .checkHeroTitle("Гравитация нас не удержит")
                .checkHeroSubtitle("Фитнес-центр для тех, кто хочет покорить силу тяжести");
    }

    @Test
    @DisplayName("Проверка отображения карточек особенностей")
    @Description("Проверяет наличие всех трех карточек в секции особенностей")
    @Story("Секция особенностей")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldDisplayFeatureCards() {
        mainPageSteps
                .checkFeaturesSectionVisible()
                .checkFeatureCardElementsVisible()
                .checkFeatureCardTrainersVisible()
                .checkFeatureCardLearningVisible();
    }

    @Test
    @DisplayName("Проверка отображения карточки упражнения")
    @Description("Проверяет отображение карточки упражнения 'Стойка на руках'")
    @Story("Карточки упражнений")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldDisplayExerciseCard() {
        mainPageSteps
                .checkExerciseCardHandstandVisible();
    }

    @Test
    @DisplayName("Навигация на страницу 'О нас'")
    @Description("Проверяет переход на страницу 'О нас' через навигационную панель")
    @Story("Навигация")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldNavigateToAboutPage() {
        mainPageSteps
                .checkNavBarVisible()
                .clickNavLinkAbout();

        aboutPageSteps
                .checkHeroTitle("О нас");
    }
}
