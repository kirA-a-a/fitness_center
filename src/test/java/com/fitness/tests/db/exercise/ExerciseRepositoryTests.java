package com.fitness.tests.db.exercise;

import com.fitness.config.DbTestConfig;
import com.fitness.tests.db.steps.ExerciseRepositorySteps;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("dbtest")
@Import(DbTestConfig.class)
@DisplayName("Тесты репозитория упражнений")
@Epic("Database")
@Feature("Упражнения")
@Owner("Сафронов Иван Дмитриевич")
public class ExerciseRepositoryTests {

    @Autowired
    private ExerciseRepositorySteps exerciseSteps;

    @Test
    @DisplayName("Сохранение и получение упражнения")
    @Description("Проверяет сохранение нового упражнения в БД и его получение по ID")
    @Story("CRUD операции")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("smoke")
    void shouldSaveAndFindExercise() {
        exerciseSteps
                .saveExercise("test-exercise", "Тестовое упражнение", "Описание")
                .assertSavedIdNotNull()
                .findById(exerciseSteps.getLastSavedExercise().getId())
                .assertFoundPresent()
                .assertFoundName("Тестовое упражнение");
    }

    @Test
    @DisplayName("Получение всех упражнений")
    @Description("Проверяет получение списка всех упражнений из БД")
    @Story("Чтение данных")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldFindAllExercises() {
        exerciseSteps
                .saveExercise("ex1", "Упражнение 1", null)
                .saveExercise("ex2", "Упражнение 2", null)
                .findAll()
                .assertFoundListSizeGreaterOrEqual(2);
    }

    @Test
    @DisplayName("Поиск упражнения по slug")
    @Description("Проверяет поиск упражнения по уникальному slug")
    @Story("Чтение данных")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldFindBySlug() {
        exerciseSteps
                .saveExercise("unique-slug", "Уникальное упражнение", null)
                .findBySlug("unique-slug")
                .assertFoundPresent()
                .assertFoundName("Уникальное упражнение");
    }

    @Test
    @DisplayName("Проверка существования упражнения")
    @Description("Проверяет метод existsById")
    @Story("Чтение данных")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldCheckExistsById() {
        exerciseSteps
                .saveExercise("exists-test", "Exists Test", null)
                .existsById(exerciseSteps.getLastSavedExercise().getId())
                .assertExists()
                .existsById(java.util.UUID.randomUUID().toString())
                .assertNotExists();
    }

    @Test
    @DisplayName("Удаление упражнения")
    @Description("Проверяет удаление упражнения из БД")
    @Story("Удаление данных")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldDeleteExercise() {
        exerciseSteps
                .saveExercise("to-delete", "Для удаления", null);

        String id = exerciseSteps.getLastSavedExercise().getId();

        exerciseSteps
                .deleteById(id)
                .existsById(id)
                .assertNotExists();
    }

    @Test
    @DisplayName("Подсчёт упражнений")
    @Description("Проверяет подсчёт количества упражнений")
    @Story("Чтение данных")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldCountExercises() {
        exerciseSteps.count();
        Long initialCount = exerciseSteps.getLastCount();

        exerciseSteps
                .saveExercise("count-test", "Count Test", null)
                .count()
                .assertCountEquals(initialCount + 1);
    }
}
