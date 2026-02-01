package com.fitness.tests.ui.steps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

import com.fitness.config.ui.SelenideConfig;
import com.fitness.module.modules.MainPageModule;
import io.qameta.allure.Allure;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MainPageSteps {

    private final MainPageModule mainPageModule;
    private final SelenideConfig selenideConfig;

    public MainPageSteps(MainPageModule mainPageModule, SelenideConfig selenideConfig) {
        this.mainPageModule = mainPageModule;
        this.selenideConfig = selenideConfig;
    }

    public MainPageSteps openPage() {
        return Allure.step("Открытие главной страницы: " + selenideConfig.getBaseUrl(), () -> {
            open(selenideConfig.getBaseUrl());
            return this;
        });
    }

    public MainPageSteps checkNavBarVisible() {
        return Allure.step("Проверка отображения навигационной панели", () -> {
            mainPageModule.getNavBar().shouldBeVisible();
            return this;
        });
    }

    public MainPageSteps checkHeroTitle(String expectedText) {
        return Allure.step("Проверка заголовка главной страницы: " + expectedText, () -> {
            mainPageModule.getHeroTitle().getElement().shouldHave(text(expectedText));
            return this;
        });
    }

    public MainPageSteps checkHeroSubtitle(String expectedText) {
        return Allure.step("Проверка подзаголовка главной страницы: " + expectedText, () -> {
            mainPageModule.getHeroSubtitle().getElement().shouldHave(text(expectedText));
            return this;
        });
    }

    public MainPageSteps checkFeaturesSectionVisible() {
        return Allure.step("Проверка отображения секции особенностей", () -> {
            mainPageModule.getFeaturesSection().shouldBeVisible();
            return this;
        });
    }

    public MainPageSteps checkFeatureCardElementsVisible() {
        return Allure.step("Проверка отображения карточки 'Сложные элементы'", () -> {
            mainPageModule.getFeatureCardElements().shouldBeVisible();
            return this;
        });
    }

    public MainPageSteps checkFeatureCardTrainersVisible() {
        return Allure.step("Проверка отображения карточки 'Опытные тренеры'", () -> {
            mainPageModule.getFeatureCardTrainers().shouldBeVisible();
            return this;
        });
    }

    public MainPageSteps checkFeatureCardLearningVisible() {
        return Allure.step("Проверка отображения карточки 'Структурированное обучение'", () -> {
            mainPageModule.getFeatureCardLearning().shouldBeVisible();
            return this;
        });
    }

    public MainPageSteps checkExerciseCardHandstandVisible() {
        return Allure.step("Проверка отображения карточки упражнения 'Стойка на руках'", () -> {
            mainPageModule.getExerciseCardHandstand().shouldBeVisible();
            return this;
        });
    }

    public MainPageSteps clickExerciseCardHandstand() {
        return Allure.step("Клик на карточку упражнения 'Стойка на руках'", () -> {
            mainPageModule.getExerciseCardHandstand().click();
            return this;
        });
    }

    public MainPageSteps clickNavLinkAbout() {
        return Allure.step("Клик на ссылку 'О нас' в навигации", () -> {
            mainPageModule.getNavLinkAbout().click();
            return this;
        });
    }

    public MainPageSteps clickNavLinkContacts() {
        return Allure.step("Клик на ссылку 'Контакты' в навигации", () -> {
            mainPageModule.getNavLinkContacts().click();
            return this;
        });
    }

    public MainPageSteps clickCtaButton() {
        return Allure.step("Клик на кнопку 'Связаться с нами'", () -> {
            mainPageModule.getCtaButton().click();
            return this;
        });
    }

    public MainPageSteps checkFooterVisible() {
        return Allure.step("Проверка отображения футера", () -> {
            mainPageModule.getFooter().shouldBeVisible();
            return this;
        });
    }
}
