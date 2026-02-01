package com.fitness.locators.classes;

import lombok.Data;

@Data
public class ExerciseDetailPageLocators {

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
    private String backLink;
    private String exerciseTitle;

    // Content секция
    private String contentSection;
    private String exerciseCard;
    private String exerciseImageContainer;
    private String exerciseImage;

    // Секция описания
    private String sectionDescription;
    private String sectionTitleDescription;
    private String sectionContentDescription;

    // Секция техники выполнения
    private String sectionTechnique;
    private String sectionTitleTechnique;
    private String sectionContentTechnique;

    // Секция прогрессии
    private String sectionProgression;
    private String sectionTitleProgression;
    private String sectionContentProgression;

    // Секция мышечных групп
    private String sectionMuscles;
    private String sectionTitleMuscles;
    private String sectionContentMuscles;

    // Секция типичных ошибок
    private String sectionMistakes;
    private String sectionTitleMistakes;
    private String sectionContentMistakes;

    // Секция советов
    private String sectionTips;
    private String sectionTitleTips;
    private String sectionContentTips;

    // Footer
    private String footer;
    private String footerCopyright;
}
