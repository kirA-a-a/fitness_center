package com.fitness.tests.api.contactMessage;

import com.fitness.tests.api.steps.ContactMessageApiSteps;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("API тесты сообщений")
@Epic("API")
@Feature("Сообщения")
@Owner("Сафронов Иван Дмитриевич")
public class ContactMessageApiTests {

    private ContactMessageApiSteps messageApi;

    @BeforeEach
    void setUp() {
        messageApi = new ContactMessageApiSteps();
    }

    @Test
    @DisplayName("Создание сообщения через API")
    @Description("Проверяет создание нового сообщения через POST /api/messages")
    @Story("CRUD операции")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("smoke")
    void shouldCreateMessage() {
        messageApi
                .create("API Тестер", "api@test.com", "+7 999 000 00 00", "API сообщение")
                .assertStatusCreated()
                .assertResponseIdNotNull()
                .assertResponseName("API Тестер");
    }

    @Test
    @DisplayName("Получение сообщения по ID")
    @Description("Проверяет получение сообщения через GET /api/messages/{id}")
    @Story("Чтение данных")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldGetMessageById() {
        messageApi
                .create("Get Test", "get@test.com", "+1", "Message");

        Long id = messageApi.getLastBody().getId();

        messageApi
                .getById(id)
                .assertStatusOk()
                .assertResponseName("Get Test");
    }

    @Test
    @DisplayName("Получение всех сообщений")
    @Description("Проверяет получение списка сообщений через GET /api/messages")
    @Story("Чтение данных")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldGetAllMessages() {
        messageApi
                .create("User1", "u1@test.com", "+1", "Msg1")
                .create("User2", "u2@test.com", "+2", "Msg2")
                .getAll()
                .assertListNotEmpty();
    }

    @Test
    @DisplayName("Обновление сообщения")
    @Description("Проверяет обновление сообщения через PUT /api/messages/{id}")
    @Story("CRUD операции")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldUpdateMessage() {
        messageApi
                .create("Before", "before@test.com", "+1", "Old");

        Long id = messageApi.getLastBody().getId();

        messageApi
                .update(id, "After", "after@test.com", "+2", "New")
                .assertStatusOk()
                .assertResponseName("After");
    }

    @Test
    @DisplayName("Удаление сообщения")
    @Description("Проверяет удаление сообщения через DELETE /api/messages/{id}")
    @Story("CRUD операции")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldDeleteMessage() {
        messageApi
                .create("Delete", "delete@test.com", "+1", "To delete");

        Long id = messageApi.getLastBody().getId();

        messageApi
                .delete(id)
                .assertStatusNoContent()
                .getById(id)
                .assertStatusNotFound();
    }

    @Test
    @DisplayName("Получение несуществующего сообщения")
    @Description("Проверяет 404 статус при запросе несуществующего сообщения")
    @Story("Обработка ошибок")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldReturn404ForNonExistentMessage() {
        messageApi
                .getById(999999L)
                .assertStatusNotFound();
    }
}
