package com.fitness.tests.db.steps;

import com.fitness.entity.Exercise;
import com.fitness.repository.ExerciseRepository;
import io.qameta.allure.Allure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Шаги для работы с репозиторием упражнений.
 */
@Component
public class ExerciseRepositorySteps {

    private static final String TABLE_NAME = "exercises";

    @Autowired
    private ExerciseRepository exerciseRepository;

    private Exercise lastSavedExercise;
    private Optional<Exercise> lastFoundExercise;
    private List<Exercise> lastFoundExercises;
    private Boolean lastExistsResult;
    private Long lastCount;

    // ==================== CRUD операции ====================

    public ExerciseRepositorySteps saveExercise(String slug, String name, String description) {
        step("Сохраняем упражнение: " + name, () -> {
            Exercise exercise = new Exercise();
            exercise.setSlug(slug);
            exercise.setName(name);
            exercise.setDescription(description);

            String sql = String.format(
                    "INSERT INTO %s (slug, name, description) VALUES ('%s', '%s', '%s')",
                    TABLE_NAME, slug, name, description != null ? description : "NULL");
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastSavedExercise = exerciseRepository.save(exercise);

            String response = String.format("Inserted 1 row. Generated ID: %s", lastSavedExercise.getId());
            Allure.addAttachment("SQL ответ", "text/plain", response);
        });
        return this;
    }

    public ExerciseRepositorySteps findById(String id) {
        step("Ищем упражнение по ID: " + id, () -> {
            String sql = String.format("SELECT * FROM %s WHERE id = '%s'", TABLE_NAME, id);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastFoundExercise = exerciseRepository.findById(id);

            if (lastFoundExercise.isPresent()) {
                Exercise ex = lastFoundExercise.get();
                String response = String.format(
                        "| id | slug | name |\n|----|------|------|\n| %s | %s | %s |",
                        ex.getId(), ex.getSlug(), ex.getName());
                Allure.addAttachment("SQL ответ", "text/plain", response);
            } else {
                Allure.addAttachment("SQL ответ", "text/plain", "0 rows returned");
            }
        });
        return this;
    }

    public ExerciseRepositorySteps findBySlug(String slug) {
        step("Ищем упражнение по slug: " + slug, () -> {
            String sql = String.format("SELECT * FROM %s WHERE slug = '%s'", TABLE_NAME, slug);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastFoundExercise = exerciseRepository.findBySlug(slug);

            if (lastFoundExercise.isPresent()) {
                Exercise ex = lastFoundExercise.get();
                String response = String.format(
                        "| id | slug | name |\n|----|------|------|\n| %s | %s | %s |",
                        ex.getId(), ex.getSlug(), ex.getName());
                Allure.addAttachment("SQL ответ", "text/plain", response);
            } else {
                Allure.addAttachment("SQL ответ", "text/plain", "0 rows returned");
            }
        });
        return this;
    }

    public ExerciseRepositorySteps findAll() {
        step("Получаем все упражнения", () -> {
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastFoundExercises = exerciseRepository.findAll();

            StringBuilder response = new StringBuilder();
            response.append("| id | slug | name |\n|----|------|------|\n");
            for (Exercise ex : lastFoundExercises) {
                response.append(String.format("| %s | %s | %s |\n", ex.getId(), ex.getSlug(), ex.getName()));
            }
            response.append("\nTotal: ").append(lastFoundExercises.size()).append(" rows");
            Allure.addAttachment("SQL ответ", "text/plain", response.toString());
        });
        return this;
    }

    public ExerciseRepositorySteps existsById(String id) {
        step("Проверяем существование упражнения с ID: " + id, () -> {
            String sql = String.format("SELECT COUNT(*) FROM %s WHERE id = '%s'", TABLE_NAME, id);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastExistsResult = exerciseRepository.existsById(id);

            String response = String.format("COUNT: %d (exists: %s)", lastExistsResult ? 1 : 0, lastExistsResult);
            Allure.addAttachment("SQL ответ", "text/plain", response);
        });
        return this;
    }

    public ExerciseRepositorySteps deleteById(String id) {
        step("Удаляем упражнение с ID: " + id, () -> {
            String sql = String.format("DELETE FROM %s WHERE id = '%s'", TABLE_NAME, id);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            exerciseRepository.deleteById(id);

            Allure.addAttachment("SQL ответ", "text/plain", "1 row deleted");
        });
        return this;
    }

    public ExerciseRepositorySteps count() {
        step("Считаем количество упражнений", () -> {
            String sql = String.format("SELECT COUNT(*) FROM %s", TABLE_NAME);
            Allure.addAttachment("SQL запрос", "text/plain", sql);

            lastCount = exerciseRepository.count();

            String response = String.format("COUNT: %d", lastCount);
            Allure.addAttachment("SQL ответ", "text/plain", response);
        });
        return this;
    }

    // ==================== Проверки (Assertions) ====================

    public ExerciseRepositorySteps assertSavedIdNotNull() {
        step("Проверяем: ID сохранённого упражнения не null", () -> {
            assertThat(lastSavedExercise.getId()).isNotNull();
            Allure.addAttachment("Результат", "ID = " + lastSavedExercise.getId());
        });
        return this;
    }

    public ExerciseRepositorySteps assertFoundPresent() {
        step("Проверяем: упражнение найдено", () -> {
            assertThat(lastFoundExercise).isPresent();
            Allure.addAttachment("Результат", "Упражнение найдено");
        });
        return this;
    }

    public ExerciseRepositorySteps assertFoundNotPresent() {
        step("Проверяем: упражнение не найдено", () -> {
            assertThat(lastFoundExercise).isNotPresent();
            Allure.addAttachment("Результат", "Упражнение не найдено");
        });
        return this;
    }

    public ExerciseRepositorySteps assertFoundName(String expectedName) {
        step("Проверяем: название упражнения = '" + expectedName + "'", () -> {
            assertThat(lastFoundExercise).isPresent();
            assertThat(lastFoundExercise.get().getName()).isEqualTo(expectedName);
            Allure.addAttachment("Результат", "Название совпадает: " + expectedName);
        });
        return this;
    }

    public ExerciseRepositorySteps assertFoundListSizeGreaterOrEqual(int minSize) {
        step("Проверяем: найдено >= " + minSize + " упражнений", () -> {
            assertThat(lastFoundExercises).hasSizeGreaterThanOrEqualTo(minSize);
            Allure.addAttachment("Результат", "Найдено: " + lastFoundExercises.size() + " >= " + minSize);
        });
        return this;
    }

    public ExerciseRepositorySteps assertExists() {
        step("Проверяем: упражнение существует", () -> {
            assertThat(lastExistsResult).isTrue();
            Allure.addAttachment("Результат", "Существует: true");
        });
        return this;
    }

    public ExerciseRepositorySteps assertNotExists() {
        step("Проверяем: упражнение не существует", () -> {
            assertThat(lastExistsResult).isFalse();
            Allure.addAttachment("Результат", "Существует: false");
        });
        return this;
    }

    public ExerciseRepositorySteps assertCountEquals(Long expectedCount) {
        step("Проверяем: количество = " + expectedCount, () -> {
            assertThat(lastCount).isEqualTo(expectedCount);
            Allure.addAttachment("Результат", "Количество: " + lastCount + " = " + expectedCount);
        });
        return this;
    }

    // ==================== Геттеры ====================

    public Exercise getLastSavedExercise() {
        return lastSavedExercise;
    }

    public Optional<Exercise> getLastFoundExercise() {
        return lastFoundExercise;
    }

    public List<Exercise> getLastFoundExercises() {
        return lastFoundExercises;
    }

    public Boolean getLastExistsResult() {
        return lastExistsResult;
    }

    public Long getLastCount() {
        return lastCount;
    }
}
