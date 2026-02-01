package com.fitness.module.component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class Divs extends BaseComponent {

    public Divs(String name, String cssSelector) {
        this.name = name;
        this.elementsCollection = Selenide.$$(cssSelector);
    }

    public Divs(String name, By by) {
        this.name = name;
        this.elementsCollection = Selenide.$$(by);
    }

    public Divs(String name, SelenideElement parent, String cssSelector) {
        this.name = name;
        this.elementsCollection = parent.$$(cssSelector);
    }

    public Divs(String name, SelenideElement parent, By by) {
        this.name = name;
        this.elementsCollection = parent.$$(by);
    }

    public int size() {
        return this.elementsCollection.size();
    }

    public SelenideElement get(int index) {
        return this.elementsCollection.get(index);
    }

    public Div getDiv(int index) {
        return new Div(this.name + "[" + index + "]", this.elementsCollection.get(index));
    }
}
