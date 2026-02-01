package com.fitness.tests.ui.exerciseDetailPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.fitness.PageObjectWrapper;
import com.fitness.config.TestConfig;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestConfig.class)
@DisplayName("Тесты страницы детального просмотра упражнения")
@Epic("UI")
@Feature("Страница упражнения")
@Owner("Сафронов Иван Дмитриевич")
public class ExerciseDetailPageTests extends PageObjectWrapper {

    @BeforeEach
    void setUp() {
        exerciseDetailPageSteps.openPage("handstand");
    }

    @Test
    @DisplayName("Проверка заголовка упражнения")
    @Description("Проверяет отображение названия упражнения")
    @Story("Информация об упражнении")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldDisplayExerciseTitle() {
        exerciseDetailPageSteps
                .checkExerciseTitle("Стойка на руках");
    }

    @Test
    @DisplayName("Проверка изображения упражнения")
    @Description("Проверяет отображение изображения упражнения")
    @Story("Информация об упражнении")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldDisplayExerciseImage() {
        exerciseDetailPageSteps
                .checkExerciseImageVisible();
    }

    @Test
    @DisplayName("Проверка секции описания")
    @Description("Проверяет отображение секции с описанием упражнения")
    @Story("Секции контента")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldDisplayDescriptionSection() {
        exerciseDetailPageSteps
                .checkDescriptionSectionVisible();
    }

    @Test
    @DisplayName("Проверка секции техники выполнения")
    @Description("Проверяет отображение секции с техникой выполнения упражнения")
    @Story("Секции контента")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldDisplayTechniqueSection() {
        exerciseDetailPageSteps
                .checkTechniqueSectionVisible()
                .checkProgressionSectionVisible()
                .checkMusclesSectionVisible();
    }

    @Test
    @DisplayName("Возврат на главную страницу")
    @Description("Проверяет переход на главную страницу при клике на ссылку 'Вернуться'")
    @Story("Навигация")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldNavigateBackToMainPage() {
        exerciseDetailPageSteps
                .clickBackLink();

        // Ищем несуществующий элемент для демонстрации категории "Элемент не найден"
        $("[data-test-id='non-existent-element']").shouldBe(visible);
    }
}
