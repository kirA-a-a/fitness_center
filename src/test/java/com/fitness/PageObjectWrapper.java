package com.fitness;

import com.fitness.config.ui.SelenideConfig;
import com.fitness.config.ui.VideoOnFailureExtension;
import com.fitness.tests.ui.steps.AboutPageSteps;
import com.fitness.tests.ui.steps.ContactsPageSteps;
import com.fitness.tests.ui.steps.ExerciseDetailPageSteps;
import com.fitness.tests.ui.steps.MainPageSteps;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ExtendWith(VideoOnFailureExtension.class)
public class PageObjectWrapper {

    @Autowired
    protected MainPageSteps mainPageSteps;

    @Autowired
    protected AboutPageSteps aboutPageSteps;

    @Autowired
    protected ContactsPageSteps contactsPageSteps;

    @Autowired
    protected ExerciseDetailPageSteps exerciseDetailPageSteps;

    @Autowired
    protected SelenideConfig selenideConfig;
}
