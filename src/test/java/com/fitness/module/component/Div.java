package com.fitness.module.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public final class Div extends BaseComponent {

    public Div(String name, String cssSelector) {
        this.name = name;
        this.element = Selenide.$(cssSelector);
    }

    public Div(String name, By by) {
        this.name = name;
        this.element = Selenide.$(by);
    }

    public Div(String name, SelenideElement element) {
        this.name = name;
        this.element = element;
    }

    public Div(String name, SelenideElement parent, String cssSelector) {
        this.name = name;
        this.element = parent.$(cssSelector);
    }

    public Div(String name, SelenideElement parent, By by) {
        this.name = name;
        this.element = parent.$(by);
    }

    public ElementsCollection getDiv(String dataTestId) {
        return this.element.$$(Selectors.byTagName("div"))
                .filter(Condition.attribute("data-testid", dataTestId));
    }

    public void click() {
        this.element.shouldBe(Condition.visible).click();
    }

    public void shouldBeVisible() {
        this.element.shouldBe(Condition.visible);
    }

    public String getText() {
        return this.element.getText();
    }
}
