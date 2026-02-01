package com.fitness.locators.classes;

import lombok.Data;

@Data
public class ContactsPageLocators {
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

    // Contacts секция
    private String contactsSection;
    private String contactCardsContainer;

    // Карточка адреса
    private String contactCardAddress;
    private String contactTitleAddress;
    private String contactInfoAddress;

    // Карточка телефона
    private String contactCardPhone;
    private String contactTitlePhone;
    private String contactInfoPhone;

    // Карточка email
    private String contactCardEmail;
    private String contactTitleEmail;
    private String contactInfoEmail;

    // Форма обратной связи
    private String contactFormCard;
    private String contactFormTitle;
    private String contactForm;

    // Alert сообщения
    private String alertMessage;
    private String alertText;
    private String alertCloseButton;

    // Поле имени
    private String labelName;
    private String inputName;
    private String errorName;

    // Поле email
    private String labelEmail;
    private String inputEmail;
    private String errorEmail;

    // Поле телефона
    private String labelPhone;
    private String inputPhone;
    private String errorPhone;

    // Поле сообщения
    private String labelMessage;
    private String inputMessage;
    private String errorMessage;
    private String messageCounterContainer;
    private String messageCounter;

    // Кнопка отправки
    private String submitButton;
    private String submitText;

    // Footer
    private String footer;
    private String footerCopyright;
}
