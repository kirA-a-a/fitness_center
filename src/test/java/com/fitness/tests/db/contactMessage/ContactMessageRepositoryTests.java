package com.fitness.tests.db.contactMessage;

import com.fitness.config.DbTestConfig;
import com.fitness.tests.db.steps.ContactMessageRepositorySteps;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@DataJpaTest
@ActiveProfiles("dbtest")
@Import(DbTestConfig.class)
@DisplayName("Тесты репозитория сообщений")
@Epic("Database")
@Feature("Сообщения")
@Owner("Сафронов Иван Дмитриевич")
public class ContactMessageRepositoryTests {

    @Autowired
    private ContactMessageRepositorySteps messageSteps;

    @Test
    @DisplayName("Сохранение и получение сообщения")
    @Description("Проверяет сохранение нового сообщения в БД и его получение по ID")
    @Story("CRUD операции")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("smoke")
    void shouldSaveAndFindMessage() {
        messageSteps
                .saveMessage("Тестовый пользователь", "test@example.com", "+7 999 123 45 67", "Тестовое сообщение")
                .assertSavedIdNotNull()
                .findById(messageSteps.getLastSavedMessage().getId())
                .assertFoundPresent()
                .assertFoundName("Тестовый пользователь");
    }

    @Test
    @DisplayName("Получение всех сообщений")
    @Description("Проверяет получение списка всех сообщений из БД")
    @Story("Чтение данных")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldFindAllMessages() {
        messageSteps
                .saveMessage("User1", "u1@test.com", "+1", "Msg1")
                .saveMessage("User2", "u2@test.com", "+2", "Msg2")
                .findAll()
                .assertFoundListSizeGreaterOrEqual(2);
    }

    @Test
    @DisplayName("Проверка существования сообщения")
    @Description("Проверяет метод existsById")
    @Story("Чтение данных")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldCheckExistsById() {
        messageSteps
                .saveMessage("Exists", "e@test.com", "+3", "Test")
                .existsById(messageSteps.getLastSavedMessage().getId())
                .assertExists()
                .existsById(UUID.randomUUID())
                .assertNotExists();
    }

    @Test
    @DisplayName("Удаление сообщения")
    @Description("Проверяет удаление сообщения из БД")
    @Story("Удаление данных")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("functional")
    void shouldDeleteMessage() {
        messageSteps
                .saveMessage("Delete", "d@test.com", "+4", "To delete");

        UUID id = messageSteps.getLastSavedMessage().getId();

        messageSteps
                .deleteById(id)
                .existsById(id)
                .assertNotExists();
    }

    @Test
    @DisplayName("Подсчёт сообщений")
    @Description("Проверяет подсчёт количества сообщений")
    @Story("Чтение данных")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldCountMessages() {
        messageSteps.count();
        Long initialCount = messageSteps.getLastCount();

        messageSteps
                .saveMessage("Count", "c@test.com", "+5", "Count test")
                .count()
                .assertCountEquals(initialCount + 1);
    }
}
