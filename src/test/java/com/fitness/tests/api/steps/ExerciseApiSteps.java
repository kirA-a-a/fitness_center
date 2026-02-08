package com.fitness.tests.api.steps;

import com.fitness.entity.Exercise;
import io.qameta.allure.Allure;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Шаги для API тестов упражнений.
 */
public class ExerciseApiSteps {

    private static final String BASE_URL = "http://localhost:8080/api/exercises";

    private final RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<Exercise> lastResponse;
    private ResponseEntity<List<Exercise>> lastListResponse;
    private ResponseEntity<Void> lastVoidResponse;
    private int lastStatusCode;

    // ==================== API операции ====================

    public ExerciseApiSteps getAll() {
        step("GET " + BASE_URL, () -> {
            Allure.addAttachment("HTTP запрос", "GET " + BASE_URL);

            lastListResponse = restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Exercise>>() {}
            );
            lastStatusCode = lastListResponse.getStatusCode().value();

            Allure.addAttachment("HTTP статус", String.valueOf(lastStatusCode));
            if (lastListResponse.getBody() != null) {
                Allure.addAttachment("HTTP ответ", "Найдено записей: " + lastListResponse.getBody().size());
            }
        });
        return this;
    }

    public ExerciseApiSteps getById(String id) {
        step("GET " + BASE_URL + "/" + id, () -> {
            Allure.addAttachment("HTTP запрос", "GET " + BASE_URL + "/" + id);

            try {
                lastResponse = restTemplate.getForEntity(BASE_URL + "/" + id, Exercise.class);
                lastStatusCode = lastResponse.getStatusCode().value();
            } catch (HttpClientErrorException e) {
                lastStatusCode = e.getStatusCode().value();
                lastResponse = null;
            }

            Allure.addAttachment("HTTP статус", String.valueOf(lastStatusCode));
            if (lastResponse != null && lastResponse.getBody() != null) {
                Exercise ex = lastResponse.getBody();
                Allure.addAttachment("HTTP ответ", "application/json",
                        String.format("{\"id\": \"%s\", \"slug\": \"%s\", \"name\": \"%s\"}",
                                ex.getId(), ex.getSlug(), ex.getName()));
            }
        });
        return this;
    }

    public ExerciseApiSteps getBySlug(String slug) {
        step("GET " + BASE_URL + "/slug/" + slug, () -> {
            Allure.addAttachment("HTTP запрос", "GET " + BASE_URL + "/slug/" + slug);

            try {
                lastResponse = restTemplate.getForEntity(BASE_URL + "/slug/" + slug, Exercise.class);
                lastStatusCode = lastResponse.getStatusCode().value();
            } catch (HttpClientErrorException e) {
                lastStatusCode = e.getStatusCode().value();
                lastResponse = null;
            }

            Allure.addAttachment("HTTP статус", String.valueOf(lastStatusCode));
            if (lastResponse != null && lastResponse.getBody() != null) {
                Exercise ex = lastResponse.getBody();
                Allure.addAttachment("HTTP ответ", "application/json",
                        String.format("{\"id\": \"%s\", \"slug\": \"%s\", \"name\": \"%s\"}",
                                ex.getId(), ex.getSlug(), ex.getName()));
            }
        });
        return this;
    }

    public ExerciseApiSteps create(String slug, String name, String description) {
        step("POST " + BASE_URL, () -> {
            Exercise exercise = new Exercise();
            exercise.setSlug(slug);
            exercise.setName(name);
            exercise.setDescription(description);

            Allure.addAttachment("HTTP запрос", "application/json",
                    String.format("POST %s\n{\"slug\": \"%s\", \"name\": \"%s\", \"description\": \"%s\"}",
                            BASE_URL, slug, name, description != null ? description : ""));

            lastResponse = restTemplate.postForEntity(BASE_URL, exercise, Exercise.class);
            lastStatusCode = lastResponse.getStatusCode().value();

            Allure.addAttachment("HTTP статус", String.valueOf(lastStatusCode));
            if (lastResponse.getBody() != null) {
                Exercise ex = lastResponse.getBody();
                Allure.addAttachment("HTTP ответ", "application/json",
                        String.format("{\"id\": \"%s\", \"slug\": \"%s\", \"name\": \"%s\"}",
                                ex.getId(), ex.getSlug(), ex.getName()));
            }
        });
        return this;
    }

    public ExerciseApiSteps update(String id, String slug, String name, String description) {
        step("PUT " + BASE_URL + "/" + id, () -> {
            Exercise exercise = new Exercise();
            exercise.setSlug(slug);
            exercise.setName(name);
            exercise.setDescription(description);

            Allure.addAttachment("HTTP запрос", "application/json",
                    String.format("PUT %s/%s\n{\"slug\": \"%s\", \"name\": \"%s\"}",
                            BASE_URL, id, slug, name));

            try {
                lastResponse = restTemplate.exchange(
                        BASE_URL + "/" + id,
                        HttpMethod.PUT,
                        new HttpEntity<>(exercise),
                        Exercise.class
                );
                lastStatusCode = lastResponse.getStatusCode().value();
            } catch (HttpClientErrorException e) {
                lastStatusCode = e.getStatusCode().value();
                lastResponse = null;
            }

            Allure.addAttachment("HTTP статус", String.valueOf(lastStatusCode));
        });
        return this;
    }

    public ExerciseApiSteps delete(String id) {
        step("DELETE " + BASE_URL + "/" + id, () -> {
            Allure.addAttachment("HTTP запрос", "DELETE " + BASE_URL + "/" + id);

            try {
                lastVoidResponse = restTemplate.exchange(
                        BASE_URL + "/" + id,
                        HttpMethod.DELETE,
                        null,
                        Void.class
                );
                lastStatusCode = lastVoidResponse.getStatusCode().value();
            } catch (HttpClientErrorException e) {
                lastStatusCode = e.getStatusCode().value();
                lastVoidResponse = null;
            }

            Allure.addAttachment("HTTP статус", String.valueOf(lastStatusCode));
        });
        return this;
    }

    // ==================== Проверки ====================

    public ExerciseApiSteps assertStatusOk() {
        step("Проверяем: статус 200 OK", () -> {
            assertThat(lastStatusCode).isEqualTo(200);
            Allure.addAttachment("Результат", "Статус: 200 OK");
        });
        return this;
    }

    public ExerciseApiSteps assertStatusCreated() {
        step("Проверяем: статус 201 Created", () -> {
            assertThat(lastStatusCode).isEqualTo(201);
            Allure.addAttachment("Результат", "Статус: 201 Created");
        });
        return this;
    }

    public ExerciseApiSteps assertStatusNotFound() {
        step("Проверяем: статус 404 Not Found", () -> {
            assertThat(lastStatusCode).isEqualTo(404);
            Allure.addAttachment("Результат", "Статус: 404 Not Found");
        });
        return this;
    }

    public ExerciseApiSteps assertStatusNoContent() {
        step("Проверяем: статус 204 No Content", () -> {
            assertThat(lastStatusCode).isEqualTo(204);
            Allure.addAttachment("Результат", "Статус: 204 No Content");
        });
        return this;
    }

    public ExerciseApiSteps assertListNotEmpty() {
        step("Проверяем: список не пустой", () -> {
            assertThat(lastListResponse.getBody()).isNotEmpty();
            Allure.addAttachment("Результат", "Список содержит " + lastListResponse.getBody().size() + " записей");
        });
        return this;
    }

    public ExerciseApiSteps assertResponseName(String expectedName) {
        step("Проверяем: название = '" + expectedName + "'", () -> {
            assertThat(lastResponse).isNotNull();
            assertThat(lastResponse.getBody()).isNotNull();
            assertThat(lastResponse.getBody().getName()).isEqualTo(expectedName);
            Allure.addAttachment("Результат", "Название совпадает: " + expectedName);
        });
        return this;
    }

    public ExerciseApiSteps assertResponseIdNotNull() {
        step("Проверяем: ID не null", () -> {
            assertThat(lastResponse).isNotNull();
            assertThat(lastResponse.getBody()).isNotNull();
            assertThat(lastResponse.getBody().getId()).isNotNull();
            Allure.addAttachment("Результат", "ID = " + lastResponse.getBody().getId());
        });
        return this;
    }

    // ==================== Геттеры ====================

    public ResponseEntity<Exercise> getLastResponse() {
        return lastResponse;
    }

    public ResponseEntity<List<Exercise>> getLastListResponse() {
        return lastListResponse;
    }

    public Exercise getLastBody() {
        return lastResponse != null ? lastResponse.getBody() : null;
    }

    public int getLastStatusCode() {
        return lastStatusCode;
    }
}
