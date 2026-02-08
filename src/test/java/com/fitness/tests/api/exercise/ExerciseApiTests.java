package com.fitness.tests.api.exercise;

import com.fitness.tests.api.steps.ExerciseApiSteps;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@DisplayName("API тесты упражнений")
@Epic("API")
@Feature("Упражнения")
@Owner("Сафронов Иван Дмитриевич")
public class ExerciseApiTests {

    private ExerciseApiSteps exerciseApi;
    private String testId;

    @BeforeEach
    void setUp() {
        exerciseApi = new ExerciseApiSteps();
        testId = UUID.randomUUID().toString().substring(0, 8);
    }

    @Test
    @DisplayName("Создание упражнения через API")
    @Description("Проверяет создание нового упражнения через POST /api/exercises")
    @Story("CRUD операции")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("smoke")
    void shouldCreateExercise() {
        exerciseApi
                .create("api-test-" + testId, "API Тест", "Описание")
                .assertStatusCreated()
                .assertResponseIdNotNull()
                .assertResponseName("API Тест");
    }

    @Test
    @DisplayName("Получение упражнения по ID")
    @Description("Проверяет получение упражнения через GET /api/exercises/{id}")
    @Story("Чтение данных")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldGetExerciseById() {
        exerciseApi
                .create("get-by-id-" + testId, "Get By ID", null);

        UUID id = exerciseApi.getLastBody().getId();

        exerciseApi
                .getById(id)
                .assertStatusOk()
                .assertResponseName("Get By ID");
    }

    @Test
    @DisplayName("Получение упражнения по slug")
    @Description("Проверяет получение упражнения через GET /api/exercises/slug/{slug}")
    @Story("Чтение данных")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldGetExerciseBySlug() {
        String slug = "unique-slug-" + testId;
        exerciseApi
                .create(slug, "Unique Slug Test", null)
                .getBySlug(slug)
                .assertStatusOk()
                .assertResponseName("Unique Slug Test");
    }

    @Test
    @DisplayName("Получение всех упражнений")
    @Description("Проверяет получение списка упражнений через GET /api/exercises")
    @Story("Чтение данных")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldGetAllExercises() {
        exerciseApi
                .create("list-test-1-" + testId, "List Test 1", null)
                .create("list-test-2-" + testId, "List Test 2", null)
                .getAll()
                .assertListNotEmpty();
    }

    @Test
    @DisplayName("Обновление упражнения")
    @Description("Проверяет обновление упражнения через PUT /api/exercises/{id}")
    @Story("CRUD операции")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldUpdateExercise() {
        String slug = "update-test-" + testId;
        exerciseApi
                .create(slug, "Before Update", null);

        UUID id = exerciseApi.getLastBody().getId();

        exerciseApi
                .update(id, slug, "After Update", "Новое описание")
                .assertStatusOk()
                .assertResponseName("After Update");
    }

    @Test
    @DisplayName("Удаление упражнения")
    @Description("Проверяет удаление упражнения через DELETE /api/exercises/{id}")
    @Story("CRUD операции")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldDeleteExercise() {
        exerciseApi
                .create("delete-test-" + testId, "To Delete", null);

        UUID id = exerciseApi.getLastBody().getId();

        exerciseApi
                .delete(id)
                .assertStatusNoContent()
                .getById(id)
                .assertStatusNotFound();
    }

    @Test
    @DisplayName("Получение несуществующего упражнения")
    @Description("Проверяет 404 статус при запросе несуществующего упражнения")
    @Story("Обработка ошибок")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldReturn404ForNonExistentExercise() {
        exerciseApi
                .getById(UUID.randomUUID())
                .assertStatusNotFound();
    }
}
