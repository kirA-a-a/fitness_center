package com.fitness.tests.api.steps;

import com.fitness.entity.ContactMessage;
import io.qameta.allure.Allure;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Шаги для API тестов сообщений.
 */
public class ContactMessageApiSteps {

    private static final String BASE_URL = "http://localhost:8080/api/messages";

    private final RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<ContactMessage> lastResponse;
    private ResponseEntity<List<ContactMessage>> lastListResponse;
    private ResponseEntity<Void> lastVoidResponse;
    private int lastStatusCode;

    // ==================== API операции ====================

    public ContactMessageApiSteps getAll() {
        step("GET " + BASE_URL, () -> {
            Allure.addAttachment("HTTP запрос", "GET " + BASE_URL);

            lastListResponse = restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ContactMessage>>() {}
            );
            lastStatusCode = lastListResponse.getStatusCode().value();

            Allure.addAttachment("HTTP статус", String.valueOf(lastStatusCode));
            if (lastListResponse.getBody() != null) {
                Allure.addAttachment("HTTP ответ", "Найдено записей: " + lastListResponse.getBody().size());
            }
        });
        return this;
    }

    public ContactMessageApiSteps getById(UUID id) {
        step("GET " + BASE_URL + "/" + id, () -> {
            Allure.addAttachment("HTTP запрос", "GET " + BASE_URL + "/" + id);

            try {
                lastResponse = restTemplate.getForEntity(BASE_URL + "/" + id, ContactMessage.class);
                lastStatusCode = lastResponse.getStatusCode().value();
            } catch (HttpClientErrorException e) {
                lastStatusCode = e.getStatusCode().value();
                lastResponse = null;
            }

            Allure.addAttachment("HTTP статус", String.valueOf(lastStatusCode));
            if (lastResponse != null && lastResponse.getBody() != null) {
                ContactMessage msg = lastResponse.getBody();
                Allure.addAttachment("HTTP ответ", "application/json",
                        String.format("{\"id\": \"%s\", \"name\": \"%s\", \"email\": \"%s\"}",
                                msg.getId(), msg.getName(), msg.getEmail()));
            }
        });
        return this;
    }

    public ContactMessageApiSteps create(String name, String email, String phone, String message) {
        step("POST " + BASE_URL, () -> {
            ContactMessage msg = new ContactMessage(name, email, phone, message);

            Allure.addAttachment("HTTP запрос", "application/json",
                    String.format("POST %s\n{\"name\": \"%s\", \"email\": \"%s\", \"phone\": \"%s\", \"message\": \"%s\"}",
                            BASE_URL, name, email, phone, message));

            lastResponse = restTemplate.postForEntity(BASE_URL, msg, ContactMessage.class);
            lastStatusCode = lastResponse.getStatusCode().value();

            Allure.addAttachment("HTTP статус", String.valueOf(lastStatusCode));
            if (lastResponse.getBody() != null) {
                ContactMessage resp = lastResponse.getBody();
                Allure.addAttachment("HTTP ответ", "application/json",
                        String.format("{\"id\": \"%s\", \"name\": \"%s\", \"email\": \"%s\"}",
                                resp.getId(), resp.getName(), resp.getEmail()));
            }
        });
        return this;
    }

    public ContactMessageApiSteps update(UUID id, String name, String email, String phone, String message) {
        step("PUT " + BASE_URL + "/" + id, () -> {
            ContactMessage msg = new ContactMessage(name, email, phone, message);

            Allure.addAttachment("HTTP запрос", "application/json",
                    String.format("PUT %s/%s\n{\"name\": \"%s\", \"email\": \"%s\"}",
                            BASE_URL, id, name, email));

            try {
                lastResponse = restTemplate.exchange(
                        BASE_URL + "/" + id,
                        HttpMethod.PUT,
                        new HttpEntity<>(msg),
                        ContactMessage.class
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

    public ContactMessageApiSteps delete(UUID id) {
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

    public ContactMessageApiSteps assertStatusOk() {
        step("Проверяем: статус 200 OK", () -> {
            assertThat(lastStatusCode).isEqualTo(200);
            Allure.addAttachment("Результат", "Статус: 200 OK");
        });
        return this;
    }

    public ContactMessageApiSteps assertStatusCreated() {
        step("Проверяем: статус 201 Created", () -> {
            assertThat(lastStatusCode).isEqualTo(201);
            Allure.addAttachment("Результат", "Статус: 201 Created");
        });
        return this;
    }

    public ContactMessageApiSteps assertStatusNotFound() {
        step("Проверяем: статус 404 Not Found", () -> {
            assertThat(lastStatusCode).isEqualTo(404);
            Allure.addAttachment("Результат", "Статус: 404 Not Found");
        });
        return this;
    }

    public ContactMessageApiSteps assertStatusNoContent() {
        step("Проверяем: статус 204 No Content", () -> {
            assertThat(lastStatusCode).isEqualTo(204);
            Allure.addAttachment("Результат", "Статус: 204 No Content");
        });
        return this;
    }

    public ContactMessageApiSteps assertListNotEmpty() {
        step("Проверяем: список не пустой", () -> {
            assertThat(lastListResponse.getBody()).isNotEmpty();
            Allure.addAttachment("Результат", "Список содержит " + lastListResponse.getBody().size() + " записей");
        });
        return this;
    }

    public ContactMessageApiSteps assertResponseName(String expectedName) {
        step("Проверяем: имя = '" + expectedName + "'", () -> {
            assertThat(lastResponse).isNotNull();
            assertThat(lastResponse.getBody()).isNotNull();
            assertThat(lastResponse.getBody().getName()).isEqualTo(expectedName);
            Allure.addAttachment("Результат", "Имя совпадает: " + expectedName);
        });
        return this;
    }

    public ContactMessageApiSteps assertResponseIdNotNull() {
        step("Проверяем: ID не null", () -> {
            assertThat(lastResponse).isNotNull();
            assertThat(lastResponse.getBody()).isNotNull();
            assertThat(lastResponse.getBody().getId()).isNotNull();
            Allure.addAttachment("Результат", "ID = " + lastResponse.getBody().getId());
        });
        return this;
    }

    // ==================== Геттеры ====================

    public ResponseEntity<ContactMessage> getLastResponse() {
        return lastResponse;
    }

    public ResponseEntity<List<ContactMessage>> getLastListResponse() {
        return lastListResponse;
    }

    public ContactMessage getLastBody() {
        return lastResponse != null ? lastResponse.getBody() : null;
    }

    public int getLastStatusCode() {
        return lastStatusCode;
    }
}
