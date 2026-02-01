package com.fitness.tests.ui.steps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

import com.fitness.config.ui.SelenideConfig;
import com.fitness.module.modules.ContactsPageModule;
import io.qameta.allure.Allure;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ContactsPageSteps {

    private final ContactsPageModule contactsPageModule;
    private final SelenideConfig selenideConfig;

    public ContactsPageSteps(ContactsPageModule contactsPageModule, SelenideConfig selenideConfig) {
        this.contactsPageModule = contactsPageModule;
        this.selenideConfig = selenideConfig;
    }

    public ContactsPageSteps openPage() {
        return Allure.step("Открытие страницы контактов: " + selenideConfig.getBaseUrl() + "/contacts", () -> {
            open(selenideConfig.getBaseUrl() + "/contacts");
            return this;
        });
    }

    public ContactsPageSteps checkHeroTitle(String expectedText) {
        return Allure.step("Проверка заголовка страницы контактов: " + expectedText, () -> {
            contactsPageModule.getHeroTitle().getElement().shouldHave(text(expectedText));
            return this;
        });
    }

    public ContactsPageSteps checkHeroSubtitleVisible() {
        return Allure.step("Проверка подзаголовка страницы контактов", () -> {
            contactsPageModule.getHeroSubtitle().shouldBeVisible();
            return this;
        });
    }

    public ContactsPageSteps checkAddressCardVisible() {
        return Allure.step("Проверка отображения карточки с адресом", () -> {
            contactsPageModule.getContactCardAddress().shouldBeVisible();
            return this;
        });
    }

    public ContactsPageSteps checkPhoneCardVisible() {
        return Allure.step("Проверка отображения карточки с телефоном", () -> {
            contactsPageModule.getContactCardPhone().shouldBeVisible();
            return this;
        });
    }

    public ContactsPageSteps checkEmailCardVisible() {
        return Allure.step("Проверка отображения карточки с email", () -> {
            contactsPageModule.getContactCardEmail().shouldBeVisible();
            return this;
        });
    }

    public ContactsPageSteps checkContactFormVisible() {
        return Allure.step("Проверка отображения формы обратной связи", () -> {
            contactsPageModule.getContactFormTitle().shouldBeVisible();
            return this;
        });
    }

    public ContactsPageSteps enterName(String name) {
        return Allure.step("Ввод имени: " + name, () -> {
            contactsPageModule.getInputName().setValue(name);
            return this;
        });
    }

    public ContactsPageSteps enterEmail(String email) {
        return Allure.step("Ввод email: " + email, () -> {
            contactsPageModule.getInputEmail().setValue(email);
            return this;
        });
    }

    public ContactsPageSteps enterPhone(String phone) {
        return Allure.step("Ввод телефона: " + phone, () -> {
            contactsPageModule.getInputPhone().setValue(phone);
            return this;
        });
    }

    public ContactsPageSteps enterMessage(String message) {
        return Allure.step("Ввод сообщения: " + message, () -> {
            contactsPageModule.getInputMessage().setValue(message);
            return this;
        });
    }

    public ContactsPageSteps clickSubmitButton() {
        return Allure.step("Клик на кнопку 'Отправить'", () -> {
            contactsPageModule.getSubmitButton().click();
            return this;
        });
    }

    public ContactsPageSteps checkNameErrorVisible() {
        return Allure.step("Проверка отображения ошибки поля 'Имя'", () -> {
            contactsPageModule.getErrorName().shouldBeVisible();
            return this;
        });
    }

    public ContactsPageSteps checkEmailErrorVisible() {
        return Allure.step("Проверка отображения ошибки поля 'Email'", () -> {
            contactsPageModule.getErrorEmail().shouldBeVisible();
            return this;
        });
    }

    public ContactsPageSteps checkFooterVisible() {
        return Allure.step("Проверка отображения футера", () -> {
            contactsPageModule.getFooter().shouldBeVisible();
            return this;
        });
    }
}
