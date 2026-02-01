package com.fitness.module.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class Input extends BaseComponent {

    public Input(String name, String cssSelector) {
        this.name = name;
        this.element = Selenide.$(cssSelector);
    }

    public Input(String name, By by) {
        this.name = name;
        this.element = Selenide.$(by);
    }

    public Input(String name, SelenideElement element) {
        this.name = name;
        this.element = element;
    }

    public Input(String name, SelenideElement parent, String cssSelector) {
        this.name = name;
        this.element = parent.$(cssSelector);
    }

    public Input(String name, SelenideElement parent, By by) {
        this.name = name;
        this.element = parent.$(by);
    }

    public void setValue(String value) {
        this.element.shouldBe(Condition.visible).setValue(value);
    }

    public void clear() {
        this.element.clear();
    }

    public void clearAndSetValue(String value) {
        this.element.shouldBe(Condition.visible).clear();
        this.element.setValue(value);
    }

    public String getValue() {
        return this.element.getValue();
    }

    public void shouldHaveValue(String value) {
        this.element.shouldHave(Condition.value(value));
    }

    public void shouldBeVisible() {
        this.element.shouldBe(Condition.visible);
    }

    public void shouldBeEnabled() {
        this.element.shouldBe(Condition.enabled);
    }
}
