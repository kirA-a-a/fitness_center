package com.fitness.repository;

import com.fitness.entity.ContactMessage;

/**
 * Вставка без id — БД генерирует id (DEFAULT gen_random_uuid()::text), возвращаем его в сущность.
 */
public interface ContactMessageRepositoryCustom {

    /**
     * Вставляет сообщение в БД без id; БД проставляет id, метод записывает его в сущность.
     */
    void insertAndSetId(ContactMessage message);
}
