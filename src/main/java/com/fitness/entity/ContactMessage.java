package com.fitness.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_messages")
public class ContactMessage {

    @Id
    @Column(columnDefinition = "varchar(36) NOT NULL DEFAULT gen_random_uuid()::text", insertable = false)
    private String id;

    @Column(nullable = false)
    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 100, message = "Имя не должно превышать 100 символов")
    private String name;
    
    @Column(nullable = false)
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    @Size(max = 100, message = "Email не должен превышать 100 символов")
    private String email;
    
    @Column(nullable = false, length = 20)
    @NotBlank(message = "Телефон не может быть пустым")
    @Size(max = 20, message = "Телефон не должен превышать 20 символов")
    private String phone;

    @Column(length = 100)
    @Size(max = 100, message = "Telegram не должен превышать 100 символов")
    @Pattern(regexp = "^(|@[^@]{0,99})$", message = "Telegram должен начинаться с одного символа @")
    private String telegram;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Сообщение не может быть пустым")
    @Size(max = 500, message = "Сообщение не должно превышать 500 символов")
    private String message;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
    
    // Конструкторы
    public ContactMessage() {
    }
    
    public ContactMessage(String name, String email, String phone, String message) {
        this(name, email, phone, null, message);
    }

    public ContactMessage(String name, String email, String phone, String telegram, String message) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.telegram = telegram;
        this.message = message;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
