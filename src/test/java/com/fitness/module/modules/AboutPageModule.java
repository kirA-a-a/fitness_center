package com.fitness.module.modules;

import com.fitness.locators.classes.AboutPageLocators;
import com.fitness.module.component.BaseComponent;
import com.fitness.module.component.Button;
import com.fitness.module.component.Div;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AboutPageModule extends BaseComponent {

    private final Div heroTitle;
    private final Div heroSubtitle;
    private final Div aboutCardMission;
    private final Div aboutTitleMission;
    private final Div aboutCardTeam;
    private final Div aboutTitleTeam;
    private final Div aboutCardOffers;
    private final Div offerStructuredLearning;
    private final Div offerIndividualApproach;
    private final Div offerSafety;
    private final Div ctaTitle;
    private final Button ctaButton;
    private final Div footer;

    public AboutPageModule(AboutPageLocators locators) {
        this.heroTitle = new Div("Заголовок страницы 'О нас'", locators.getHeroTitle());
        this.heroSubtitle = new Div("Подзаголовок страницы 'О нас'", locators.getHeroSubtitle());
        this.aboutCardMission = new Div("Карточка 'Наша миссия'", locators.getAboutCardMission());
        this.aboutTitleMission = new Div("Заголовок 'Наша миссия'", locators.getAboutTitleMission());
        this.aboutCardTeam = new Div("Карточка 'Наша команда'", locators.getAboutCardTeam());
        this.aboutTitleTeam = new Div("Заголовок 'Наша команда'", locators.getAboutTitleTeam());
        this.aboutCardOffers = new Div("Карточка 'Что мы предлагаем'", locators.getAboutCardOffers());
        this.offerStructuredLearning = new Div("Блок 'Структурированное обучение'", locators.getOfferStructuredLearning());
        this.offerIndividualApproach = new Div("Блок 'Индивидуальный подход'", locators.getOfferIndividualApproach());
        this.offerSafety = new Div("Блок 'Безопасность'", locators.getOfferSafety());
        this.ctaTitle = new Div("Заголовок CTA секции", locators.getCtaTitle());
        this.ctaButton = new Button("Кнопка 'Связаться с нами'", locators.getCtaButton());
        this.footer = new Div("Футер", locators.getFooter());
    }
}
