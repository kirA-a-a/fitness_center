package com.fitness.tests.db.steps;

import com.fitness.entity.ContactMessage;
import com.fitness.repository.ContactMessageRepository;
import io.qameta.allure.Allure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Шаги для работы с репозиторием сообщений.
 */
@Component
public class ContactMessageRepositorySteps {

    private static final String TABLE_NAME = "contact_messages";

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    private ContactMessage lastSavedMessage;
    private Optional<ContactMessage> lastFoundMessage;
    private List<ContactMessage> lastFoundMessages;
    private Boolean lastExistsResult;
    private Long lastCount;

    // ==================== CRUD операции ====================

    public ContactMessageRepositorySteps saveMessage(String name, String email, String phone, String message) {
        step("Сохраняем сообщение от: " + name, () -> {
            ContactMessage msg = new ContactMessage(name, email, phone, message);

            String sql = String.format(
                    "INSERT INTO %s (name, email, phone, message) VALUES ('%s', '%s', '%s', '%s')",
                    TABLE_NAME, name, email, phone, message);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastSavedMessage = contactMessageRepository.save(msg);

            String response = String.format("Inserted 1 row. Generated ID: %s", lastSavedMessage.getId());
            Allure.addAttachment("SQL ответ", "text/plain", response);
        });
        return this;
    }

    public ContactMessageRepositorySteps findById(UUID id) {
        step("Ищем сообщение по ID: " + id, () -> {
            String sql = String.format("SELECT * FROM %s WHERE id = '%s'", TABLE_NAME, id);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastFoundMessage = contactMessageRepository.findById(id);

            if (lastFoundMessage.isPresent()) {
                ContactMessage msg = lastFoundMessage.get();
                String response = String.format(
                        "| id | name | email | phone |\n|----|------|-------|-------|\n| %s | %s | %s | %s |",
                        msg.getId(), msg.getName(), msg.getEmail(), msg.getPhone());
                Allure.addAttachment("SQL ответ", "text/plain", response);
            } else {
                Allure.addAttachment("SQL ответ", "text/plain", "0 rows returned");
            }
        });
        return this;
    }

    public ContactMessageRepositorySteps findAll() {
        step("Получаем все сообщения", () -> {
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastFoundMessages = contactMessageRepository.findAll();

            StringBuilder response = new StringBuilder();
            response.append("| id | name | email | phone |\n|----|------|-------|-------|\n");
            for (ContactMessage msg : lastFoundMessages) {
                response.append(String.format("| %s | %s | %s | %s |\n",
                        msg.getId(), msg.getName(), msg.getEmail(), msg.getPhone()));
            }
            response.append("\nTotal: ").append(lastFoundMessages.size()).append(" rows");
            Allure.addAttachment("SQL ответ", "text/plain", response.toString());
        });
        return this;
    }

    public ContactMessageRepositorySteps existsById(UUID id) {
        step("Проверяем существование сообщения с ID: " + id, () -> {
            String sql = String.format("SELECT COUNT(*) FROM %s WHERE id = '%s'", TABLE_NAME, id);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastExistsResult = contactMessageRepository.existsById(id);

            String response = String.format("COUNT: %d (exists: %s)", lastExistsResult ? 1 : 0, lastExistsResult);
            Allure.addAttachment("SQL ответ", "text/plain", response);
        });
        return this;
    }

    public ContactMessageRepositorySteps deleteById(UUID id) {
        step("Удаляем сообщение с ID: " + id, () -> {
            String sql = String.format("DELETE FROM %s WHERE id = '%s'", TABLE_NAME, id);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            contactMessageRepository.deleteById(id);

            Allure.addAttachment("SQL ответ", "text/plain", "1 row deleted");
        });
        return this;
    }

    public ContactMessageRepositorySteps count() {
        step("Считаем количество сообщений", () -> {
            String sql = String.format("SELECT COUNT(*) FROM %s", TABLE_NAME);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastCount = contactMessageRepository.count();

            String response = String.format("COUNT: %d", lastCount);
            Allure.addAttachment("SQL ответ", "text/plain", response);
        });
        return this;
    }

    // ==================== Проверки (Assertions) ====================

    public ContactMessageRepositorySteps assertSavedIdNotNull() {
        step("Проверяем: ID сохранённого сообщения не null", () -> {
            assertThat(lastSavedMessage.getId()).isNotNull();
            Allure.addAttachment("Результат", "ID = " + lastSavedMessage.getId());
        });
        return this;
    }

    public ContactMessageRepositorySteps assertFoundPresent() {
        step("Проверяем: сообщение найдено", () -> {
            assertThat(lastFoundMessage).isPresent();
            Allure.addAttachment("Результат", "Сообщение найдено");
        });
        return this;
    }

    public ContactMessageRepositorySteps assertFoundNotPresent() {
        step("Проверяем: сообщение не найдено", () -> {
            assertThat(lastFoundMessage).isNotPresent();
            Allure.addAttachment("Результат", "Сообщение не найдено");
        });
        return this;
    }

    public ContactMessageRepositorySteps assertFoundName(String expectedName) {
        step("Проверяем: имя отправителя = '" + expectedName + "'", () -> {
            assertThat(lastFoundMessage).isPresent();
            assertThat(lastFoundMessage.get().getName()).isEqualTo(expectedName);
            Allure.addAttachment("Результат", "Имя совпадает: " + expectedName);
        });
        return this;
    }

    public ContactMessageRepositorySteps assertFoundListSizeGreaterOrEqual(int minSize) {
        step("Проверяем: найдено >= " + minSize + " сообщений", () -> {
            assertThat(lastFoundMessages).hasSizeGreaterThanOrEqualTo(minSize);
            Allure.addAttachment("Результат", "Найдено: " + lastFoundMessages.size() + " >= " + minSize);
        });
        return this;
    }

    public ContactMessageRepositorySteps assertExists() {
        step("Проверяем: сообщение существует", () -> {
            assertThat(lastExistsResult).isTrue();
            Allure.addAttachment("Результат", "Существует: true");
        });
        return this;
    }

    public ContactMessageRepositorySteps assertNotExists() {
        step("Проверяем: сообщение не существует", () -> {
            assertThat(lastExistsResult).isFalse();
            Allure.addAttachment("Результат", "Существует: false");
        });
        return this;
    }

    public ContactMessageRepositorySteps assertCountEquals(Long expectedCount) {
        step("Проверяем: количество = " + expectedCount, () -> {
            assertThat(lastCount).isEqualTo(expectedCount);
            Allure.addAttachment("Результат", "Количество: " + lastCount + " = " + expectedCount);
        });
        return this;
    }

    // ==================== Геттеры ====================

    public ContactMessage getLastSavedMessage() {
        return lastSavedMessage;
    }

    public Optional<ContactMessage> getLastFoundMessage() {
        return lastFoundMessage;
    }

    public List<ContactMessage> getLastFoundMessages() {
        return lastFoundMessages;
    }

    public Boolean getLastExistsResult() {
        return lastExistsResult;
    }

    public Long getLastCount() {
        return lastCount;
    }
}
