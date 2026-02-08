package com.fitness.repository;

import com.fitness.entity.ContactMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class ContactMessageRepositoryImpl implements ContactMessageRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertAndSetId(ContactMessage message) {
        if (message.getCreatedAt() == null) {
            message.setCreatedAt(LocalDateTime.now());
        }
        String sql = "INSERT INTO contact_messages (name, email, phone, telegram, message, created_at) " +
                     "VALUES (:name, :email, :phone, :telegram, :message, :created_at) RETURNING id";
        Object id = entityManager.createNativeQuery(sql)
                .setParameter("name", message.getName())
                .setParameter("email", message.getEmail())
                .setParameter("phone", message.getPhone())
                .setParameter("telegram", message.getTelegram())
                .setParameter("message", message.getMessage())
                .setParameter("created_at", Timestamp.valueOf(message.getCreatedAt()))
                .getSingleResult();
        message.setId(id.toString());
    }
}
