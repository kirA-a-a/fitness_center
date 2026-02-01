package com.fitness.module.modules;

import com.fitness.locators.classes.ExerciseDetailPageLocators;
import com.fitness.module.component.BaseComponent;
import com.fitness.module.component.Div;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ExerciseDetailPageModule extends BaseComponent {

    private final Div backLink;
    private final Div exerciseTitle;
    private final Div exerciseImage;
    private final Div sectionDescription;
    private final Div sectionTechnique;
    private final Div sectionProgression;
    private final Div sectionMuscles;
    private final Div sectionMistakes;
    private final Div sectionTips;
    private final Div footer;

    public ExerciseDetailPageModule(ExerciseDetailPageLocators locators) {
        this.backLink = new Div("Ссылка 'Вернуться на главную'", locators.getBackLink());
        this.exerciseTitle = new Div("Название упражнения", locators.getExerciseTitle());
        this.exerciseImage = new Div("Изображение упражнения", locators.getExerciseImage());
        this.sectionDescription = new Div("Секция 'Описание'", locators.getSectionDescription());
        this.sectionTechnique = new Div("Секция 'Техника выполнения'", locators.getSectionTechnique());
        this.sectionProgression = new Div("Секция 'Прогрессия'", locators.getSectionProgression());
        this.sectionMuscles = new Div("Секция 'Мышечные группы'", locators.getSectionMuscles());
        this.sectionMistakes = new Div("Секция 'Типичные ошибки'", locators.getSectionMistakes());
        this.sectionTips = new Div("Секция 'Советы'", locators.getSectionTips());
        this.footer = new Div("Футер", locators.getFooter());
    }
}
