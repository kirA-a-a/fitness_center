package com.fitness.locators.classes;

import lombok.Data;

@Data
public class AboutPageLocators {

    // Навигация
    private String navbar;
    private String navbarBrand;
    private String navbarToggler;
    private String navbarNav;
    private String navLinkHome;
    private String navLinkAbout;
    private String navLinkContacts;

    // Hero секция
    private String heroSection;
    private String heroTitle;
    private String heroSubtitle;

    // About секция
    private String aboutSection;

    // Карточка миссии
    private String aboutCardMission;
    private String aboutTitleMission;
    private String aboutDescriptionMission;

    // Карточка команды
    private String aboutCardTeam;
    private String aboutTitleTeam;
    private String aboutDescriptionTeam;

    // Карточка предложений
    private String aboutCardOffers;
    private String aboutTitleOffers;

    // Предложения - Структурированное обучение
    private String offerStructuredLearning;
    private String offerTitleStructuredLearning;
    private String offerDescriptionStructuredLearning;

    // Предложения - Индивидуальный подход
    private String offerIndividualApproach;
    private String offerTitleIndividualApproach;
    private String offerDescriptionIndividualApproach;

    // Предложения - Безопасность
    private String offerSafety;
    private String offerTitleSafety;
    private String offerDescriptionSafety;

    // CTA секция
    private String ctaSection;
    private String ctaTitle;
    private String ctaDescription;
    private String ctaButton;

    // Footer
    private String footer;
    private String footerCopyright;
}
