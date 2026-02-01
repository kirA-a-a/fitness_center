package com.fitness.module.modules;

import com.fitness.locators.classes.ContactsPageLocators;
import com.fitness.module.component.BaseComponent;
import com.fitness.module.component.Button;
import com.fitness.module.component.Div;
import com.fitness.module.component.Input;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ContactsPageModule extends BaseComponent {

    private final Div heroTitle;
    private final Div heroSubtitle;
    private final Div contactCardAddress;
    private final Div contactCardPhone;
    private final Div contactCardEmail;
    private final Div contactFormTitle;
    private final Input inputName;
    private final Input inputEmail;
    private final Input inputPhone;
    private final Input inputMessage;
    private final Button submitButton;
    private final Div alertMessage;
    private final Div errorName;
    private final Div errorEmail;
    private final Div footer;

    public ContactsPageModule(ContactsPageLocators locators) {
        this.heroTitle = new Div("Заголовок страницы контактов", locators.getHeroTitle());
        this.heroSubtitle = new Div("Подзаголовок страницы контактов", locators.getHeroSubtitle());
        this.contactCardAddress = new Div("Карточка с адресом", locators.getContactCardAddress());
        this.contactCardPhone = new Div("Карточка с телефоном", locators.getContactCardPhone());
        this.contactCardEmail = new Div("Карточка с email", locators.getContactCardEmail());
        this.contactFormTitle = new Div("Заголовок формы", locators.getContactFormTitle());
        this.inputName = new Input("Поле 'Имя'", locators.getInputName());
        this.inputEmail = new Input("Поле 'Email'", locators.getInputEmail());
        this.inputPhone = new Input("Поле 'Телефон'", locators.getInputPhone());
        this.inputMessage = new Input("Поле 'Сообщение'", locators.getInputMessage());
        this.submitButton = new Button("Кнопка 'Отправить'", locators.getSubmitButton());
        this.alertMessage = new Div("Сообщение об успехе/ошибке", locators.getAlertMessage());
        this.errorName = new Div("Ошибка поля 'Имя'", locators.getErrorName());
        this.errorEmail = new Div("Ошибка поля 'Email'", locators.getErrorEmail());
        this.footer = new Div("Футер", locators.getFooter());
    }
}
