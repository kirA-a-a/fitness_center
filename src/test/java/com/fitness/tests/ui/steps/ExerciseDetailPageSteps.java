package com.fitness.tests.ui.steps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

import com.fitness.config.ui.SelenideConfig;
import com.fitness.module.modules.ExerciseDetailPageModule;
import io.qameta.allure.Allure;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ExerciseDetailPageSteps {

    private final ExerciseDetailPageModule exerciseDetailPageModule;
    private final SelenideConfig selenideConfig;

    public ExerciseDetailPageSteps(ExerciseDetailPageModule exerciseDetailPageModule, SelenideConfig selenideConfig) {
        this.exerciseDetailPageModule = exerciseDetailPageModule;
        this.selenideConfig = selenideConfig;
    }

    public ExerciseDetailPageSteps openPage(String slug) {
        String url = selenideConfig.getBaseUrl() + "/elements/" + slug;
        return Allure.step("Открытие страницы упражнения: " + url, () -> {
            open(url);
            return this;
        });
    }

    public ExerciseDetailPageSteps checkExerciseTitle(String expectedText) {
        return Allure.step("Проверка заголовка упражнения: " + expectedText, () -> {
            exerciseDetailPageModule.getExerciseTitle().getElement().shouldHave(text(expectedText));
            return this;
        });
    }

    public ExerciseDetailPageSteps checkExerciseImageVisible() {
        return Allure.step("Проверка отображения изображения упражнения", () -> {
            exerciseDetailPageModule.getExerciseImage().shouldBeVisible();
            return this;
        });
    }

    public ExerciseDetailPageSteps checkDescriptionSectionVisible() {
        return Allure.step("Проверка отображения секции 'Описание'", () -> {
            exerciseDetailPageModule.getSectionDescription().shouldBeVisible();
            return this;
        });
    }

    public ExerciseDetailPageSteps checkTechniqueSectionVisible() {
        return Allure.step("Проверка отображения секции 'Техника выполнения'", () -> {
            exerciseDetailPageModule.getSectionTechnique().shouldBeVisible();
            return this;
        });
    }

    public ExerciseDetailPageSteps checkProgressionSectionVisible() {
        return Allure.step("Проверка отображения секции 'Прогрессия'", () -> {
            exerciseDetailPageModule.getSectionProgression().shouldBeVisible();
            return this;
        });
    }

    public ExerciseDetailPageSteps checkMusclesSectionVisible() {
        return Allure.step("Проверка отображения секции 'Мышечные группы'", () -> {
            exerciseDetailPageModule.getSectionMuscles().shouldBeVisible();
            return this;
        });
    }

    public ExerciseDetailPageSteps clickBackLink() {
        return Allure.step("Клик на ссылку 'Вернуться на главную'", () -> {
            exerciseDetailPageModule.getBackLink().click();
            return this;
        });
    }

    public ExerciseDetailPageSteps checkFooterVisible() {
        return Allure.step("Проверка отображения футера", () -> {
            exerciseDetailPageModule.getFooter().shouldBeVisible();
            return this;
        });
    }
}
