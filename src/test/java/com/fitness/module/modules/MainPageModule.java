package com.fitness.module.modules;

import com.fitness.locators.classes.MainPageLocators;
import com.fitness.module.component.BaseComponent;
import com.fitness.module.component.Button;
import com.fitness.module.component.Div;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MainPageModule extends BaseComponent {

    private final Div navBar;
    private final Div navLinkHome;
    private final Div navLinkAbout;
    private final Div navLinkContacts;
    private final Div heroTitle;
    private final Div heroSubtitle;
    private final Div featuresSection;
    private final Div featureCardElements;
    private final Div featureCardTrainers;
    private final Div featureCardLearning;
    private final Div exerciseCardHandstand;
    private final Div exerciseCardSpichag;
    private final Button ctaButton;
    private final Div footer;

    public MainPageModule(MainPageLocators locators) {
        this.navBar = new Div("Навигационная панель", locators.getNavbar());
        this.navLinkHome = new Div("Ссылка 'Главная'", locators.getNavLinkHome());
        this.navLinkAbout = new Div("Ссылка 'О нас'", locators.getNavLinkAbout());
        this.navLinkContacts = new Div("Ссылка 'Контакты'", locators.getNavLinkContacts());
        this.heroTitle = new Div("Заголовок главной страницы", locators.getHeroTitle());
        this.heroSubtitle = new Div("Подзаголовок главной страницы", locators.getHeroSubtitle());
        this.featuresSection = new Div("Секция особенностей", locators.getFeaturesSection());
        this.featureCardElements = new Div("Карточка 'Сложные элементы'", locators.getFeatureCardElements());
        this.featureCardTrainers = new Div("Карточка 'Опытные тренеры'", locators.getFeatureCardTrainers());
        this.featureCardLearning = new Div("Карточка 'Структурированное обучение'", locators.getFeatureCardLearning());
        this.exerciseCardHandstand = new Div("Карточка 'Стойка на руках'", locators.getExerciseCardHandstand());
        this.exerciseCardSpichag = new Div("Карточка 'Спичаг'", locators.getExerciseCardSpichag());
        this.ctaButton = new Button("Кнопка 'Связаться с нами'", locators.getCtaButton());
        this.footer = new Div("Футер", locators.getFooter());
    }
}
