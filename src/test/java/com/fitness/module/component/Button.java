package com.fitness.module.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class Button extends BaseComponent {

    public Button(String name, String cssSelector) {
        this.name = name;
        this.element = Selenide.$(cssSelector);
    }

    public Button(String name, By by) {
        this.name = name;
        this.element = Selenide.$(by);
    }

    public Button(String name, SelenideElement element) {
        this.name = name;
        this.element = element;
    }

    public Button(String name, SelenideElement parent, String cssSelector) {
        this.name = name;
        this.element = parent.$(cssSelector);
    }

    public Button(String name, SelenideElement parent, By by) {
        this.name = name;
        this.element = parent.$(by);
    }

    public void click() {
        this.element.scrollIntoView("{behavior: 'instant', block: 'center'}").shouldBe(Condition.visible).click();
    }

    public void shouldBeVisible() {
        this.element.shouldBe(Condition.visible);
    }

    public void shouldBeEnabled() {
        this.element.shouldBe(Condition.enabled);
    }

    public void shouldBeDisabled() {
        this.element.shouldBe(Condition.disabled);
    }

    public String getText() {
        return this.element.getText();
    }
}
