package com.fitness.tests.ui.steps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

import com.fitness.config.ui.SelenideConfig;
import com.fitness.module.modules.AboutPageModule;
import io.qameta.allure.Allure;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AboutPageSteps {

    private final AboutPageModule aboutPageModule;
    private final SelenideConfig selenideConfig;

    public AboutPageSteps(AboutPageModule aboutPageModule, SelenideConfig selenideConfig) {
        this.aboutPageModule = aboutPageModule;
        this.selenideConfig = selenideConfig;
    }

    public AboutPageSteps openPage() {
        return Allure.step("Открытие страницы 'О нас': " + selenideConfig.getBaseUrl() + "/about", () -> {
            open(selenideConfig.getBaseUrl() + "/about");
            return this;
        });
    }

    public AboutPageSteps checkHeroTitle(String expectedText) {
        return Allure.step("Проверка заголовка страницы 'О нас': " + expectedText, () -> {
            aboutPageModule.getHeroTitle().getElement().shouldHave(text(expectedText));
            return this;
        });
    }

    public AboutPageSteps checkHeroSubtitle(String expectedText) {
        return Allure.step("Проверка подзаголовка страницы 'О нас': " + expectedText, () -> {
            aboutPageModule.getHeroSubtitle().getElement().shouldHave(text(expectedText));
            return this;
        });
    }

    public AboutPageSteps checkMissionCardVisible() {
        return Allure.step("Проверка отображения карточки 'Наша миссия'", () -> {
            aboutPageModule.getAboutCardMission().shouldBeVisible();
            return this;
        });
    }

    public AboutPageSteps checkMissionTitle() {
        return Allure.step("Проверка заголовка карточки 'Наша миссия'", () -> {
            aboutPageModule.getAboutTitleMission().getElement().shouldHave(text("Наша миссия"));
            return this;
        });
    }

    public AboutPageSteps checkTeamCardVisible() {
        return Allure.step("Проверка отображения карточки 'Наша команда'", () -> {
            aboutPageModule.getAboutCardTeam().shouldBeVisible();
            return this;
        });
    }

    public AboutPageSteps checkTeamTitle() {
        return Allure.step("Проверка заголовка карточки 'Наша команда'", () -> {
            aboutPageModule.getAboutTitleTeam().getElement().shouldHave(text("Наша команда"));
            return this;
        });
    }

    public AboutPageSteps checkOffersCardVisible() {
        return Allure.step("Проверка отображения карточки 'Что мы предлагаем'", () -> {
            aboutPageModule.getAboutCardOffers().shouldBeVisible();
            return this;
        });
    }

    public AboutPageSteps checkOfferStructuredLearningVisible() {
        return Allure.step("Проверка отображения блока 'Структурированное обучение'", () -> {
            aboutPageModule.getOfferStructuredLearning().shouldBeVisible();
            return this;
        });
    }

    public AboutPageSteps checkOfferIndividualApproachVisible() {
        return Allure.step("Проверка отображения блока 'Индивидуальный подход'", () -> {
            aboutPageModule.getOfferIndividualApproach().shouldBeVisible();
            return this;
        });
    }

    public AboutPageSteps checkOfferSafetyVisible() {
        return Allure.step("Проверка отображения блока 'Безопасность'", () -> {
            aboutPageModule.getOfferSafety().shouldBeVisible();
            return this;
        });
    }

    public AboutPageSteps clickCtaButton() {
        return Allure.step("Клик на кнопку 'Связаться с нами'", () -> {
            aboutPageModule.getCtaButton().click();
            return this;
        });
    }

    public AboutPageSteps checkFooterVisible() {
        return Allure.step("Проверка отображения футера", () -> {
            aboutPageModule.getFooter().shouldBeVisible();
            return this;
        });
    }
}
