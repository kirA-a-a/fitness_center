package com.fitness.module.component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public abstract class BaseComponent {

    protected SelenideElement element;
    protected ElementsCollection elementsCollection;
    protected String name;

    public SelenideElement getElement() {
        return this.element;
    }

    public ElementsCollection getElementsCollection() {
        return this.elementsCollection;
    }

    public String getName() {
        return this.name;
    }
}
